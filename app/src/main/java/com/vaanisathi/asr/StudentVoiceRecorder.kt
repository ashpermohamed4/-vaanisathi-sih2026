package com.vaanisathi.asr

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File

class StudentVoiceRecorder(private val context: Context) {

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var outputFile: File? = null
    
    enum class State { IDLE, RECORDING, PLAYING }
    var state = State.IDLE
        private set

    fun startRecording(): Boolean {
        return try {
            val file = File(context.cacheDir, "student_voice.3gp")
            outputFile = file

            recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                @Suppress("DEPRECATION")
                MediaRecorder()
            }

            recorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(file.absolutePath)
                prepare()
                start()
            }
            state = State.RECORDING
            Log.d("StudentVoice", "Recording started")
            true
        } catch (e: Exception) {
            Log.e("StudentVoice", "Record failed: ${e.message}")
            false
        }
    }

    fun stopRecording(): Boolean {
        return try {
            recorder?.stop()
            recorder?.release()
            recorder = null
            state = State.IDLE
            outputFile?.exists() == true
        } catch (e: Exception) {
            state = State.IDLE
            false
        }
    }

    fun playBack(onComplete: () -> Unit) {
        val file = outputFile ?: return
        if (!file.exists()) return
        try {
            player = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                prepare()
                setOnCompletionListener {
                    state = State.IDLE
                    onComplete()
                }
                start()
            }
            state = State.PLAYING
        } catch (e: Exception) {
            Log.e("StudentVoice", "Playback failed: ${e.message}")
            onComplete()
        }
    }

    fun release() {
        recorder?.release()
        recorder = null
        player?.release()
        player = null
        state = State.IDLE
    }
}
