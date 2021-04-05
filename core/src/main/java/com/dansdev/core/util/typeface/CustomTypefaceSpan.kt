package com.dansdev.core.util.typeface

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan
import com.livinglifetechway.k4kotlin.core.orZero

class CustomTypefaceSpan(private val font: Typeface?) : TypefaceSpan("") {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        applyCustomTypeFace(ds, font)
    }

    override fun updateMeasureState(paint: TextPaint) {
        super.updateMeasureState(paint)
        applyCustomTypeFace(paint, font)
    }

    private fun applyCustomTypeFace(paint: Paint, tf: Typeface?) {
        val oldStyle: Int
        val old: Typeface? = paint.typeface
        oldStyle = old?.style ?: 0
        val fake = oldStyle and tf?.style.orZero().inv()
        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }
        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }
        paint.typeface = tf
    }
}
