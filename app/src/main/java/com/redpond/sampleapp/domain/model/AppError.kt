package com.redpond.sampleapp.domain.model

sealed class AppError(override val message: String) : Throwable(message) {
    class StateError(message: String) : AppError(message)
    class ServerError(message: String) : AppError(message)
}

fun Throwable.toAppError(): AppError {
    return when (this) {
        is IllegalStateException -> AppError.StateError(this.message ?: "State error")
        else -> AppError.ServerError(this.message ?: "Server error")
    }
}