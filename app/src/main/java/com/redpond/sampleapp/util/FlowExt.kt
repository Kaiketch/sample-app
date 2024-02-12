package com.redpond.sampleapp.util

import com.redpond.sampleapp.domain.model.AppError
import com.redpond.sampleapp.domain.model.toAppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun <T> Flow<T>.catchAppError(block: (appError: AppError) -> Unit): Flow<T> {
    return catch { error ->
        block(error.toAppError())
    }
}

fun <T> Result<T>.onAppFailure(block: (appError: AppError) -> Unit): Result<T> {
    return onFailure { error -> block(error.toAppError()) }
}
