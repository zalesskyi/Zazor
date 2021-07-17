package com.example.zazor.ui.photo.collage.di

import com.example.zazor.ui.photo.collage.base.CollageFragment
import com.example.zazor.ui.photo.collage.base.CollageViewModel
import com.example.zazor.ui.photo.collage.base.CollageViewModelImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module

val collageModule = module {

    viewModel {
        CollageViewModelImpl(androidApplication(), get())
    }
}

fun CollageFragment.injectViewModel(): Lazy<CollageViewModel> =
    lazy { getViewModel<CollageViewModelImpl>(null) }