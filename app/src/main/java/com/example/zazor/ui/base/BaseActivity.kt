package com.example.zazor.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseActivity<STATE : UiState, EVENT : UiEvent>(@LayoutRes private val layoutRes: Int) : FragmentActivity() {

    abstract fun observeState(state: STATE?)

    abstract val viewModel : BaseViewModel<STATE, EVENT>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        lifecycleScope.launch {
            viewModel.init()
            viewModel.uiState.collect(::observeState)
        }
    }

    override fun onPause() {
        resetFlows()
        super.onPause()
    }

    private fun showNoInternetError() {
        Toast.makeText(this, "Нет соединения", Toast.LENGTH_SHORT).show()
    }

    private fun resetFlows() {
        viewModel.reset()
    }
}