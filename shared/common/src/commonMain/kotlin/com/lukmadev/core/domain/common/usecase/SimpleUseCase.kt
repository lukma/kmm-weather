package com.lukmadev.core.domain.common.usecase

import com.lukmadev.core.domain.common.entity.Result
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlin.coroutines.cancellation.CancellationException

abstract class SimpleUseCase<P : Any, T> {
    protected lateinit var param: P
        private set

    protected abstract suspend fun build(): T

    @NativeCoroutines
    suspend operator fun invoke(param: P? = null): Result<T> {
        param?.run { this@SimpleUseCase.param = this }
        return try {
            Result.Success(build())
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (cause: Exception) {
            Result.Failure(cause)
        }
    }
}
