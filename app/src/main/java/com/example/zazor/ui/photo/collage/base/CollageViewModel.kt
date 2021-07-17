package com.example.zazor.ui.photo.collage.base

import android.content.Context
import android.graphics.Bitmap
import com.example.zazor.data.models.Photo
import com.example.zazor.data.repositories.PhotoRepository
import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl
import com.example.zazor.ui.photo.basic.BasicPhotoViewModelImpl
import com.example.zazor.utils.extensions.toBytes
import org.joda.time.DateTime
import java.io.BufferedOutputStream
import java.io.File
import java.io.IOException

interface CollageViewModel : BaseViewModel<CollageContract.State, CollageContract.Event>

class CollageViewModelImpl(private val context: Context,
                           private val photoRepository: PhotoRepository
) : BaseViewModelImpl<CollageContract.State, CollageContract.Event>(), CollageViewModel {

    private var photoCounter = 0

    private var gridSize = 0

    override suspend fun initialState(): CollageContract.State? = null

    override fun onEventArrived(event: CollageContract.Event?) {
        when (event) {
            is CollageContract.Event.Initial -> {
                gridSize = event.gridSize
            }
            is CollageContract.Event.PhotoCaptured -> {
                uiState.value = CollageContract.State.ShowPreview(event.bitmap, photoCounter)
            }
            is CollageContract.Event.PreviewShown -> {
                uiState.value = if (++photoCounter == gridSize) {
                    CollageContract.State.CaptureCollage
                } else {
                    CollageContract.State.ShowCamera(photoCounter)
                }
            }
            is CollageContract.Event.SaveEdits -> {
                saveEdits(event.bitmap)
            }
        }
    }

    private fun saveEdits(bitmap: Bitmap) {
        launch {
            val localPath = loadToLocalFile(bitmap.toBytes(), null)
            savePhoto(localPath)
        }
    }

    private suspend fun savePhoto(path: String) {
        photoRepository.savePhoto(
            Photo(
                path,
                "",
                DateTime.now()
            )
        )
    }

    /**
     * @return absolute path to stored local file.
     */
    private fun loadToLocalFile(packet: ByteArray, name: String?): String =
        File(
            context.getExternalFilesDir(BasicPhotoViewModelImpl.DIR_PHOTOS),
            (name ?: DateTime.now().millis.toString()) + ".jpg"
        ).also { file ->
            saveImage(packet, file.outputStream().buffered())
        }.absolutePath

    @Throws(IOException::class)
    private fun saveImage(bytes: ByteArray, outputStream: BufferedOutputStream) {
        outputStream.use {
            it.write(bytes)
            it.flush()
        }
    }
}