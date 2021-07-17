package com.example.zazor.ui.settings.list.di

import com.example.zazor.ui.settings.SettingsActivity
import com.example.zazor.ui.settings.SettingsViewModel
import com.example.zazor.ui.settings.SettingsViewModelImpl
import com.example.zazor.ui.settings.list.SettingsListFragment
import com.example.zazor.ui.settings.list.SettingsListViewModel
import com.example.zazor.ui.settings.list.SettingsListViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.dsl.module

val settingsListModule = module {
    viewModel { SettingsListViewModelImpl(get()) }
}

fun SettingsListFragment.injectViewModel(): Lazy<SettingsListViewModel> =
    lazy { getViewModel<SettingsListViewModelImpl>(null) }