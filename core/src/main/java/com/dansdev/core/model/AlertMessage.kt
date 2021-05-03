package com.dansdev.core.model

import android.content.res.Resources
import androidx.annotation.StringRes

sealed class AlertMessage {

    data class ErrorMessage(@StringRes val message: Int = 0, val text: String = "") : AlertMessage() {

        fun content(resources: Resources): CharSequence {
            return if (message != 0) resources.getString(message)
            else text
        }
    }

    data class SuccessMessage(@StringRes val message: Int = 0, val text: String = "") : AlertMessage() {

        fun content(resources: Resources): CharSequence {
            return if (message != 0) resources.getString(message)
            else text
        }
    }

    data class ProgressMessage(@StringRes val message: Int = 0, val text: String = "") : AlertMessage() {

        var dismiss: (() -> Unit)? = null

        fun content(resources: Resources): CharSequence {
            return if (message != 0) resources.getString(message)
            else text
        }
    }
}
