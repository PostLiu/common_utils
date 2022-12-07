package com.postliu.commonutils.state

sealed interface UIResult<out T> {
    object Loading : UIResult<Nothing>
    data class Throw(val throwable: Throwable) : UIResult<Nothing>
    data class Failed(val message: String) : UIResult<Nothing>
    data class Success<T>(val data: T) : UIResult<T>

    companion object {

        inline fun <T> UIResult<T>.start(action: () -> Unit): UIResult<T> {
            if (this is Loading) {
                action()
            }
            return this
        }

        inline fun <T> UIResult<T>.catch(action: (Throwable) -> Unit): UIResult<T> {
            if (this is Throw) {
                action(throwable)
            }
            return this
        }

        inline fun <T> UIResult<T>.failed(action: (String) -> Unit): UIResult<T> {
            if (this is Failed) {
                action(message)
            }
            return this
        }

        inline fun <T, R> UIResult<T>.map(action: (T) -> R): UIResult<R> {
            return when (this) {
                is Loading -> Loading
                is Throw -> Throw(throwable)
                is Failed -> Failed(message)
                is Success -> Success(action(data))
            }
        }

        inline fun <T, R> UIResult<T>.mapCatching(action: (T) -> R): UIResult<R> {
            return when (this) {
                is Loading -> Loading
                is Throw -> Throw(throwable)
                is Failed -> Failed(message)
                is Success -> kotlin.runCatching {
                    Success(action(data))
                }.getOrElse { Throw(it) }
            }
        }

        inline fun <T> UIResult<T>.success(action: (T) -> Unit) {
            if (this is Success) {
                action(data)
            }
        }

        val <T> UIResult<T>.data get() = if (this is Success) data else null

        val <T> UIResult<T>.message get() = if (this is Failed) message else ""

        val <T> UIResult<T>.throwable get() = if (this is Throw) throwable else Throwable()
    }
}