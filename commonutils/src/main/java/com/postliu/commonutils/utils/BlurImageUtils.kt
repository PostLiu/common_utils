@file:Suppress("DEPRECATION")

package com.postliu.commonutils.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.launch

object BlurImageUtils {

    fun ImageView.loadBlurImageCompat(url: String, radius: Float = 10f) {
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val renderEffect = RenderEffect.createBlurEffect(radius, radius, Shader.TileMode.CLAMP)
                setRenderEffect(renderEffect)
                setImageBitmap(getBitmapFromCoil(context, url))
            } else {
                val newBitmap =
                    getBitmapFromCoil(context, url)
                        ?.let { blurImage(context, it) }
                setImageBitmap(newBitmap)
            }
        }
    }

    suspend fun getBitmapFromCoil(context: Context, url: Any): Bitmap? {
        val result = kotlin.runCatching {
            val request = ImageRequest.Builder(context).data(url).allowHardware(false).build()
            ImageLoader(context).execute(request).drawable?.toBitmap()
        }
        return result.getOrNull()
    }

    fun blurImage(context: Context, bitmap: Bitmap, radius: Float = 10f): Bitmap {
        val renderScript = RenderScript.create(context)
        val input = Allocation.createFromBitmap(renderScript, bitmap)
        val out = Allocation.createTyped(renderScript, input.type)
        ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript)).apply {
            setInput(input)
            setRadius(radius)
            forEach(out)
        }
        out.copyTo(bitmap)
        renderScript.destroy()
        return bitmap
    }

}