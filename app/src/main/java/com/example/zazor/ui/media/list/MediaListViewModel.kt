package com.example.zazor.ui.media.list

import com.example.zazor.data.repositories.PhotoRepository
import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl
import com.example.zazor.ui.media.MediaContract

interface MediaListViewModel : BaseViewModel<MediaListContract.State, MediaListContract.Event>

class MediaListViewModelImpl(private val photoRepository: PhotoRepository) : BaseViewModelImpl<MediaListContract.State, MediaListContract.Event>(), MediaListViewModel {

    override suspend fun initialState(): MediaListContract.State =
        MediaListContract.State.Initial(photoRepository.getPhotos())

    override fun onEventArrived(event: MediaListContract.Event?) = Unit
}