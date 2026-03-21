package com.mediabrowser.app

import android.app.Application
import com.mediabrowser.app.di.DataModule
import com.mediabrowser.app.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MediaBrowserApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MediaBrowserApp)
            androidLogger(Level.ERROR)
            modules(
                listOf(
                    PresentationModule,
                    DataModule,
                )
            )
        }
    }
}