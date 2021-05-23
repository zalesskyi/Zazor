package com.example.zazor.data.prefs

import android.annotation.SuppressLint
import android.content.Context

interface AppPreferences {

    fun putUserData(email: String?)

    fun getUserEmail(): String?

    fun clear()
}

@SuppressLint("ApplySharedPref")
class AppPreferencesImpl(context: Context) : AppPreferences {

    companion object {

        private const val PREFS = "PREFS"
        private const val EMAIL_KEY = "email"
    }

    private val preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    override fun putUserData(email: String?) {
        preferences.edit().putString(EMAIL_KEY, email).commit()
    }

    override fun getUserEmail(): String? =
          preferences.getString(EMAIL_KEY, null)

    override fun clear() {
        preferences.edit().clear().commit()
    }
}