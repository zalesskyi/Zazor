package com.example.zazor.ui.photo.panorama

import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.dermandar.dmd_lib.CallbackInterfaceShooter
import com.dermandar.dmd_lib.DMD_Capture
import com.example.zazor.R
import com.example.zazor.databinding.FragmentPanoramaBinding
import com.example.zazor.ui.base.BaseFragment
import com.example.zazor.ui.photo.panorama.di.injectViewModel
import com.example.zazor.utils.SimpleShooterCallback
import com.example.zazor.utils.extensions.hide
import com.example.zazor.utils.viewBinding.viewBinding
import com.nativesystem.YinYangLib
import java.util.HashMap

class PanoramaFragment : BaseFragment<PanoramaContract.State, PanoramaContract.Event>(R.layout.fragment_panorama) {

    override val viewModel by injectViewModel()

    override val screenTitle = R.string.panorama

    private val binding by viewBinding(FragmentPanoramaBinding::bind)

    private val shooterCallback = SimpleShooterCallback()

    private lateinit var dmdCapture: DMD_Capture
    private lateinit var vgCamera: ViewGroup

    override fun observeState(state: PanoramaContract.State?) = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCapture()
    }

    override fun onResume() {
        super.onResume()
        callback?.onPanoramaShown()
        dmdCapture.startCamera(requireContext(), 400, 500)
    }

    private fun initCapture() {
        dmdCapture = DMD_Capture()
        vgCamera = dmdCapture.initShooter(requireContext(),
            shooterCallback,
            requireActivity().windowManager.defaultDisplay.rotation,
            false,
            false)
        binding.flContainer.addView(vgCamera)
        binding.flContainer.post {
            (binding.flContainer.children.first() as? ViewGroup)?.children?.forEach {
                if (it.javaClass.name == "com.dermandar.dmd_lib.YinYangGLView") {
                    it.hide()
                }
            }
        }
    }
}