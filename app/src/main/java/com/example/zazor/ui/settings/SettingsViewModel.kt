package com.example.zazor.ui.settings

import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl
import com.example.zazor.ui.media.MediaContract

interface SettingsViewModel : BaseViewModel<SettingsContract.State, SettingsContract.Event>

class SettingsViewModelImpl : BaseViewModelImpl<SettingsContract.State, SettingsContract.Event>(), SettingsViewModel {

    override suspend fun initialState(): SettingsContract.State = SettingsContract.State.Initial

    override fun onEventArrived(event: SettingsContract.Event?) = Unit
}