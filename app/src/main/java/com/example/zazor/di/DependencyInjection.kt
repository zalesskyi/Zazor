package com.example.zazor.di

import android.app.Application
import com.example.zazor.data.di.prefsModule
import com.example.zazor.ui.main.di.mainModule
import com.example.zazor.ui.media.di.mediaModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object DependencyInjection {

    fun init(application: Application) {
        startKoin {
            androidContext(application)
            modules(prefsModule, mainModule, mediaModule)
        }
    }
}