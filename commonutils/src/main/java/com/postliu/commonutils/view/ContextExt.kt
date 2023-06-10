package com.postliu.commonutils.view

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources.Theme
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.postliu.commonutils.utils.HandlerToastUtils
import java.io.File

/**
 * 屏幕方向是否是竖屏
 */
val Context.isPortrait get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

/**
 * 屏幕方向是否是横屏
 */
val Context.isLandscape get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

/**
 * 复制内容到剪贴板
 *
 * @param tips tag
 * @param label 复制的内容
 * @receiver
 */
fun Context.copyClip(tips: String = "复制成功", label: () -> String) {
    val data = label.invoke()
    val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboardManager.setPrimaryClip(ClipData.newPlainText(tips, data))
}

/**
 * 获取颜色资源
 *
 * @param color
 * @param theme
 */
fun Context.getColor(@ColorRes color: Int, theme: Theme) = ResourcesCompat.getColor(resources, color, theme)

/**
 * Toast
 *
 * @param msg
 */
fun Context.toast(msg: Any?) = msg?.let {
    HandlerToastUtils.show(this, it.toString(), Toast.LENGTH_SHORT)
}

/**
 * Snackbar
 *
 * @param msg
 */
fun View.showSnackbar(msg: Any?) = msg?.let {
    Snackbar.make(this, "$it", Snackbar.LENGTH_SHORT).run {
        if (isShown) {
            return@run
        }
        show()
    }
}

/**
 * 显示软键盘.注意,只有view是[EditText]才能显示软键盘
 *
 * @param window
 */
fun View.showSoftKeyboard(window: Window) {
    if (this is EditText) {
        WindowInsetsControllerCompat(window, this).show(WindowInsetsCompat.Type.ime())
    }
}

/**
 * 隐藏软键盘
 *
 * @param window
 */
fun View.hideSoftKeyboard(window: Window) {
    WindowInsetsControllerCompat(window, this).hide(WindowInsetsCompat.Type.ime())
}

/**
 * 实现[View]的颜色黑白化
 *
 * @param sat A value of 0 maps the color to gray-scale. 1 is identity.
 */
fun View.colorMatrix(sat: Float = 1f) {
    val paint = Paint()
    val colorMatrix = ColorMatrix().apply { setSaturation(sat) }
    paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
    setLayerType(View.LAYER_TYPE_HARDWARE, paint)
}

/**
 * Snackbar
 *
 * @param msg
 */
fun Activity.showSnackbar(msg: Any?) = window.decorView.showSnackbar(msg)

/**
 * Snackbar
 *
 * @param msg
 */
fun Fragment.showSnackbar(msg: Any?) = requireView().showSnackbar(msg)

/**
 * Snackbar
 *
 * @param msg
 */
fun Dialog.showSnackbar(msg: Any?) = window?.decorView?.showSnackbar(msg)

/**
 * 显示软键盘
 *
 * @param view
 */
fun Activity.showSoftKeyboard(view: View) {
    if (view is EditText) {
        WindowInsetsControllerCompat(window, view).show(WindowInsetsCompat.Type.ime())
    }
}

/**
 * 隐藏软键盘
 *
 * @param view
 */
fun Activity.hideSoftKeyboard(view: View) {
    WindowInsetsControllerCompat(window, view).hide(WindowInsetsCompat.Type.ime())
}

/**
 * 显示软键盘
 *
 * @param view
 */
fun Fragment.showSoftKeyboard(view: View) = runCatching {
    if (view is EditText) {
        WindowInsetsControllerCompat(requireActivity().window, view).show(WindowInsetsCompat.Type.ime())
    }
}

/**
 * 隐藏软键盘
 *
 * @param view
 */
fun Fragment.hideSoftKeyboard(view: View) = runCatching {
    WindowInsetsControllerCompat(requireActivity().window, view).hide(WindowInsetsCompat.Type.ime())
}

/**
 * 应用内安装Apk
 *
 * @param apkFile
 * @param authority
 */
fun Context.installApk(
    apkFile: File, authority: String = "${applicationContext.packageName}.FileProvider"
) = runCatching {
    startWithAction(this, Intent.ACTION_VIEW) {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val contentUri = FileProvider.getUriForFile(this@installApk, authority, apkFile)
            setDataAndType(contentUri, "application/vnd.android.package-archive")
        } else {
            setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        }
    }.getOrThrow()
}
