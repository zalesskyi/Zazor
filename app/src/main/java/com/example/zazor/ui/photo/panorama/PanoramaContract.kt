package com.example.zazor.ui.photo.panorama

import com.example.zazor.ui.base.UiEvent
import com.example.zazor.ui.base.UiState
import com.example.zazor.ui.photo.basic.BasicPhotoContract

class PanoramaContract {

    sealed class Event : UiEvent

    sealed class State : UiState
}