package com.example.zazor.ui.settings.list

import com.example.zazor.R
import com.example.zazor.data.models.MainSetting
import com.example.zazor.data.models.MainSettingType
import com.example.zazor.data.prefs.AppPreferences
import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl

interface SettingsListViewModel :
    BaseViewModel<SettingsListContract.State, SettingsListContract.Event>

class SettingsListViewModelImpl(appPrefs: AppPreferences) :
    BaseViewModelImpl<SettingsListContract.State, SettingsListContract.Event>(),
    SettingsListViewModel {

    private val settings = listOf(
        MainSetting(MainSettingType.PIN_CODE, R.string.pin_code_setting, appPrefs.getPin() != null),
        MainSetting(MainSettingType.NOTES, R.string.notes_setting),
        MainSetting(MainSettingType.CLEAR_CODE, R.string.clear_code_setting, appPrefs.getClearCode() != null)
    )

    override suspend fun initialState(): SettingsListContract.State =
        SettingsListContract.State.Initial(settings)

    override fun onEventArrived(event: SettingsListContract.Event?) = Unit
}