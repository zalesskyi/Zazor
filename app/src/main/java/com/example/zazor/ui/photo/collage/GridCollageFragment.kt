package com.example.zazor.ui.photo.collage

import android.widget.ImageView
import com.example.zazor.R
import com.example.zazor.databinding.FragmentGridCollageBinding
import com.example.zazor.ui.photo.collage.base.CollageFragment
import com.example.zazor.utils.viewBinding.viewBinding
import io.fotoapparat.view.CameraView

class GridCollageFragment : CollageFragment(R.layout.fragment_grid_collage) {

    private val binding by viewBinding(FragmentGridCollageBinding::bind)

    override val cameraViews: List<CameraView> by lazy {
        listOf(binding.cvFirst, binding.cvSecond, binding.cvThird, binding.cvFour)
    }

    override val previewViews: List<ImageView> by lazy {
        listOf(binding.ivFirst, binding.ivSecond, binding.ivThird, binding.ivFour)
    }

    override val collagePreview by lazy {
        binding.clCollagePreview
    }
}