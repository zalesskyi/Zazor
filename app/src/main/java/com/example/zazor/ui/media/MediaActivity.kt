package com.example.zazor.ui.media

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.zazor.R
import com.example.zazor.databinding.ActivityMediaBinding
import com.example.zazor.ui.base.BaseActivity
import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.media.di.injectViewModel
import com.example.zazor.ui.media.list.MediaListFragment
import com.example.zazor.utils.viewBinding.viewBinding

class MediaActivity : BaseActivity<MediaContract.State, MediaContract.Event>(R.layout.activity_media) {

    companion object {

        fun newIntent(context: Context) = Intent(context, MediaActivity::class.java)
    }

    override val viewModel by injectViewModel()

    override fun observeState(state: MediaContract.State?) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateTo(MediaListFragment(), R.id.flContainer)
    }
}