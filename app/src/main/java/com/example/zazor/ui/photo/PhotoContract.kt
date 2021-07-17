package com.example.zazor.ui.photo

import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState
import com.example.zazor.ui.photo.basic.BasicPhotoContract

class PhotoContract {

    sealed class Event : UiEvent {

        class PermissionResult(val granted: Boolean) : Event()
    }

    sealed class State : UiState {
        data class Initial(val photoUri: String?) : State()

        object PermissionGranted : State()

        object PermissionDenied : State()
    }
}