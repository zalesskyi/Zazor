package com.example.zazor.ui.main

import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState

class MainContract {

    sealed class Event : UiEvent {

    }

    sealed class State : UiState {
        object Initial : State()
    }
}