package com.dansdev.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cbm.patient.presentation.util.SingleLiveData
import com.dansdev.core.model.AlertMessage
import com.dansdev.core.model.NavigateDir
import com.dansdev.core.state.ProgressState

abstract class CoreViewModel<VS: CoreViewState>: ViewModel() {

    protected val showErrorToast = SingleLiveData<AlertMessage>()
    protected val navigateLiveData = SingleLiveData<NavigateDir>()
    protected val progressState = SingleLiveData<ProgressState>()
    protected val viewStateUpdate = MutableLiveData<VS>()

    fun onShowError(): LiveData<AlertMessage> = showErrorToast
    fun onNavigateAction(): LiveData<NavigateDir?> = navigateLiveData
    fun onProgressStateUpdate(): LiveData<ProgressState> = progressState

    fun onViewStateUpdate(): LiveData<VS?> = viewStateUpdate
}
