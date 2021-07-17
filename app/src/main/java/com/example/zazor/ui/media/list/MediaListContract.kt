package com.example.zazor.ui.media.list

import com.example.zazor.data.models.Photo
import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState

class MediaListContract {

    sealed class Event : UiEvent

    sealed class State : UiState {

        data class Initial(val photos: List<Photo>) : State()
    }
}