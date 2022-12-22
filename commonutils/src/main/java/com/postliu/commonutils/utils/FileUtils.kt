package com.postliu.commonutils.utils

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.PixelCopy
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.File

object FileUtils {

    fun createFolder(context: Context, folderName: String, folderType: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = createFolderOnQ(folderName, folderType)
            val uri = context.contentResolver.insert(
                MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL),
                contentValues
            )
            println("create folder on Q : $uri")
            uri != null
        } else {
            if (context.checkCallingPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                return false
            }
            val file = createFolderOnM(folderName, folderType)
            println("create folder on M : ${file.absolutePath}")
            file.exists() && file.isDirectory
        }
    }

    // 低版本创建文件夹]
    private fun createFolderOnM(folderName: String, folderType: String): File {
        val file = Environment.getExternalStoragePublicDirectory("${folderType}/$folderName")
        if (!file.exists()) {
            file.mkdir()
        }
        return file
    }

    // 高版本创建文件夹
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createFolderOnQ(folderName: String, folderType: String): ContentValues {
        return ContentValues().apply {
            put(MediaStore.Files.FileColumns.DISPLAY_NAME, folderName)
            put(MediaStore.Files.FileColumns.RELATIVE_PATH, "$folderType/$folderName")
            put(MediaStore.Files.FileColumns.DATE_ADDED, System.currentTimeMillis())
        }
    }

    fun captureView(view: View, window: Window, bitmapCallback: (Bitmap) -> Unit) {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val location = IntArray(2)
        view.getLocationInWindow(location)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PixelCopy.request(
                window,
                Rect(location[0], location[1], location[0] + view.width, location[1] + view.height),
                bitmap, { copyResult ->
                    if (copyResult == PixelCopy.SUCCESS) {
                        bitmapCallback(bitmap)
                    } else {
                        Toast.makeText(view.context, "captureView is Failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }, Handler(Looper.getMainLooper())
            )
        } else {
            val canvas = Canvas(bitmap).apply {
                drawColor(0xFFFFFFFF.toInt())
            }
            view.draw(canvas)
            bitmapCallback(bitmap)
        }
    }

    fun saveCaptureView2Album(
        context: Context,
        bitmap: Bitmap,
        fileName: String,
        failed: ((Throwable) -> Unit)? = null,
        success: ((Uri) -> Unit)? = null
    ) {
        kotlin.runCatching {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                } else {
                    put(
                        MediaStore.Images.Media.DATA,
                        "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/$fileName"
                    )
                }
            }
            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            if (uri == null) {
                failed?.invoke(NullPointerException("uri is null"))
                return
            }
            context.contentResolver.openOutputStream(uri)?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                it.flush()
            }
            uri
        }.onFailure {
            failed?.invoke(it)
        }.onSuccess {
            success?.invoke(it)
        }
    }
}