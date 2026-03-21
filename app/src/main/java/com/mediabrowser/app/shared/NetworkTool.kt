package com.mediabrowser.app.shared

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val TIMEOUT_IN_SEC = 60L
private const val CONTENT_TYPE = "application/json"

fun createRetrofit(
    baseUrl: String,
    json: Json,
    enableLogging: Boolean,
    contentType: String = CONTENT_TYPE,
    timeOutInSec: Long = TIMEOUT_IN_SEC
): Retrofit {
    val clientBuilder = OkHttpClient.Builder()
        .connectTimeout(timeOutInSec, TimeUnit.SECONDS)
        .writeTimeout(timeOutInSec, TimeUnit.SECONDS)
        .readTimeout(timeOutInSec, TimeUnit.SECONDS)

    if (enableLogging) {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        clientBuilder.addInterceptor(logging)
    }

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            json.asConverterFactory(contentType.toMediaType())
        )
        .client(clientBuilder.build())
        .build()
}