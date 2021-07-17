package com.example.zazor.ui.base

import android.app.Activity
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

interface PersistentBottomSheet {

    fun show()

    fun hide()
}

abstract class BasePersistentBottomSheet<STATE: UiState, EVENT: UiEvent>() : PersistentBottomSheet {

    abstract val viewModel: BaseViewModel<STATE, EVENT>

    abstract val behavior: BottomSheetBehavior<*>

    abstract val view: View

    abstract fun observeState(state: STATE?)

    @CallSuper
    override fun show() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        onShown()
    }

    @CallSuper
    override fun hide() {
        onHidden()
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    @CallSuper
    protected open fun onShown() {
        (view.context as? LifecycleOwner)?.lifecycleScope?.launch {
            viewModel.init()
            viewModel.uiState.collect(::observeState)
        }
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> onHidden()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
        })
    }

    @CallSuper
    protected open fun onHidden() {
        viewModel.reset()
    }
}