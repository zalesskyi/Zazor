package com.example.zazor.ui.photo.basic

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.cleveroad.droidart.ShowButtonOnSelector
import com.example.zazor.R
import com.example.zazor.databinding.FragmentBasicPhotoBinding
import com.example.zazor.ui.base.BaseFragment
import com.example.zazor.ui.photo.PhotoHandler
import com.example.zazor.ui.photo.basic.di.injectViewModel
import com.example.zazor.ui.photo.editPhoto.*
import com.example.zazor.utils.extensions.*
import com.example.zazor.utils.viewBinding.viewBinding
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.result.BitmapPhoto
import io.fotoapparat.selector.*
import kotlinx.android.synthetic.main.fragment_basic_photo.*

class BasicPhotoFragment :
    BaseFragment<BasicPhotoContract.State, BasicPhotoContract.Event>(R.layout.fragment_basic_photo),
    PhotoHandler {

    override val screenTitle = R.string.photo

    override val viewModel by injectViewModel()

    private var camera: Fotoapparat? = null

    private val binding by viewBinding(FragmentBasicPhotoBinding::bind)

    override fun observeState(state: BasicPhotoContract.State?) {
        when (state) {
            is BasicPhotoContract.State.FlipCamera -> state.configuration.run {
                camera?.switchTo(lensPosition = lensPosition, cameraConfiguration = configuration)
            }
            is BasicPhotoContract.State.ToggleFlash -> camera?.updateConfiguration(
                state.config
            )
            is BasicPhotoContract.State.AddNotes -> {
                binding.run {
                    clPreviewContainer.show()
                    llNotesContainer.showOrHide(state.notes.isNotEmpty())
                    tvLat.text = state.lat
                    tvLong.text = state.long
                    tvDate.text = state.date
                    tvNote.text = state.notes
                }
            }
            is BasicPhotoContract.State.AddOverlay -> {
                binding.evDroidArt.run {
                    show()
                    state.text?.let {
                        text = it
                    }
                    state.fontId?.let {
                        fontId = it
                    }
                    state.color?.let {
                        textColor = it
                    }
                }
            }
            is BasicPhotoContract.State.AllowDraw -> {
                binding.vDraw.run {
                    isVisible = true
                    isPaintAllowed = true
                    state.color?.let {
                        colorRes = it
                    }
                }
            }
            is BasicPhotoContract.State.DisallowDraw -> {
                binding.vDraw.isPaintAllowed = false
            }
            is BasicPhotoContract.State.SaveNotes -> {
                clPreviewContainer.getBitmap()?.let {
                    viewModel.sendEvent(BasicPhotoContract.Event.SaveEdits(it))
                }
            }
            is BasicPhotoContract.State.ClearDraw -> {
                binding.vDraw.clear()
            }
            is BasicPhotoContract.State.Initial -> Unit
            is BasicPhotoContract.State.ShowPreview -> showPreview(state.photo)
            is BasicPhotoContract.State.HidePreview -> hidePreview()
            is BasicPhotoContract.State.Exit -> activity?.onBackPressed()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            ivFlash.setOnClickListener {
                viewModel.sendEvent(BasicPhotoContract.Event.ToggleFlash)
            }
            ivBack.setOnClickListener {
                viewModel.sendEvent(BasicPhotoContract.Event.BackPressed)
            }
            tvClearAll.setOnClickListener {
                callback?.clearAll()
            }
            ivSettings.setOnClickListener {
                callback?.openSettings()
            }
        }
        setupDroidArt()
    }

    override fun onResume() {
        super.onResume()
        setupCamera()
        callback?.onPhotoShown()
        viewModel.sendEvent(BasicPhotoContract.Event.Resume)
    }

    override fun onPause() {
        super.onPause()
        camera?.stop()
        camera = null
        viewModel.sendEvent(BasicPhotoContract.Event.Pause)
    }

    override fun onStop() {
        super.onStop()
        viewModel.sendEvent(BasicPhotoContract.Event.Stop)
    }

    override fun onCapturePhoto() {
        capturePhoto()
    }

    override fun flipCamera() {
        viewModel.sendEvent(BasicPhotoContract.Event.FlipCamera)
    }

    private fun setupCamera() {
        camera = Fotoapparat(
            context = requireContext(),
            focusView = binding.focusView,
            cameraConfiguration = CameraConfiguration(pictureResolution = highestResolution()),
            view = binding.vCamera,
            scaleType = ScaleType.CenterCrop,
            lensPosition = back()
        )
        camera?.start()
    }

    private fun setupDroidArt() {
        with(binding.evDroidArt) {
            setPathEffectForSelector(
                DashPathEffect(
                    floatArrayOf(DASH_PATH_ON_DISTANCE, DASH_PATH_OFF_DISTANCE),
                    DASH_PATH_PHASE
                )
            )
            setStrokeWidthForDashLine(STROKE_WIDTH_FOR_DASH_LINE)
            setColorForTextShadow(Color.GRAY)
            setColorForSelectorButton(SELECTOR_BUTTON_COLOR_DEFAULT)
            setColorForDashLine(SELECTOR_BUTTON_COLOR_DEFAULT)
            showScaleRotateButton(ShowButtonOnSelector.HIDE_BUTTON)
            showResetViewTextButton(ShowButtonOnSelector.HIDE_BUTTON)
            showChangeViewTextButton(ShowButtonOnSelector.HIDE_BUTTON)
            setColorForSelector(Color.TRANSPARENT)
        }
    }

    private fun showPreview(preview: BitmapPhoto) {
        camera?.stop()
        callback?.onCaptured()
        with(binding) {
            vCamera.hide()
            toggleSettingsPanelVisibility(false)
            clPreviewContainer.show()
            ivPreview.run {
                show()
                setImageBitmap(preview.bitmap)
            }
        }
    }

    private fun hidePreview() {
        setupCamera()
        callback?.onPhotoEditCancel()
        with(binding) {
            toggleSettingsPanelVisibility(true)
            vCamera.show()
            clPreviewContainer.hide()
            vDraw.hide()
            ivPreview.hide()
        }
    }

    private fun toggleSettingsPanelVisibility(isVisible: Boolean) {
        with(binding) {
            ivBack.isVisible = !isVisible
            tvClearAll.isVisible = !isVisible
            ivFlash.isVisible = isVisible
            ivSettings.isVisible = isVisible
        }
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
                        viewModel.sendEvent(BasicPhotoContract.Event.PhotoCaptured(it))
                    }
                }
        }
    }
}