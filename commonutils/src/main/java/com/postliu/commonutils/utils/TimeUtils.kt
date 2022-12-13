package com.postliu.commonutils.utils

object TimeUtils {

    fun formatDHMS(
        time: Long,
        dayDesc: String = "天",
        hoursDesc: String = "时",
        minuteDesc: String = "分",
        secondDesc: String = "秒",
        block: ((day: Long, hours: Long, minute: Long, second: Long) -> Unit)? = null
    ): String {
        val day = time / (1000 * 60 * 60 * 24)
        val hour = (time - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
        val minute = (time - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60)
        val second =
            (time - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000
        block?.invoke(day, hour, minute, second)
        return buildString {
            if (day > 0) {
                append(day)
                append(dayDesc)
            }
            if (hour > 0) {
                append(hour)
                append(hoursDesc)
            }
            if (minute > 0) {
                append(minute)
                append(minuteDesc)
            }
            if (second > 0) {
                append(second)
                append(secondDesc)
            }
        }
    }
}