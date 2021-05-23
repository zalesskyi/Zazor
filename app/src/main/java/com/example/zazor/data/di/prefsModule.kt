package com.example.zazor.data.di

import com.example.zazor.data.prefs.AppPreferences
import com.example.zazor.data.prefs.AppPreferencesImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val prefsModule = module {
    single<AppPreferences> { AppPreferencesImpl(androidApplication()) }
}