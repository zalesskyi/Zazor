package com.example.zazor.ui.photo.panorama

import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl

interface PanoramaViewModel : BaseViewModel<PanoramaContract.State, PanoramaContract.Event>

class PanoramaViewModelImpl : BaseViewModelImpl<PanoramaContract.State, PanoramaContract.Event>(),
    PanoramaViewModel {

    override suspend fun initialState(): PanoramaContract.State? = null

    override fun onEventArrived(event: PanoramaContract.Event?) = Unit
}