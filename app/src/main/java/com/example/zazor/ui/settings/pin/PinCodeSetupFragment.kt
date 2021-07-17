package com.example.zazor.ui.settings.pin

import com.example.zazor.R
import com.example.zazor.ui.base.BaseFragment
import com.example.zazor.ui.settings.pin.di.injectViewModel

// todo layout
class PinCodeSetupFragment : BaseFragment<PinCodeSetupContract.State, PinCodeSetupContract.Event>(R.layout.fragment_settings_list) {

    override val viewModel by injectViewModel()

    override fun observeState(state: PinCodeSetupContract.State?) = Unit
}