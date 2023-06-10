package com.postliu.commonutils.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import java.io.Serializable

/**
 * 应用内跳转[Intent]
 * ```
 *      startWithIntent(this)
 *      startWithIntent(this){
 *          put("key","value")
 *      }
 * ```
 * @param R
 * @param context
 * @param block
 */
inline fun <reified R> startWithIntent(context: Context, noinline block: (Intent.() -> Unit)? = null) = runCatching {
    val intent = Intent(context, R::class.java)
    block?.invoke(intent)
    context.startActivity(intent)
}

/**
 * 通过action跳转[Intent]
 *
 * @param context
 * @param action
 * @param block
 */
fun startWithAction(context: Context, action: String, block: (Intent.() -> Unit)? = null) = runCatching {
    val intent = Intent(action)
    block?.invoke(intent)
    context.startActivity(intent)
}

internal const val BUNDLE_NAME = "bundle"

fun Intent.putBundle(bundle: Bundle?) = putExtra(BUNDLE_NAME, bundle)

fun FragmentActivity.intentBundle() = lazy {
    intent.getBundleExtra(BUNDLE_NAME)
}

fun FragmentActivity.intentString(key: String, defaultValue: String = "") = lazy {
    intent.getStringExtra(key) ?: defaultValue
}

fun FragmentActivity.intentBoolean(key: String, defaultValue: Boolean = false) = lazy {
    intent.getBooleanExtra(key, defaultValue)
}

fun FragmentActivity.intentInt(key: String, defaultValue: Int = 0) = lazy {
    intent.getIntExtra(key, defaultValue)
}

fun FragmentActivity.intentLong(key: String, defaultLong: Long) = lazy {
    intent.getLongExtra(key, defaultLong)
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun <T : Serializable> FragmentActivity.intentSerializable(key: String, clazz: Class<T>) = lazy {
    intent.getSerializableExtra(key, clazz)
}

@Suppress("UNCHECKED_CAST")
fun <T : Serializable> FragmentActivity.intentSerializable(key: String) = lazy {
    intent.getSerializableExtra(key) as T
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun <T : Parcelable> FragmentActivity.intentParcelable(key: String, clazz: Class<T>) = lazy {
    intent.getParcelableExtra(key, clazz)
}

fun <T : Parcelable> FragmentActivity.intentParcelable(key: String) = lazy {
    intent.getParcelableExtra<T>(key) as T
}
