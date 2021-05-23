package com.example.zazor

import android.app.Application
import com.google.firebase.FirebaseApp
import com.example.zazor.di.DependencyInjection

class ZazorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DependencyInjection.init(this)
        FirebaseApp.initializeApp(this)
    }
}