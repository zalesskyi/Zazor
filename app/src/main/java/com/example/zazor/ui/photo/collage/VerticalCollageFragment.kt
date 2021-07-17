package com.example.zazor.ui.photo.collage

import android.view.View
import android.widget.ImageView
import com.example.zazor.R
import com.example.zazor.databinding.FragmentVerticalCollageBinding
import com.example.zazor.ui.photo.collage.base.CollageFragment
import com.example.zazor.utils.viewBinding.viewBinding
import io.fotoapparat.view.CameraView

class VerticalCollageFragment : CollageFragment(R.layout.fragment_vertical_collage) {

    private val binding by viewBinding(FragmentVerticalCollageBinding::bind)

    override val cameraViews: List<CameraView> by lazy {
        listOf(binding.cvFirst, binding.cvFour)
    }

    override val previewViews: List<ImageView> by lazy {
        listOf(binding.ivFirst, binding.ivFour)
    }

    override val collagePreview by lazy {
        binding.clCollagePreview
    }
}