package com.mediabrowser.app.di

import com.mediabrowser.app.data.api.MediaBrowserApi
import com.mediabrowser.app.data.repository.MediaBrowserRepositoryImpl
import com.mediabrowser.app.domain.repository.MediaBrowserRepository
import com.mediabrowser.app.shared.createRetrofit
import com.mediabrowser.app.BuildConfig
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import retrofit2.Retrofit

private const val BASE_URL = "https://ghibliapi.vercel.app/"

val DataModule = module {

    single<Retrofit> {
        val json = Json {
            isLenient = true
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
        createRetrofit(
            json = json,
            baseUrl = BASE_URL,
            enableLogging = BuildConfig.DEBUG
        )
    }

    single<MediaBrowserApi> {
        get<Retrofit>()
            .create(MediaBrowserApi::class.java)
    }


    single<MediaBrowserRepository> {
        MediaBrowserRepositoryImpl(api = get())
    }
}