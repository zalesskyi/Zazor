package com.example.zazor.di

import android.app.Application
import com.example.zazor.data.di.prefsModule
import com.example.zazor.data.di.repositoriesModule
import com.example.zazor.ui.photo.di.photoModule
import com.example.zazor.ui.media.di.mediaModule
import com.example.zazor.ui.media.list.di.mediaListModule
import com.example.zazor.ui.photo.basic.di.basicPhotoModule
import com.example.zazor.ui.photo.collage.container.di.collageContainerModule
import com.example.zazor.ui.photo.collage.di.collageModule
import com.example.zazor.ui.photo.panorama.di.panoramaModule
import com.example.zazor.ui.settings.di.settingsModule
import com.example.zazor.ui.settings.list.di.settingsListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

object DependencyInjection {

    fun init(application: Application) {
        startKoin {
            androidContext(application)
            modules(prefsModule, repositoriesModule, photoModule,
                basicPhotoModule, panoramaModule, collageContainerModule,
                collageModule, mediaModule, mediaListModule, settingsModule, settingsListModule)
        }
    }
}