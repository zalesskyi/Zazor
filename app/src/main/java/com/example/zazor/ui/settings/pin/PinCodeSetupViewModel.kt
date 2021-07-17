package com.example.zazor.ui.settings.pin

import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl

interface PinCodeSetupViewModel : BaseViewModel<PinCodeSetupContract.State, PinCodeSetupContract.Event>

class PinCodeSetupViewModelImpl : BaseViewModelImpl<PinCodeSetupContract.State, PinCodeSetupContract.Event>(), PinCodeSetupViewModel {

    override suspend fun initialState(): PinCodeSetupContract.State = PinCodeSetupContract.State.Initial

    override fun onEventArrived(event: PinCodeSetupContract.Event?) = Unit
}