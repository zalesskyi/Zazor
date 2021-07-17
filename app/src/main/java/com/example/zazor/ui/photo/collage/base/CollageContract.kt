package com.example.zazor.ui.photo.collage.base

import android.graphics.Bitmap
import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState

class CollageContract {

    sealed class Event : UiEvent {

        data class Initial(val gridSize: Int) : Event()

        data class PhotoCaptured(val bitmap: Bitmap) : Event()

        object PreviewShown : Event()

        class SaveEdits(val bitmap: Bitmap) : Event()
    }

    sealed class State : UiState {

        data class ShowPreview(val bitmap: Bitmap, val index: Int) : State()

        data class ShowCamera(val index: Int) : State()

        object CaptureCollage : State()
    }
}