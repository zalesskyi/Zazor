package com.example.zazor.ui.settings.di

import com.example.zazor.ui.settings.SettingsActivity
import com.example.zazor.ui.settings.SettingsViewModel
import com.example.zazor.ui.settings.SettingsViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SettingsViewModelImpl() }
}

fun SettingsActivity.injectViewModel(): Lazy<SettingsViewModel> =
    lazy { getViewModel<SettingsViewModelImpl>(null) }