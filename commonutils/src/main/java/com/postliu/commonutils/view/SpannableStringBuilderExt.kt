package com.postliu.commonutils.view

import android.content.Context
import android.graphics.*
import android.text.NoCopySpan
import android.text.SpannableStringBuilder
import android.text.style.*
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.TypefaceCompat
import androidx.core.text.inSpans
import kotlin.math.roundToInt

/**
 * 重写[ClickableSpan],[NoCopySpan]处理[SpannableStringBuilder]的click事件内存泄漏问题
 *
 * @constructor Create empty No copy click span
 */
abstract class NoCopyClickSpan : ClickableSpan(), NoCopySpan

typealias BuilderAction = SpannableStringBuilder.() -> Unit

/**
 * 文本大小
 *
 * @param size
 * @param dip
 * @param builderAction
 */
inline fun SpannableStringBuilder.textSize(
    size: Int, dip: Boolean = true, builderAction: BuilderAction
) = inSpans(AbsoluteSizeSpan(size, dip), builderAction)

/**
 * 图片
 *
 * @param context
 * @param resourceId
 * @param verticalAlignment
 * @param builderAction
 */
inline fun SpannableStringBuilder.image(
    context: Context,
    @DrawableRes resourceId: Int,
    verticalAlignment: Int = DynamicDrawableSpan.ALIGN_BASELINE,
    builderAction: BuilderAction
) = inSpans(ImageSpan(context, resourceId, verticalAlignment), builderAction)

/**
 * 点击事件
 *
 * @param click
 * @param builderAction
 * @receiver
 */
inline fun SpannableStringBuilder.click(
    crossinline click: (View) -> Unit, builderAction: BuilderAction
) = inSpans(object : NoCopyClickSpan() {
    override fun onClick(widget: View) {
        click.invoke(widget)
    }
}, builderAction)

/**
 * 字体
 *
 * @param context
 * @param fontId
 * @param builderAction
 */
inline fun SpannableStringBuilder.fontFamily(
    context: Context,
    @FontRes fontId: Int,
    builderAction: BuilderAction
) = TypefaceCompat.create(context, ResourcesCompat.getFont(context, fontId), Typeface.NORMAL).run {
    inSpans(this, builderAction)
}

/**
 * 背景颜色
 *
 * @param textColor
 * @param colors
 * @param radius
 * @param gradient
 * @param builderAction
 */
inline fun SpannableStringBuilder.roundBackground(
    textColor: Int,
    colors: IntArray,
    radius: Float,
    noinline gradient: (() -> Shader)? = null,
    builderAction: BuilderAction
) = inSpans(RoundBackgroundSpan(textColor, colors, radius, gradient), builderAction)

class RoundBackgroundSpan(
    private val textColor: Int,
    private val colors: IntArray = intArrayOf(Color.WHITE),
    private val radius: Float = 0f,
    private val gradient: (() -> Shader)? = null,
) : ReplacementSpan() {

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        return measureText(paint, text, start, end).roundToInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val fm = paint.fontMetrics
        val rectF = RectF(x, top.toFloat(), x + measureText(paint, text, start, end), fm.bottom - fm.top)
        if (colors.size >= 2) {
            paint.shader = gradient?.invoke()
        } else {
            paint.color = colors.first()
        }
        canvas.drawRoundRect(rectF, radius, radius, paint)
        paint.color = textColor
        canvas.drawText(text?.toString().orEmpty(), start, end, x, y.toFloat(), paint)
    }

    private fun measureText(paint: Paint, text: CharSequence?, start: Int, end: Int): Float {
        return paint.measureText(text, start, end)
    }
}