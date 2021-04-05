package com.dansdev.core.util

import android.app.Application
import com.livinglifetechway.k4kotlin.core.isNetworkAvailable

interface NetworkManager {

    fun hasConnection(): Boolean
}

class NetworkManagerImpl(private val app: Application): NetworkManager {

    override fun hasConnection(): Boolean = app.isNetworkAvailable()
}
