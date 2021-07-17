package com.example.zazor.ui.photo.collage.container

import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl
import com.example.zazor.ui.photo.collage.base.CollageContract

interface CollageContainerViewModel : BaseViewModel<CollageContract.State, CollageContract.Event>

class CollageContainerViewModelImpl : BaseViewModelImpl<CollageContract.State, CollageContract.Event>(),
    CollageContainerViewModel {

    override suspend fun initialState(): CollageContract.State? = null

    override fun onEventArrived(event: CollageContract.Event?) {

    }
}