package com.example.zazor.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.zazor.R
import com.example.zazor.ui.base.BaseActivity
import com.example.zazor.ui.settings.di.injectViewModel
import com.example.zazor.ui.settings.list.SettingsListFragment

class SettingsActivity : BaseActivity<SettingsContract.State, SettingsContract.Event>(R.layout.activity_settings) {

    companion object {

        fun newIntent(context: Context) =
            Intent(context, SettingsActivity::class.java)
    }

    override val viewModel by injectViewModel()

    override fun observeState(state: SettingsContract.State?) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateTo(SettingsListFragment(), R.id.flContainer)
    }
}