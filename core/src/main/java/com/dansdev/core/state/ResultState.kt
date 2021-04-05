package com.dansdev.core.state

sealed class ResultState {
    object InProgress : ResultState()
    object Cancel : ResultState()
    object Done : ResultState()
    data class Fail(val message: String = "", val data: Any? = null, val exception: Exception? = null) : ResultState()
    data class Success<T>(val data: T? = null) : ResultState()

    fun isSuccess(): Boolean = this == Done || this is Success<*>
    fun isFail(): Boolean = this is Fail
}

@Suppress("UNCHECKED_CAST")
fun <T> ResultState.asSuccess(): ResultState.Success<T> = this as ResultState.Success<T>

@Suppress("UNCHECKED_CAST")
fun ResultState.asFail(): ResultState.Fail = this as ResultState.Fail
