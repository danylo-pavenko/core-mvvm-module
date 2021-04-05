package com.dansdev.core.util.keyboard

import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isShowKeyboard(): Boolean {
    val inputMethodManager = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
    return inputMethodManager?.isAcceptingText ?: false
}
