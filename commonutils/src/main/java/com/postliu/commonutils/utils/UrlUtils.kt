package com.postliu.commonutils.utils

object UrlUtils {

    fun urlParamsToMap(url: String): Map<String, String> {
        val pattern = "(\\?|&+)(.+?)=([^&]*)".toPattern()
        val matcher = pattern.matcher(url)
        val map = linkedMapOf<String, String>()
        while (matcher.find()) {
            val key = matcher.group(2)
            val value = matcher.group(3)
            if (!key.isNullOrEmpty() && !value.isNullOrEmpty()) {
                map[key] = value
            }
        }
        return map
    }
}