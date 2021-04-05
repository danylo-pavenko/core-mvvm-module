package com.dansdev.core.api

import com.dansdev.core.state.ResultState
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class Result<out T> {

    sealed class Success<T> : Result<T>() {

        abstract val value: T

        override fun toString() = "Success($value)"

        class Value<T>(override val value: T) : Success<T>()

        data class HttpResponse<T>(
            override val value: T,
            override val statusCode: Int,
            override val statusMessage: String? = null,
            override val url: String? = null
        ) : Success<T>(), com.dansdev.core.api.HttpResponse

        object Empty : Success<Nothing>() {

            override val value: Nothing get() = error("No value")

            override fun toString() = "Success"
        }
    }



    sealed class Failure<E : Throwable>(open val error: E? = null) : Result<Nothing>() {

        override fun toString() = "Failure($error)"

        class Error(override val error: Throwable) : Failure<Throwable>(error)

        class HttpError(override val error: HttpException) : Failure<HttpException>(), HttpResponse {

            override val statusCode: Int get() = error.statusCode

            override val statusMessage: String? get() = error.statusMessage

            override val url: String? get() = error.url
        }
    }
}

typealias EmptyResult = Result<Nothing>

fun <T> Result<T>.isSuccess(): Boolean {
    return this is Result.Success
}

fun <T> Result<T>.asSuccess(): Result.Success<T> {
    return this as Result.Success<T>
}

fun <T> Result<T>.asSuccessOrNull(): Result.Success<T>? {
    return if (this.isSuccess()) {
        this as Result.Success<T>
    } else null
}

@OptIn(ExperimentalContracts::class)
fun <T> Result<T>.isFailure(): Boolean {
    contract {
        returns(true) implies(this@isFailure is Result.Failure<*>)
    }
    return this is Result.Failure<*>
}

fun <T> Result<T>.asFailure(): Result.Failure<*> {
    return this as Result.Failure<*>
}

fun <T> Result<T>.asFailureState(): ResultState.Fail {
    return when (val error = this.asFailure()) {
        is Result.Failure.Error -> {
            ResultState.Fail(error.error.localizedMessage.orEmpty(), data = error, exception = Exception(error.error))
        }
        is Result.Failure.HttpError -> {
            ResultState.Fail(error.error.cause?.message.orEmpty(), data = error.error.statusCode, exception = error.error)
        }
    }
}

fun <T> Result<T>.asResultState(): ResultState {
    return if (this.isSuccess()) {
        ResultState.Success(asSuccess().value)
    } else {
        asFailureState()
    }
}
