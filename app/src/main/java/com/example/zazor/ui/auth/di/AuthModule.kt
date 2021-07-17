package com.example.zazor.ui.auth.di

import com.example.zazor.ui.auth.AuthActivity
import com.example.zazor.ui.auth.AuthViewModel
import com.example.zazor.ui.auth.AuthViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { AuthViewModelImpl() }
}

fun AuthActivity.injectViewModel(): Lazy<AuthViewModel> =
    lazy { getViewModel<AuthViewModelImpl>(null) }