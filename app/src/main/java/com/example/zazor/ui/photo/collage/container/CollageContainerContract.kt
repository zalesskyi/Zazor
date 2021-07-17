package com.example.zazor.ui.photo.collage.container

import android.graphics.Bitmap
import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState

class CollageContainerContract {

    sealed class Event : UiEvent

    sealed class State : UiState
}