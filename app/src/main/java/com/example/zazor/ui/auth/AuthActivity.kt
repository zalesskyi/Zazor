package com.example.zazor.ui.auth

import com.example.zazor.R
import com.example.zazor.ui.auth.di.injectViewModel
import com.example.zazor.ui.base.BaseActivity

class AuthActivity : BaseActivity<AuthContract.State, AuthContract.Event>(R.layout.activity_auth) {

    override val viewModel by injectViewModel()

    override fun observeState(state: AuthContract.State?) = Unit
}