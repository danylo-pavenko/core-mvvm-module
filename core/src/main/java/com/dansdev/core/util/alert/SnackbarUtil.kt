package com.dansdev.core.util.alert

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.dansdev.app.util.setBackTintRes
import com.dansdev.core.R
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {

    fun makeErr(view: View, @StringRes message: Int, duration: Int = Snackbar.LENGTH_LONG) {
        makeErr(view, view.resources.getString(message), duration)
    }

    fun makeErr(view: View, message: CharSequence, duration: Int = Snackbar.LENGTH_LONG) {
        make(view, message, duration, R.color.snackbarError, R.color.white)
    }

    fun make(view: View, message: CharSequence, duration: Int = Snackbar.LENGTH_LONG, @ColorRes colorResId: Int, @ColorRes textColorResId: Int = R.color.black) {
        val toast = Snackbar.make(view, message, duration)
        toast.view.setBackTintRes(colorResId)
        toast.setTextColor(ContextCompat.getColor(view.context, textColorResId))
        toast.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        toast.show()
    }
}

fun View.snackbarErr(@StringRes message: Int) {
    SnackbarUtil.makeErr(this, message)
}

fun View.snackbarErr(message: CharSequence) {
    SnackbarUtil.makeErr(this, message)
}

fun View.snackbarOk(message: CharSequence) {
    SnackbarUtil.make(this, message, colorResId = R.color.snackbarSuccess)
}

fun View.snackbarOk(@StringRes message: Int) {
    SnackbarUtil.make(this, resources.getString(message), colorResId = R.color.snackbarSuccess)
}
