package com.vaanisathi.asr

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.vosk.Model
import org.vosk.Recognizer
import java.io.File
import java.io.FileOutputStream

class VoskASREngine(private val context: Context) {

    sealed class ASRState {
        object Idle : ASRState()
        object Loading : ASRState()
        object Listening : ASRState()
        data class Result(val text: String, val latencyMs: Long) : ASRState()
        data class Error(val message: String) : ASRState()
    }

    private val _state = MutableStateFlow<ASRState>(ASRState.Idle)
    val state: StateFlow<ASRState> = _state

    private var model: Model? = null
    private var recognizer: Recognizer? = null
    private var audioRecord: AudioRecord? = null
    private var isListening = false

    private val SAMPLE_RATE = 16000
    private val BUFFER_SIZE = AudioRecord.getMinBufferSize(
        SAMPLE_RATE,
        AudioFormat.CHANNEL_IN_MONO,
        AudioFormat.ENCODING_PCM_16BIT
    )

    // ── MODEL LOADING ─────────────────────────────────────────────
    suspend fun initialize() = withContext(Dispatchers.IO) {
        try {
            _state.value = ASRState.Loading

            // Copy model from assets to internal storage
            val modelPath = copyModelFromAssets(
                context,
                assetFolder = "vosk-model-small-hi-0.22"
            )

            if (modelPath == null) {
                _state.value = ASRState.Error(
                    "Model files not found in assets/vosk-model-small-hi-0.22/\n" +
                    "Run: bash scripts/setup_models.sh then rebuild"
                )
                return@withContext
            }

            model = Model(modelPath)
            _state.value = ASRState.Idle

        } catch (e: Exception) {
            _state.value = ASRState.Error("Model load error: ${e.message}")
        }
    }

    // Copies model from APK assets → app internal storage
    // Returns the path to the copied model directory
    private fun copyModelFromAssets(
        context: Context,
        assetFolder: String
    ): String? {
        val outDir = File(context.filesDir, assetFolder)

        // Already copied on a previous launch — skip
        if (outDir.exists() && outDir.list()?.isNotEmpty() == true) {
            return outDir.absolutePath
        }

        outDir.mkdirs()

        return try {
            copyAssetFolder(context, assetFolder, outDir)
            outDir.absolutePath
        } catch (e: Exception) {
            outDir.deleteRecursively()
            null
        }
    }

    private fun copyAssetFolder(
        context: Context,
        assetPath: String,
        outDir: File
    ) {
        val assets = context.assets
        val children = assets.list(assetPath) ?: return

        if (children.isEmpty()) {
            // It's a file — copy it
            assets.open(assetPath).use { input ->
                FileOutputStream(outDir).use { output ->
                    input.copyTo(output)
                }
            }
        } else {
            // It's a folder — recurse
            outDir.mkdirs()
            for (child in children) {
                copyAssetFolder(
                    context,
                    "$assetPath/$child",
                    File(outDir, child)
                )
            }
        }
    }

    // ── ASR ───────────────────────────────────────────────────────
    fun startListening() {
        val currentModel = model ?: run {
            _state.value = ASRState.Error("Model not loaded — wait for loading")
            return
        }

        recognizer = Recognizer(currentModel, SAMPLE_RATE.toFloat())

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            BUFFER_SIZE * 2
        )

        isListening = true
        _state.value = ASRState.Listening

        val startTime = System.currentTimeMillis()
        val buffer = ShortArray(BUFFER_SIZE)
        audioRecord?.startRecording()

        Thread {
            while (isListening) {
                val read = audioRecord?.read(buffer, 0, buffer.size) ?: break
                if (read > 0) {
                    val bytes = ByteArray(read * 2)
                    for (i in 0 until read) {
                        bytes[i * 2] = (buffer[i].toInt() and 0xFF).toByte()
                        bytes[i * 2 + 1] = (buffer[i].toInt() shr 8).toByte()
                    }
                    if (recognizer?.acceptWaveForm(bytes, bytes.size) == true) {
                        val result = recognizer?.result ?: continue
                        val text = JSONObject(result).optString("text", "")
                        if (text.isNotBlank()) {
                            val latency = System.currentTimeMillis() - startTime
                            _state.value = ASRState.Result(text, latency)
                            stopListening()
                        }
                    }
                }
            }
        }.start()
    }

    fun stopListening() {
        isListening = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }

    fun release() {
        stopListening()
        recognizer?.close()
        model?.close()
    }
}
