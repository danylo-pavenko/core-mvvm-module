package com.dansdev.core

import androidx.lifecycle.asFlow
import com.cbm.patient.presentation.util.SingleLiveData
import com.livinglifetechway.k4kotlin.core.orTrue
import kotlinx.coroutines.flow.Flow

abstract class CoreSharedViewModel: CoreViewModel<CoreViewState>() {

    private val networkConnectedLiveData = SingleLiveData<Boolean>()

    fun onConnectedStateUpdate(): Flow<Boolean> = networkConnectedLiveData.asFlow()

    fun networkState(hasConnection: Boolean) {
        networkConnectedLiveData.apply {
            if (hasConnection != value.orTrue()) {
                postValue(hasConnection)
            }
        }
    }
}
