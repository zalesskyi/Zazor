package com.example.zazor.ui.photo.collage.container.di


import com.example.zazor.ui.photo.collage.container.CollageContainerFragment
import com.example.zazor.ui.photo.collage.container.CollageContainerViewModel
import com.example.zazor.ui.photo.collage.container.CollageContainerViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module

val collageContainerModule = module {

    viewModel {
        CollageContainerViewModelImpl()
    }
}

fun CollageContainerFragment.injectViewModel(): Lazy<CollageContainerViewModel> =
    lazy { getViewModel<CollageContainerViewModelImpl>(null) }