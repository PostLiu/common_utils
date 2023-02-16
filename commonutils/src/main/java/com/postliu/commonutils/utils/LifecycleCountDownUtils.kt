package com.postliu.commonutils.utils

import androidx.annotation.Keep
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


typealias CountDownStart = () -> Unit
typealias CountDownRunning = (Int) -> Unit
typealias CountDownError = (Throwable) -> Unit
typealias CountDownCompleted = () -> Unit

/**
 * 倒计时工具
 *
 * @constructor Create empty Count down utils
 */
object LifecycleCountDownUtils : DefaultLifecycleObserver {

    private var countDownStart: CountDownStart = {}
    private var countDownRunning: CountDownRunning = {}
    private var countDownError: CountDownError = {}
    private var countDownCompleted: CountDownCompleted = {}
    private var config = CountDownConfig()
    private var job: Job? = null

    fun addObserver(owner: LifecycleOwner) = apply {
        owner.lifecycle.addObserver(this)
    }

    fun setConfig(config: CountDownConfig) = apply {
        this.config = config
    }

    fun start(scope: CoroutineScope) = apply {
        job?.cancel()
        job = startRunning(scope = scope, totalTime = config.totalTime, timeMillis = config.timeMillis)
    }

    fun addOnStart(countDownStart: CountDownStart) = apply {
        this.countDownStart = countDownStart
    }

    fun addOnRunning(countDownRunning: CountDownRunning) = apply {
        this.countDownRunning = countDownRunning
    }

    fun addOnError(countDownError: CountDownError) = apply {
        this.countDownError = countDownError
    }

    fun addOnCompleted(countDownCompleted: CountDownCompleted) = apply {
        this.countDownCompleted = countDownCompleted
    }

    private fun cancel() {
        job?.cancel()
        job = null
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        cancel()
    }

    private fun startRunning(
        scope: CoroutineScope,
        totalTime: Int,
        timeMillis: Long
    ) = scope.launch(Dispatchers.Default) {
        flow {
            (totalTime downTo 0).forEach {
                emit(it)
                kotlinx.coroutines.delay(timeMillis)
            }
        }.flowOn(coroutineContext).onStart { countDownStart.invoke() }.onEach { countDownRunning.invoke(it) }
            .catch { countDownError.invoke(it) }.onCompletion { countDownCompleted.invoke() }.collectLatest {
                if (it == 0) {
                    countDownCompleted.invoke()
                }
            }
    }
}

@Keep
data class CountDownConfig(
    val totalTime: Int = 60,
    val timeMillis: Long = 1000,
)