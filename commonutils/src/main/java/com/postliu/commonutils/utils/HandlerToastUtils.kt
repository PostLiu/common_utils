package com.postliu.commonutils.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar.Duration

object HandlerToastUtils {

    private var toast: Toast? = null
    private val handler = Handler(Looper.getMainLooper())

    fun show(context: Context, msg: Any, @Duration duration: Int) {
        handler.post {
            if (toast != null) {
                toast?.cancel()
                toast = null
            }
            toast = Toast.makeText(context, msg.toString(), duration)
            toast?.show()
        }
    }
}