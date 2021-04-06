package com.dansdev.core.util

import androidx.navigation.NavDirections
import com.dansdev.core.model.NavigateDir

fun NavDirections.asDir(): NavigateDir {
    return NavigateDir.XDir(this)
}
