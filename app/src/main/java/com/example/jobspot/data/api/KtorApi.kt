package com.example.jobspot.data.api

import android.util.Log
import com.example.jobspot.ui.models.ApiResponse
import com.example.jobspot.ui.models.JobSurrogate
import com.example.jobspot.utils.modifyJson
import io.ktor.client.*
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorApi {

    private const val BASE_URL = "https://testapi.getlokalapp.com/common/jobs?page=1"

    val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val client by lazy {
        HttpClient(Android) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }

            install(ContentNegotiation) {
                json(json)
//                gson {
//                    //Configure GSON Options
//                    setPrettyPrinting()
//                    disableHtmlEscaping()
//                }
            }
        }
    }

    private suspend fun getJobs(): HttpResponse {
        return client.get(BASE_URL)
    }

    private suspend fun exam(): ApiResponse {
        val response = getJobs().bodyAsText()
        Log.i("TAG", response)
        val modifiedJson = modifyJson(response)
        Log.i("TAG", modifiedJson)
        val decodedString = json.decodeFromString<ApiResponse>(modifiedJson)
        Log.i("TAG", decodedString.toString())

        return decodedString
    }

    suspend fun getOnlyJobs(): List<JobSurrogate.Job> {
        return exam().results.filterIsInstance<JobSurrogate.Job>()
    }

    suspend fun getOnlyAds(): List<JobSurrogate.Ad> {
        return exam().results.filterIsInstance<JobSurrogate.Ad>()
    }

}