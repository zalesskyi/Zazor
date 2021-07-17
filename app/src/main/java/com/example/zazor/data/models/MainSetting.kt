package com.example.zazor.data.models

import androidx.annotation.StringRes

enum class MainSettingType {
    PIN_CODE, NOTES, CLEAR_CODE
}

data class MainSetting(val type: MainSettingType,
                       @StringRes
                       val stringRes: Int,
                       val isChecked: Boolean? = null)