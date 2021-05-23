package com.example.zazor.ui.media

import com.example.zazor.R
import com.example.zazor.ui.base.BaseFragment
import com.example.zazor.ui.media.di.injectViewModel

class MediaFragment : BaseFragment<MediaContract.State, MediaContract.Event>(R.layout.fragment_media) {

    override fun observeState(state: MediaContract.State?) = Unit

    override val viewModel by injectViewModel()
}