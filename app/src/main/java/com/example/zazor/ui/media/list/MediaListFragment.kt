package com.example.zazor.ui.media.list

import android.os.Bundle
import android.view.View
import com.example.zazor.R
import com.example.zazor.databinding.FragmentMediaListBinding
import com.example.zazor.ui.base.BaseFragment
import com.example.zazor.ui.media.list.di.injectViewModel
import com.example.zazor.utils.viewBinding.viewBinding

class MediaListFragment : BaseFragment<MediaListContract.State, MediaListContract.Event>(R.layout.fragment_media_list) {

    override val viewModel by injectViewModel()

    private val binding by viewBinding(FragmentMediaListBinding::bind)

    override fun observeState(state: MediaListContract.State?) {
        when (state) {
            is MediaListContract.State.Initial -> {
                binding.rvPhotos.adapter = MediaListAdapter(state.photos)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }
}