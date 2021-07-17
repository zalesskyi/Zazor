package com.example.zazor.ui.photo.basic.di

import com.example.zazor.ui.photo.basic.BasicPhotoFragment
import com.example.zazor.ui.photo.basic.BasicPhotoViewModel
import com.example.zazor.ui.photo.basic.BasicPhotoViewModelImpl
import com.example.zazor.ui.photo.di.ADD_NOTE_FLOW
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val basicPhotoModule = module {

    viewModel {
        BasicPhotoViewModelImpl(
            androidApplication(),
            get(named(ADD_NOTE_FLOW)),
            get()
        )
    }
}

fun BasicPhotoFragment.injectViewModel(): Lazy<BasicPhotoViewModel> =
    lazy { getViewModel<BasicPhotoViewModelImpl>(null) }