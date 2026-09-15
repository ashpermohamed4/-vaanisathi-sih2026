package com.vaanisathi.nmt

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object BhashiniTranslator {

    private const val API_URL =
        "https://dhruva-api.bhashini.gov.in/services/inference/pipeline"
    private const val API_KEY = "YOUR_BHASHINI_API_KEY"

    data class TranslationResult(
        val santhaliText: String,
        val success: Boolean
    )

    suspend fun translateHindiToSanthali(
        hindiText: String
    ): TranslationResult = withContext(Dispatchers.IO) {
        try {
            val url = URL(API_URL)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Authorization", API_KEY)
            conn.connectTimeout = 5000
            conn.readTimeout = 5000
            conn.doOutput = true

            val body = JSONObject().apply {
                put("pipelineTasks", JSONArray().apply {
                    put(JSONObject().apply {
                        put("taskType", "translation")
                        put("config", JSONObject().apply {
                            put("language", JSONObject().apply {
                                put("sourceLanguage", "hi")
                                put("targetLanguage", "sat")
                            })
                        })
                    })
                })
                put("inputData", JSONObject().apply {
                    put("input", JSONArray().apply {
                        put(JSONObject().apply {
                            put("source", hindiText)
                        })
                    })
                })
            }.toString()

            conn.outputStream.write(body.toByteArray())

            if (conn.responseCode == 200) {
                val resp = conn.inputStream.bufferedReader().readText()
                val json = JSONObject(resp)
                val translated = json
                    .getJSONArray("pipelineResponse")
                    .getJSONObject(0)
                    .getJSONArray("output")
                    .getJSONObject(0)
                    .getString("target")
                TranslationResult(translated, true)
            } else {
                TranslationResult("", false)
            }
        } catch (e: Exception) {
            Log.w("Bhashini", "Failed: ${e.message}")
            TranslationResult("", false)
        }
    }
}
