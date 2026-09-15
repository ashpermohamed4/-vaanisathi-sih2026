package com.vaanisathi.tts

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class AudioPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var tts: TextToSpeech? = null
    @Volatile private var ttsReady = false

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.setLanguage(Locale("hi", "IN"))
                ttsReady = true
                Log.d("AudioPlayer", "TTS ready")
            }
        }
    }

    // Play MP3 if exists, else TTS speak fallbackText
    fun playFromAssets(
        filename: String,
        fallbackText: String = ""
    ) {
        try {
            mediaPlayer?.release()
            val afd = context.assets.openFd("audio/$filename")
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor,
                    afd.startOffset, afd.length)
                prepare()
                start()
            }
        } catch (e: Exception) {
            Log.w("AudioPlayer", "No MP3, TTS: $fallbackText")
            speakNow(fallbackText)
        }
    }

    // Speak any text immediately
    fun speakNow(text: String) {
        if (text.isBlank()) return
        if (ttsReady && tts != null) {
            tts!!.speak(
                text,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "id_${System.currentTimeMillis()}"
            )
        } else {
            // TTS not ready, init fresh
            var t: TextToSpeech? = null
            t = TextToSpeech(context) { s ->
                if (s == TextToSpeech.SUCCESS) {
                    t?.setLanguage(Locale("hi", "IN"))
                    t?.speak(text,
                        TextToSpeech.QUEUE_FLUSH, null, "id2")
                }
            }
        }
    }

    // Speak Santhali then Hindi after delay
    fun speakBothDirections(
        santhaliText: String,
        hindiText: String,
        delayMs: Long = 2000
    ) {
        speakNow(santhaliText)
        Handler(Looper.getMainLooper())
            .postDelayed({ speakNow(hindiText) }, delayMs)
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun shutdown() {
        release()
        tts?.shutdown()
        tts = null
        ttsReady = false
    }
}
