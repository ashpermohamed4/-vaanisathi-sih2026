package com.vaanisathi.tts

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.util.Log
import java.io.IOException
import java.util.Locale

/**
 * Offline Audio Playback Engine
 * Tier 1: Plays pre-recorded native speaker MP3s from assets/audio/
 * Tier 2: Fallback to Android TTS engine when no native audio asset exists
 */
class AudioPlayer(private val context: Context) : TextToSpeech.OnInitListener {

    companion object {
        private const val TAG = "AudioPlayer"
        private const val AUDIO_ASSETS_PATH = "audio"
    }

    private var mediaPlayer: MediaPlayer? = null
    private var textToSpeech: TextToSpeech? = null
    private var isTtsReady = false

    init {
        textToSpeech = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech?.setLanguage(Locale("hi", "IN"))
            isTtsReady = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
            Log.i(TAG, "TextToSpeech initialized. Ready: $isTtsReady")
        } else {
            Log.e(TAG, "TextToSpeech initialization failed.")
        }
    }

    /**
     * Plays a native speaker audio file from local assets.
     */
    fun playAssetAudio(filename: String?, onComplete: (() -> Unit)? = null): Boolean {
        if (filename.isNullOrBlank()) return false

        stopPlayback()
        return try {
            val assetPath = "$AUDIO_ASSETS_PATH/$filename"
            val afd: AssetFileDescriptor = context.assets.openFd(assetPath)

            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                prepare()
                setOnCompletionListener {
                    stopPlayback()
                    onComplete?.invoke()
                }
                start()
            }
            Log.i(TAG, "Playing native audio asset: $assetPath")
            true
        } catch (e: IOException) {
            Log.w(TAG, "Asset audio not found: $filename. Falling back to phonetic synthesize.", e)
            false
        }
    }

    /**
     * Speaks text using local TTS fallback.
     */
    fun speakText(text: String) {
        if (isTtsReady && text.isNotBlank()) {
            stopPlayback()
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "VaaniSathi_TTS")
        }
    }

    /**
     * Stops any currently playing audio.
     */
    fun stopPlayback() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
            mediaPlayer = null
        }
    }

    /**
     * Cleans up resources.
     */
    fun release() {
        stopPlayback()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }
}
