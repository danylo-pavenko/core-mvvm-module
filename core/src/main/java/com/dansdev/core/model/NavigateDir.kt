package com.dansdev.core.model

import android.net.Uri
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import com.dansdev.core.util.asNavDirection

sealed class NavigateDir {
    object Back : NavigateDir()
    object Finish : NavigateDir()
    data class Path(val uri: Uri) : NavigateDir()
    data class Destination(@IdRes val destinationId: Int) : NavigateDir()
    data class XDir(val navDirections: NavDirections) : NavigateDir()

    fun navigation(): NavDirections? = when (this) {
        is XDir -> navDirections
        is Destination -> destinationId.asNavDirection()
        else -> null
    }
}
