package com.example.zazor.ui.settings.list

import com.example.zazor.data.models.MainSetting
import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState

class SettingsListContract {

    sealed class Event : UiEvent

    sealed class State : UiState {
        data class Initial(val settings: List<MainSetting>) : State()
    }
}