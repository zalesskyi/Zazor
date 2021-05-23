package com.example.zazor.ui.media

import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState

class MediaContract {

    sealed class Event : UiEvent

    sealed class State : UiState {
        object Initial : State()
    }
}