package com.postliu.commonutils.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * dp,px互转工具
 *
 * @constructor Create empty Dimen utils
 */
object DimenUtils {

    val Int.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )

    val Int.px
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            this.toFloat(),
            Resources.getSystem().displayMetrics
        )

    val Float.dp
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
        )

    val Float.px
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX,
            this,
            Resources.getSystem().displayMetrics
        )

    fun dp2px(context: Context, dimen: Float) = with(context.resources.displayMetrics) {
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimen, this)
    }

    fun px2dp(context: Context, dimen: Float) = with(context.resources.displayMetrics) {
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dimen, this)
    }
}