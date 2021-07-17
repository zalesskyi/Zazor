package com.example.zazor.ui.settings.pin.di

import com.example.zazor.ui.settings.SettingsViewModel
import com.example.zazor.ui.settings.SettingsViewModelImpl
import com.example.zazor.ui.settings.pin.PinCodeSetupFragment
import com.example.zazor.ui.settings.pin.PinCodeSetupViewModel
import com.example.zazor.ui.settings.pin.PinCodeSetupViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module

val pinCodeSetupModule = module {
    viewModel { PinCodeSetupViewModelImpl() }
}

fun PinCodeSetupFragment.injectViewModel(): Lazy<PinCodeSetupViewModel> =
    lazy { getViewModel<PinCodeSetupViewModelImpl>(null) }