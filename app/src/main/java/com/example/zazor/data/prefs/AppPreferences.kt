package com.example.zazor.data.prefs

import android.annotation.SuppressLint
import android.content.Context

interface AppPreferences {

    fun putPin(pin: String)

    fun getPin(): String?

    fun putClearCode(code: String)

    fun getClearCode(): String?

    fun clear()
}

@SuppressLint("ApplySharedPref")
class AppPreferencesImpl(context: Context) : AppPreferences {

    companion object {

        private const val PREFS = "PREFS"
        private const val PIN_KEY = "pinCode"
        private const val CLEAR_CODE_KEY = "clearCode"
    }

    private val preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    override fun putPin(pin: String) {
        preferences.edit().putString(PIN_KEY, pin).commit()
    }

    override fun getPin(): String? {
        return preferences.getString(PIN_KEY, null)
    }

    override fun putClearCode(code: String) {
        preferences.edit().putString(CLEAR_CODE_KEY, code).commit()
    }

    override fun getClearCode(): String? {
        return preferences.getString(CLEAR_CODE_KEY, null)
    }

    override fun clear() {
        preferences.edit().clear().commit()
    }
}