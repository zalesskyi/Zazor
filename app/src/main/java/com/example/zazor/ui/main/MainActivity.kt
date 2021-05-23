package com.example.zazor.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.zazor.R
import com.example.zazor.ui.base.BaseActivity
import com.example.zazor.ui.main.di.injectViewModel
import com.example.zazor.ui.media.MediaFragment

class MainActivity : BaseActivity<MainContract.State, MainContract.Event>(R.layout.activity_main), MainCallback {

    override val viewModel by injectViewModel()

    override fun observeState(state: MainContract.State?) {
        when (state) {
            is MainContract.State.Initial -> Unit
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun openMedia() {
        navigateTo(MediaFragment())
    }

    private fun navigateTo(fragment: Fragment, container: Int = R.id.flContainer, addToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().replace(container, fragment).apply {
            if (addToBackStack) addToBackStack(fragment::class.simpleName)
        }.commit()
    }
}