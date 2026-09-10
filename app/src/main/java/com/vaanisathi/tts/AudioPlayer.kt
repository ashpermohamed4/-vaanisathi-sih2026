package com.vaanisathi.tts

import android.content.Context
import android.media.MediaPlayer
import android.util.Log

class AudioPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null

    // Play a pre-recorded native speaker audio file from assets/audio/
    // This is TIER 1 TTS — the fast path
    fun playFromAssets(filename: String, onComplete: (() -> Unit)? = null) {
        try {
            release()
            val afd = context.assets.openFd("audio/$filename")
            mediaPlayer = MediaPlayer().apply {
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                prepare()
                setOnCompletionListener {
                    onComplete?.invoke()
                    release()
                }
                start()
            }
        } catch (e: Exception) {
            // File not yet recorded — log silently, show text fallback
            Log.w("AudioPlayer", "Audio not found: $filename — showing text only")
            onComplete?.invoke()
        }
    }

    // Play a placeholder tone when audio file is missing
    // Useful during development before native audio is recorded
    fun playPlaceholder() {
        // Vibrate or show visual feedback
        Log.d("AudioPlayer", "Placeholder: audio file not yet recorded")
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
