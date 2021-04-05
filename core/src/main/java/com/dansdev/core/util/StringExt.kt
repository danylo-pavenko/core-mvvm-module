package com.cbm.patient.presentation.util

import android.content.Context
import android.text.Spannable
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.dansdev.core.util.typeface.CustomTypefaceSpan
import java.util.regex.Pattern

fun Spannable.addSpanTextColor(
    context: Context,
    @ColorRes colorResId: Int,
    startIndex: Int,
    endIndex: Int
) {
    setSpan(
        ForegroundColorSpan(
            ContextCompat.getColor(
                context,
                colorResId
            )
        ),
        startIndex,
        endIndex,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun Spannable.addSpanTextFont(
    context: Context,
    @FontRes fontResId: Int,
    startIndex: Int,
    endIndex: Int
) {
    setSpan(
        CustomTypefaceSpan(ResourcesCompat.getFont(context, fontResId)),
        startIndex,
        endIndex,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun Spannable.addSpanTextClick(
    startIndex: Int,
    endIndex: Int,
    onClick: () -> Unit
) {
    setSpan(object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClick()
        }
    }, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun String.asCardFormat(): String {
    val builder = StringBuilder()
    forEachIndexed { index, c ->
        builder.append(c)
        if ((index + 1) % 4 == 0 && index > 0) builder.append(" ")
    }
    return builder.toString()
}

fun String.asCapsSentence(): CharSequence {
    if (isEmpty()) return this
    val builder = StringBuilder()
    builder.append(this[0].toUpperCase())
    builder.append(this.subSequence(1, this.length))
    return builder
}

fun String.capsMonthName(): CharSequence {
    if (isEmpty()) return this
    val parts = split(Pattern.compile("\\s"))
    val monthName = parts.last().asCapsSentence()
    val builder = StringBuilder()
    builder.append(parts[0])
    builder.append(" ")
    builder.append(monthName)
    return builder
}
