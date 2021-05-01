package com.dansdev.core.util

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import com.dansdev.core.model.NavigateDir

fun NavDirections.asDir(): NavigateDir {
    return NavigateDir.XDir(this)
}

fun Int.asNavDirection(bundle: Bundle = bundleOf()): NavDirections = object : NavDirections {

    override fun getActionId(): Int = this@asNavDirection

    override fun getArguments(): Bundle = bundle
}
