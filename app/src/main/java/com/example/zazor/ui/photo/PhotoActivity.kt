package com.example.zazor.ui.photo

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.zazor.R
import com.example.zazor.databinding.ActivityPhotoBinding
import com.example.zazor.databinding.BottomSheetAddNoteBinding
import com.example.zazor.ui.base.BaseActivity
import com.example.zazor.ui.media.MediaActivity
import com.example.zazor.ui.photo.collage.container.CollageContainerListener
import com.example.zazor.ui.photo.editPhoto.EditPhotoBottomSheet
import com.example.zazor.ui.photo.di.injectViewModel
import com.example.zazor.ui.settings.SettingsActivity
import com.example.zazor.utils.extensions.gone
import com.example.zazor.utils.extensions.hide
import com.example.zazor.utils.extensions.loadImage
import com.example.zazor.utils.extensions.show
import com.example.zazor.utils.viewBinding.viewBinding

class PhotoActivity : BaseActivity<PhotoContract.State, PhotoContract.Event>(R.layout.activity_photo),
    PhotoCallback {

    companion object {

        private const val BASIC_PHOTO_ITEM = 1
    }

    override val viewModel by injectViewModel()

    private var adapter: PhotoPagerAdapter? = null

    private val binding by viewBinding(ActivityPhotoBinding::bind)

    private val sheetBinding by viewBinding(BottomSheetAddNoteBinding::bind) {
        it.findViewById(R.id.clRoot)
    }

    private val addNoteSheet by lazy {
        getCurrentPhotoHandler()?.let {
            EditPhotoBottomSheet(sheetBinding, it)
        } ?: error("Fragments are not initialized")
    }

    private val singlePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
        viewModel.sendEvent(PhotoContract.Event.PermissionResult(granted.values.all { true }))
    }

    override fun observeState(state: PhotoContract.State?) {
        when (state) {
            is PhotoContract.State.PermissionGranted -> {
                setupViewPager()
            }
            is PhotoContract.State.PermissionDenied -> showPermissionToast()
            is PhotoContract.State.Initial -> {
                state.photoUri?.let { uri ->
                    binding.ivLastPhoto.loadImage(uri)
                } ?: binding.ivLastPhoto.hide()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivCapture.setOnClickListener {
            getCurrentPhotoHandler()?.onCapturePhoto()
        }
        binding.ivFlip.setOnClickListener {
            getCurrentPhotoHandler()?.flipCamera()
        }
        binding.ivGrid.setOnClickListener {
            (adapter?.getItem(binding.vpPhoto.currentItem) as? CollageContainerListener)?.onGridSelected()
        }
        binding.ivGridHorizontal.setOnClickListener {
            (adapter?.getItem(binding.vpPhoto.currentItem) as? CollageContainerListener)?.onHorizontalSelected()
        }
        binding.ivGridVertical.setOnClickListener {
            (adapter?.getItem(binding.vpPhoto.currentItem) as? CollageContainerListener)?.onVerticalSelected()
        }
        binding.ivLastPhoto.setOnClickListener {
            openMedia()
        }
    }

    override fun onStart() {
        super.onStart()
        singlePermission.launch(arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION))
    }

    override fun onCaptured() {
        binding.clPhotoPanel.gone()
        addNoteSheet.show()
    }

    override fun onCollageShown() {
        binding.clCollageGrid.show()
    }

    override fun onPanoramaShown() {
        binding.clCollageGrid.hide()
    }

    override fun onPhotoShown() {
        binding.clCollageGrid.hide()
    }

    override fun openSettings() {
        startActivity(SettingsActivity.newIntent(this))
    }

    override fun onPhotoEditCancel() {
        addNoteSheet.clearAll()
        addNoteSheet.hide()
        binding.clPhotoPanel.show()
    }

    override fun clearAll() {
        addNoteSheet.clearAll()
    }

    private fun setupViewPager() {
        adapter ?: run {
            adapter = PhotoPagerAdapter(this, supportFragmentManager)
            binding.vpPhoto.adapter = adapter
            binding.vpPhoto.setCurrentItem(BASIC_PHOTO_ITEM, false)
            binding.tlPhotos.setViewPager(binding.vpPhoto)
        }
    }

    private fun openMedia() {
        startActivity(MediaActivity.newIntent(this))
    }

    private fun showPermissionToast() {
        Toast.makeText(this, R.string.give_permission, Toast.LENGTH_LONG).show()
    }

    private fun getCurrentPhotoHandler(): PhotoHandler? =
        adapter?.getItem(binding.vpPhoto.currentItem) as? PhotoHandler
}