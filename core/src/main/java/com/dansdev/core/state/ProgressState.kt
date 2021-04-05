package com.dansdev.core.state

sealed class ProgressState {
    object Show: ProgressState()
    object Hide: ProgressState()
}
