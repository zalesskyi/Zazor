package com.example.zazor.ui.auth

import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl
import com.example.zazor.ui.settings.SettingsContract

interface AuthViewModel : BaseViewModel<AuthContract.State, AuthContract.Event>

class AuthViewModelImpl : BaseViewModelImpl<AuthContract.State, AuthContract.Event>(), AuthViewModel {

    override suspend fun initialState(): AuthContract.State = AuthContract.State.Initial

    override fun onEventArrived(event: AuthContract.Event?) = Unit
}