package com.example.zazor.utils.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.showOrGone(show: Boolean) {
    if (show) show() else gone()
}

fun View.showOrHide(show: Boolean) {
    if (show) show() else invisible()
}

fun View.disableClickTemporarily() {
    isClickable = false
    postDelayed({
        isClickable = true
    }, 500)
}

/**
 * Get Bitmap from [this] View.
 *
 * @param config [Bitmap.Config] bitmap configuration
 *
 * @return [Bitmap] bitmap with view screenshot
 */
fun View.getBitmap(config: Bitmap.Config = Bitmap.Config.ARGB_8888) =
    takeIf { hasSize() }?.run {
        val bmp = Bitmap.createBitmap(width, height, config)
        val canvas = Canvas(bmp)
        draw(canvas)
        return@run bmp
    }

fun View.hasSize() = width > 0 && height > 0