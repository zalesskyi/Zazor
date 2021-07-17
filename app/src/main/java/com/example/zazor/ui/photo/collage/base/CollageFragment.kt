package com.example.zazor.ui.photo.collage.base

import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.zazor.ui.base.BaseFragment
import com.example.zazor.ui.photo.PhotoHandler
import com.example.zazor.ui.photo.collage.di.injectViewModel
import com.example.zazor.utils.extensions.*
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.selector.continuousFocusPicture
import io.fotoapparat.selector.highestResolution
import io.fotoapparat.view.CameraView

abstract class CollageFragment(layoutRes: Int) : BaseFragment<CollageContract.State, CollageContract.Event>(layoutRes),
    PhotoHandler {

    abstract val cameraViews: List<CameraView>

    abstract val previewViews: List<ImageView>

    abstract val collagePreview: View

    private var camera: Fotoapparat? = null

    override val viewModel by injectViewModel()

    override fun observeState(state: CollageContract.State?) {
        when (state) {
            is CollageContract.State.ShowPreview -> {
                cameraViews[state.index].invisible()
                previewViews[state.index].run {
                    show()
                    setImageBitmap(state.bitmap)
                }
                viewModel.sendEvent(CollageContract.Event.PreviewShown)
            }
            is CollageContract.State.ShowCamera -> {
                camera?.stop()
                setupCamera(cameraViews[state.index])
            }
            is CollageContract.State.CaptureCollage -> {
                collagePreview.getBitmap()?.let {
                    viewModel.sendEvent(CollageContract.Event.SaveEdits(it))
                    previewViews.forEach {
                        it.isVisible = false
                    }
                    cameraViews.forEach {
                        it.textureView()?.alpha = 0F
                        it.isVisible = true
                    }
                    setupCamera(cameraViews.first())
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sendEvent(CollageContract.Event.Initial(cameraViews.size))
    }

    override fun onResume() {
        super.onResume()
        setupCamera(cameraViews.first())
    }

    override fun onCapturePhoto() {
        capturePhoto()
    }

    override fun flipCamera() = Unit

    private fun setupCamera(view: CameraView) {
        camera = Fotoapparat(
            context = requireContext(),
            cameraConfiguration = CameraConfiguration(pictureResolution = highestResolution()),
            view = view,
            scaleType = ScaleType.CenterCrop,
            lensPosition = back()
        )
        view.textureView()?.alpha = 1F
        camera?.start()
    }

    private fun capturePhoto() {
        camera?.run {
            autoFocus().takePicture()
        }?.let { photoResult ->
            photoResult
                .toBitmap()
                .whenAvailable { photo ->
                    photo?.let {
                        camera?.updateConfiguration(CameraConfiguration(focusMode = continuousFocusPicture()))
                        viewModel.sendEvent(CollageContract.Event.PhotoCaptured(it.bitmap))
                    }
                }
        }
    }

    private fun CameraView.textureView() = children.firstOrNull() as? TextureView
}