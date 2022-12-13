package com.postliu.commonutils

import com.postliu.commonutils.utils.TimeUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() = runBlocking {
        val features = 1670552520000
        val now = System.currentTimeMillis()
        val result = features - now
        repeat(result.toInt()) {
            delay(1000)
            if (it >= (result / 1000).toInt()) {
                return@repeat
            }
            TimeUtils.formatDHMS(features - System.currentTimeMillis()).run {
                println("$it--${(result/1000).toInt()}->剩余时间:$this")
            }
        }
    }
}