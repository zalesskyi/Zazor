package com.example.zazor.ui.main.di

import com.example.zazor.ui.main.MainActivity
import com.example.zazor.ui.main.MainViewModel
import com.example.zazor.ui.main.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module


val mainModule = module {
    viewModel { MainViewModelImpl(get()) }
}

fun MainActivity.injectViewModel(): Lazy<MainViewModel> =
      lazy { getViewModel<MainViewModelImpl>(null) }