package com.example.zazor.ui.photo

interface PhotoCallback {

    fun onCaptured()

    fun onPhotoEditCancel()

    fun clearAll()

    fun onCollageShown()

    fun onPhotoShown()

    fun onPanoramaShown()

    fun openSettings()
}