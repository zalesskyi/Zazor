package com.example.zazor.ui.photo.collage

import android.widget.ImageView
import com.example.zazor.R
import com.example.zazor.databinding.FragmentHorizontalCollageBinding
import com.example.zazor.ui.photo.collage.base.CollageFragment
import com.example.zazor.utils.viewBinding.viewBinding
import io.fotoapparat.view.CameraView

class HorizontalCollageFragment : CollageFragment(R.layout.fragment_horizontal_collage) {

    private val binding by viewBinding(FragmentHorizontalCollageBinding::bind)

    override val cameraViews: List<CameraView> by lazy {
        listOf(binding.cvFirst, binding.cvSecond)
    }

    override val previewViews: List<ImageView> by lazy {
        listOf(binding.ivFirst, binding.ivSecond)
    }

    override val collagePreview by lazy {
        binding.clCollagePreview
    }
}