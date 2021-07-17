package com.example.zazor.ui.photo.basic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import com.example.zazor.data.models.Photo
import com.example.zazor.data.repositories.PhotoRepository
import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl
import com.example.zazor.ui.photo.editPhoto.EditPhotoContract
import com.example.zazor.utils.camera.Camera
import com.example.zazor.utils.extensions.toBytes
import com.google.android.gms.location.LocationServices
import io.fotoapparat.configuration.UpdateConfiguration
import io.fotoapparat.selector.firstAvailable
import io.fotoapparat.selector.off
import io.fotoapparat.selector.torch
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import org.joda.time.DateTime
import java.io.BufferedOutputStream
import java.io.File
import java.io.IOException
import java.util.*

interface BasicPhotoViewModel : BaseViewModel<BasicPhotoContract.State, BasicPhotoContract.Event>

@SuppressLint("StaticFieldLeak")
class BasicPhotoViewModelImpl(
    private val context: Context,
    private val editPhotoFlow: MutableSharedFlow<EditPhotoContract.Flow>,
    private val photoRepository: PhotoRepository
) :
    BaseViewModelImpl<BasicPhotoContract.State, BasicPhotoContract.Event>(), BasicPhotoViewModel {

    companion object {

        const val DIR_PHOTOS = "photos"
    }

    private var activeCamera: Camera = Camera.Back

    private var isPreviewShown: Boolean = false

    private var isFlashOn: Boolean = false

    private var addNoteJob: Job? = null

    private var lastLocation: Location? = null

    private var photoTime: DateTime? = null

    override suspend fun initialState(): BasicPhotoContract.State? = null

    override fun init() {
        super.init()
        getLastLocation(context)
    }

    override fun onEventArrived(event: BasicPhotoContract.Event?) {
        when (event) {
            is BasicPhotoContract.Event.Resume -> subscribeToAddNoteFlow()
            is BasicPhotoContract.Event.FlipCamera -> handleCameraFlip()
            is BasicPhotoContract.Event.ToggleFlash -> handleFlashToggle()
            is BasicPhotoContract.Event.PhotoCaptured -> {
                uiState.value = BasicPhotoContract.State.ShowPreview(event.photo)
                isPreviewShown = true
                photoTime = DateTime.now()
            }
            is BasicPhotoContract.Event.SaveEdits -> saveEdits(event.bitmap)
            is BasicPhotoContract.Event.BackPressed -> handleBackPressed()
            is BasicPhotoContract.Event.Pause -> unSubscribeFromAddNoteFlow()
            is BasicPhotoContract.Event.Stop -> Unit
        }
    }

    private fun subscribeToAddNoteFlow() {
        addNoteJob = launch {
            editPhotoFlow.collect { flowState ->
                when (flowState) {
                    is EditPhotoContract.Flow.Cancel -> {
                        handleBackPressed()
                        //addNoteFlow.value = AddNoteContract.Flow.Idle
                    }
                    is EditPhotoContract.Flow.AddNote -> {
                        handleNoteAdding(flowState)
                        //handleBackPressed()
                        //addNoteFlow.value = AddNoteContract.Flow.Idle
                    }
                    is EditPhotoContract.Flow.AddOverlay -> {
                        handleOverlayAdding(flowState)
                    }
                    is EditPhotoContract.Flow.Done -> {
                        uiState.value = BasicPhotoContract.State.SaveNotes
                        //savePhoto()
                    }
                    is EditPhotoContract.Flow.AllowPaint -> {
                        uiState.value = BasicPhotoContract.State.AllowDraw(flowState.color)
                    }
                    is EditPhotoContract.Flow.DisallowPaint -> {
                        uiState.value = BasicPhotoContract.State.DisallowDraw
                    }
                    is EditPhotoContract.Flow.ClearPaint -> {
                        uiState.value = BasicPhotoContract.State.ClearDraw
                    }
                }
            }
        }
    }

    private fun handleNoteAdding(flowState: EditPhotoContract.Flow.AddNote) {
        lastLocation?.let { location ->
            photoTime?.let { date ->
                uiState.value = BasicPhotoContract.State.AddNotes(
                    flowState.note,
                    location.latitude.toString(),
                    location.longitude.toString(),
                    date.toString("dd MM YYYY")
                )
            }
        }
    }

    private fun handleOverlayAdding(flowState: EditPhotoContract.Flow.AddOverlay) {
        flowState.run {
            uiState.value = BasicPhotoContract.State.AddOverlay(text, color, fontId)
        }
    }

    private fun saveEdits(bitmap: Bitmap) {
        launch {
            val localPath = loadToLocalFile(bitmap.toBytes(), null)
            savePhoto(localPath)
            handleBackPressed()
        }
    }

    private fun unSubscribeFromAddNoteFlow() {
        addNoteJob?.cancel()
        addNoteJob = null
    }

    private fun handleCameraFlip() {
        activeCamera = when (activeCamera) {
            Camera.Front -> Camera.Back
            Camera.Back -> Camera.Front
        }
        uiState.value = BasicPhotoContract.State.FlipCamera(activeCamera)
    }

    private fun handleFlashToggle() {
        isFlashOn = !isFlashOn
        uiState.value = BasicPhotoContract.State.ToggleFlash(
            UpdateConfiguration(
                flashMode = if (isFlashOn) {
                    firstAvailable(
                        torch(),
                        off()
                    )
                } else {
                    off()
                }
            )
        )
    }

    private fun handleBackPressed() {
        uiState.value =
            if (isPreviewShown) BasicPhotoContract.State.HidePreview
            else BasicPhotoContract.State.Exit
        isPreviewShown = false
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(context: Context) {
        LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener {
            lastLocation = it
        }
    }

    private suspend fun savePhoto(path: String) {
        photoRepository.savePhoto(
            Photo(
                path,
                "",
                DateTime.now(),
                getAddress().orEmpty(),
                lastLocation?.latitude,
                lastLocation?.longitude
            )
        )
    }

    private fun getAddress() =
        lastLocation?.let {
            Geocoder(context, Locale.forLanguageTag("ru")).getFromLocation(it.latitude, it.longitude, 1).firstOrNull()?.run {
                "${getAddressLine(0)}, ${locality}, $countryCode"
            }
        }

    /**
     * @return absolute path to stored local file.
     */
    private fun loadToLocalFile(packet: ByteArray, name: String?): String =
        File(
            context.getExternalFilesDir(DIR_PHOTOS),
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