package com.example.zazor.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.zazor.ui.main.MainCallback
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<STATE : UiState, EVENT : UiEvent>(@LayoutRes private val layoutRes: Int) : Fragment() {

    abstract fun observeState(state: STATE?)

    abstract val viewModel : BaseViewModel<STATE, EVENT>

    protected var callback: MainCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as? MainCallback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.takeUnless { layoutRes == NO_ID }?.inflate(layoutRes, container, false)

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewModel.uiState.collect(::observeState)
        }
    }

    override fun onPause() {
        resetFlows()
        super.onPause()
    }

    override fun onDetach() {
        callback = null
        super.onDetach()
    }

    private fun resetFlows() {
        viewModel.reset()
    }
}