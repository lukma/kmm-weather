package com.lukmadev.core.domain.common.usecase

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

abstract class FlowUseCase<P : Any, T> {
    protected lateinit var param: P
        private set

    protected abstract suspend fun build(): Flow<T>

    @NativeCoroutines
    suspend operator fun invoke(param: P? = null): Flow<T> {
        param?.run { this@FlowUseCase.param = this }
        return try {
            build()
        } catch (cancellationException: CancellationException) {
            throw cancellationException
        } catch (cause: Exception) {
            flow { throw cause }
        }
    }
}
