package com.example.zazor.ui.auth

import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState

class AuthContract {

    sealed class Event : UiEvent {


    }

    sealed class State : UiState {
        object Initial : State()
    }
}