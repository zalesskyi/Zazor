package com.example.zazor.ui.settings.pin

import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState

class PinCodeSetupContract {

    sealed class Event : UiEvent

    sealed class State : UiState {
        object Initial : State()
    }
}