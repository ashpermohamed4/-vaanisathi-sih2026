package com.vaanisathi.asr

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.StorageService
import java.io.IOException

/**
 * Production-grade offline Vosk ASR Engine wrapper for Android.
 * Loads the 42 MB vosk-model-small-hi-0.22 model and executes on-device speech recognition.
 */
class VoskASREngine(private val context: Context) : RecognitionListener {

    companion object {
        private const val TAG = "VoskASREngine"
        private const val MODEL_NAME = "vosk-model-small-hi-0.22"
        private const val SAMPLE_RATE = 16000.0f
    }

    private var model: Model? = null
    private var speechService: SpeechService? = null
    private var startTimeMs: Long = 0L

    private val _isModelLoaded = MutableStateFlow(false)
    val isModelLoaded: StateFlow<Boolean> = _isModelLoaded

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening

    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText

    private val _lastLatencyMs = MutableStateFlow(0L)
    val lastLatencyMs: StateFlow<Long> = _lastLatencyMs

    private val _engineStatus = MutableStateFlow("Initializing Vosk Engine...")
    val engineStatus: StateFlow<String> = _engineStatus

    /**
     * Initializes the Vosk Kaldi model from app assets or internal storage.
     */
    fun initializeModel(onLoaded: ((Boolean) -> Unit)? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _engineStatus.value = "Unpacking offline Hindi ASR model (42 MB)..."
                StorageService.unpack(
                    context,
                    MODEL_NAME,
                    "models",
                    { loadedModel: Model? ->
                        model = loadedModel
                        _isModelLoaded.value = true
                        _engineStatus.value = "Offline ASR Ready (Vosk Small Hindi)"
                        Log.i(TAG, "Vosk Hindi model loaded successfully.")
                        onLoaded?.invoke(true)
                    },
                    { exception: IOException ->
                        _engineStatus.value = "Failed to load Vosk model: ${exception.message}"
                        Log.e(TAG, "Failed to unpack model", exception)
                        onLoaded?.invoke(false)
                    }
                )
            } catch (e: Exception) {
                _engineStatus.value = "Model load error: ${e.message}"
                Log.e(TAG, "Error initializing Vosk model", e)
                onLoaded?.invoke(false)
            }
        }
    }

    /**
     * Starts microphone audio capture and live speech decoding.
     */
    fun startListening() {
        val loadedModel = model ?: run {
            _engineStatus.value = "Model not loaded yet."
            return
        }

        if (_isListening.value) return

        try {
            val recognizer = Recognizer(loadedModel, SAMPLE_RATE)
            recognizer.setWords(true)

            speechService = SpeechService(recognizer, SAMPLE_RATE).apply {
                startListening(this@VoskASREngine)
            }
            startTimeMs = System.currentTimeMillis()
            _isListening.value = true
            _recognizedText.value = ""
            _engineStatus.value = "Listening for Hindi speech..."
            Log.i(TAG, "SpeechService started listening.")
        } catch (e: IOException) {
            _engineStatus.value = "Microphone error: ${e.message}"
            Log.e(TAG, "Error starting SpeechService", e)
        }
    }

    /**
     * Stops microphone audio capture and finalizes recognition.
     */
    fun stopListening() {
        speechService?.let {
            it.stop()
            it.shutdown()
            speechService = null
        }
        _isListening.value = false
        _engineStatus.value = "ASR Idle (Model Ready)"
        Log.i(TAG, "SpeechService stopped.")
    }

    // ----------------------------------------------------
    // Vosk RecognitionListener Callbacks
    // ----------------------------------------------------
    override fun onPartialResult(hypothesis: String?) {
        hypothesis?.let {
            try {
                val json = JSONObject(it)
                val partial = json.optString("partial", "")
                if (partial.isNotBlank()) {
                    _recognizedText.value = partial
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error parsing partial result", e)
            }
        }
    }

    override fun onResult(hypothesis: String?) {
        hypothesis?.let {
            try {
                val json = JSONObject(it)
                val text = json.optString("text", "")
                if (text.isNotBlank()) {
                    val latency = System.currentTimeMillis() - startTimeMs
                    _lastLatencyMs.value = latency
                    _recognizedText.value = text
                    Log.i(TAG, "Recognized: '$text' in ${latency}ms")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error parsing result", e)
            }
        }
    }

    override fun onFinalResult(hypothesis: String?) {
        hypothesis?.let {
            try {
                val json = JSONObject(it)
                val text = json.optString("text", "")
                if (text.isNotBlank()) {
                    val latency = System.currentTimeMillis() - startTimeMs
                    _lastLatencyMs.value = latency
                    _recognizedText.value = text
                    Log.i(TAG, "Final Recognized: '$text' in ${latency}ms")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error parsing final result", e)
            }
        }
        _isListening.value = false
    }

    override fun onError(exception: java.lang.Exception?) {
        _engineStatus.value = "ASR Error: ${exception?.message}"
        _isListening.value = false
        Log.e(TAG, "ASR Recognition error", exception)
    }

    override fun onTimeout() {
        _isListening.value = false
        _engineStatus.value = "ASR Timeout (No speech detected)"
        Log.w(TAG, "ASR Timeout reached.")
    }
}
