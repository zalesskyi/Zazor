package com.example.zazor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class DrawView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var paint: Paint? = null

    private var paths = mutableListOf<Path>()

    var isPaintAllowed = true
    set(value) {
        isClickable = value
        isFocusable = value
    }

    var colorRes: Int
    get () = 0
    set(value) {
        paint?.color = value
        invalidate()
    }

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = Color.RED
            strokeWidth = 24F
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint?.let { drawPaint ->
            paths.forEach { drawPath ->
                canvas.drawPath(drawPath, drawPaint)
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event?.takeIf { isPaintAllowed }?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    paths.add(Path().also {
                        it.moveTo(event.x, event.y)
                    })
                }
                MotionEvent.ACTION_MOVE -> {
                    paths.lastOrNull()?.lineTo(event.x, event.y)
                    invalidate()
                }
                else -> Unit
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun clear() {
        paths.clear()
        invalidate()
    }
}