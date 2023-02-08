package com.postliu.commonutils.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.postliu.commonutils.view.startWithAction
import com.postliu.commonutils.view.toast

/**
 * 调用外部地图应用进行导航
 *
 * @constructor Create empty Map navigation utils
 */
object MapNavigationUtils {

    fun openBaiduMap(
        context: Context, latitude: String, longitude: String, error: MapOpenFailed, success: MapOpenSuccess
    ) {
        if (latitude.isEmpty() && longitude.isEmpty()) {
            context.toast("目的地经纬度不存在")
            return
        }
        startWithAction(context, Intent.ACTION_VIEW) {
            addCategory(Intent.CATEGORY_DEFAULT)
            data =
                Uri.parse("baidumap://map/direction?destination=${latitude},${longitude}&coord_type=gcj02&src=${context.packageName}")
        }.onFailure {
            error.failed(it)
        }.onSuccess {
            success.success()
        }
    }

    fun openGaodeMap(
        context: Context, latitude: String, longitude: String, error: MapOpenFailed, success: MapOpenSuccess
    ) {
        if (latitude.isEmpty() && longitude.isEmpty()) {
            context.toast("目的地经纬度不存在")
            return
        }
        startWithAction(context, Intent.ACTION_VIEW) {
            addCategory(Intent.CATEGORY_DEFAULT)
            data = Uri.parse("amapuri://route/plan/?dlat=${latitude}&dlon=${longitude}&dev=0&t=1")
        }.onFailure {
            error.failed(it)
        }.onSuccess {
            success.success()
        }
    }

    fun openTencentMap(
        context: Context, latitude: String, longitude: String, error: MapOpenFailed, success: MapOpenSuccess
    ) {
        if (latitude.isEmpty() && longitude.isEmpty()) {
            context.toast("目的地经纬度不存在")
            return
        }
        startWithAction(context, Intent.ACTION_VIEW) {
            addCategory(Intent.CATEGORY_DEFAULT)
            data =
                Uri.parse("qqmap://map/routeplan?type=drive&fromcoord=CurrentLocation&tocoord=${latitude},${longitude}&referer=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77")
        }.onFailure {
            error.failed(it)
        }.onSuccess {
            success.success()
        }
    }
}

interface MapOpenSuccess {

    fun success()

    companion object {
        operator fun invoke(block: () -> Unit) = object : MapOpenSuccess {
            override fun success() {
                block.invoke()
            }
        }
    }
}

interface MapOpenFailed {
    fun failed(throwable: Throwable)

    companion object {
        operator fun invoke(block: (Throwable) -> Unit) = object : MapOpenFailed {
            override fun failed(throwable: Throwable) {
                block.invoke(throwable)
            }
        }
    }
}