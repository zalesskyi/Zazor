package com.example.zazor.ui.photo.basic

import android.graphics.Bitmap
import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState
import com.example.zazor.utils.camera.Camera
import io.fotoapparat.configuration.UpdateConfiguration
import io.fotoapparat.result.BitmapPhoto
import io.fotoapparat.selector.FlashSelector

class BasicPhotoContract {

    sealed class Event : UiEvent {
        object Resume : Event()

        class PhotoCaptured(val photo: BitmapPhoto) : Event()

        class SaveEdits(val bitmap: Bitmap) : Event()

        object FlipCamera : Event()

        object ToggleFlash : Event()

        object BackPressed : Event()

        object Pause : Event()

        object Stop : Event()
    }

    sealed class State : UiState {

        object Initial : State()

        class FlipCamera(val configuration: Camera) : State()

        class ToggleFlash(val config: UpdateConfiguration) : State()

        class ShowPreview(val photo: BitmapPhoto) : State()

        object SaveNotes : State()

        class AddNotes(val notes: String,
                       val lat: String,
                       val long: String,
                       val date: String) : State()

        class AddOverlay(val text: String?,
                         val color: Int?,
                         val fontId: Int?) : State()

        data class AllowDraw(val color: Int?) : State()

        object DisallowDraw : State()

        object ClearDraw : State()

        object HidePreview : State()

        object Exit : State()
    }
}