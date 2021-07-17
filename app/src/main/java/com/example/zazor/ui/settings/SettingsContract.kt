package com.example.zazor.ui.settings

import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState

class SettingsContract {

    sealed class Event : UiEvent

    sealed class State : UiState {
        object Initial : State()
    }
}