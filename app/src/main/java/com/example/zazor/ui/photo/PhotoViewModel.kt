package com.example.zazor.ui.photo

import com.example.zazor.data.repositories.PhotoRepository
import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl
import com.example.zazor.ui.photo.editPhoto.EditPhotoContract
import kotlinx.coroutines.flow.SharedFlow

interface PhotoViewModel : BaseViewModel<PhotoContract.State, PhotoContract.Event>

class PhotoViewModelImpl(private val editPhotoFlow: SharedFlow<EditPhotoContract.Flow>,
                         private val photoRepository: PhotoRepository)
    : BaseViewModelImpl<PhotoContract.State, PhotoContract.Event>(),
    PhotoViewModel {

    override suspend fun initialState(): PhotoContract.State =
        PhotoContract.State.Initial(photoRepository.getLastPhoto()?.path)

    override fun onEventArrived(event: PhotoContract.Event?) {
        when (event) {
            is PhotoContract.Event.PermissionResult -> handlePermission(event.granted)
        }
    }

    private fun handlePermission(granted: Boolean) {
        uiState.value = if (granted) PhotoContract.State.PermissionGranted
        else PhotoContract.State.PermissionDenied
    }
}