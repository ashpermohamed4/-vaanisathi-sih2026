package com.vaanisathi.asr

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.StorageService
import org.json.JSONObject

class VoskASREngine(private val context: Context) {

    // States the UI observes
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

    // Load the Vosk Hindi model from assets
    // Model must be in: app/src/main/assets/vosk-model-small-hi-0.22/
    suspend fun initialize() = withContext(Dispatchers.IO) {
        try {
            _state.value = ASRState.Loading
            StorageService.unpack(
                context,
                "vosk-model-small-hi-0.22",  // folder in assets/
                "model",
                { model ->
                    this@VoskASREngine.model = model
                    _state.value = ASRState.Idle
                },
                { exception ->
                    _state.value = ASRState.Error("Model load failed: ${exception.message}")
                }
            )
        } catch (e: Exception) {
            _state.value = ASRState.Error(e.message ?: "Unknown error")
        }
    }

    // Start recording and recognizing — called from TeacherScreen
    fun startListening() {
        val currentModel = model ?: run {
            _state.value = ASRState.Error("Model not loaded")
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
                val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
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
