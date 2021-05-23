package com.example.zazor.ui.main

import com.example.zazor.data.prefs.AppPreferences
import com.example.zazor.ui.base.BaseViewModel
import com.example.zazor.ui.base.BaseViewModelImpl

interface MainViewModel : BaseViewModel<MainContract.State, MainContract.Event>

class MainViewModelImpl(private val appPrefs: AppPreferences)
    : BaseViewModelImpl<MainContract.State, MainContract.Event>(),
    MainViewModel {


    override suspend fun initialState(): MainContract.State = MainContract.State.Initial

    override fun onEventArrived(event: MainContract.Event?) = Unit
}