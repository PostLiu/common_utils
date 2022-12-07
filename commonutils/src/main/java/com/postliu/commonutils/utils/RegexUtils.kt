package com.postliu.commonutils.utils

import java.net.URL

/**
 * 正则验证工具类
 *
 * @constructor Create empty Regex utils
 */
object RegexUtils {

    /**
     * 验证手机号
     *
     * @param text
     */
    fun isPhoneNumber(text: CharSequence) = "^1[34578]\\d{9}\$".toRegex().matches(text)

    /**
     * 验证身份证号
     *
     * @param text
     */
    fun isIDCard(text: CharSequence) =
        "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]\$".toRegex()
            .matches(text)

    /**
     * 验证邮箱
     *
     * @param text
     */
    fun isEmail(text: CharSequence) = "\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}\b".toRegex().matches(text)

    /**
     * 验证URL
     *
     * @param text
     */
    fun isURL(text: CharSequence) = kotlin.runCatching { URL(text.toString()) }.isSuccess

    /**
     * 验证日期(yyyy-MM-dd)
     *
     * @param text
     */
    fun isDate(text: CharSequence) = "\\d{4}-(0[1-9]|1[012])-([12][0-9]|3[01]|0[1-9])".toRegex().matches(text)

    /**
     * 验证时间(HH:mm:ss)
     *
     * @param text
     */
    fun isTime(text: CharSequence) = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]".toRegex().matches(text)
}