package com.example.zazor.ui.photo.panorama.di

import com.example.zazor.ui.photo.basic.BasicPhotoFragment
import com.example.zazor.ui.photo.basic.BasicPhotoViewModel
import com.example.zazor.ui.photo.basic.BasicPhotoViewModelImpl
import com.example.zazor.ui.photo.di.ADD_NOTE_FLOW
import com.example.zazor.ui.photo.panorama.PanoramaFragment
import com.example.zazor.ui.photo.panorama.PanoramaViewModel
import com.example.zazor.ui.photo.panorama.PanoramaViewModelImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val panoramaModule = module {

    viewModel {
        PanoramaViewModelImpl()
    }
}

fun PanoramaFragment.injectViewModel(): Lazy<PanoramaViewModel> =
    lazy { getViewModel<PanoramaViewModelImpl>(null) }