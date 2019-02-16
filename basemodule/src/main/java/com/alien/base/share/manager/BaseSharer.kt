package com.alien.base.share.manager

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.NonNull
import com.alien.base.image.Compressor
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

abstract class BaseSharer: ShareView {

    override fun localToBitmap(path: String): Bitmap? {
        val file = File(path)
        return if (!file.exists()) {
            null
        } else {
            val originalBitmap = BitmapFactory.decodeFile(path)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.RGB_565
            options.inSampleSize = Compressor.computeSize(originalBitmap.width, originalBitmap.height)
            if (originalBitmap != null && !originalBitmap.isRecycled){
                originalBitmap.recycle()
            }
            BitmapFactory.decodeFile(path, options)
        }
    }

    override fun toBitmap(@NonNull url: String): Bitmap? {
        val url = URL(url)
        val httpURLConnection = url.openConnection() as HttpURLConnection
        httpURLConnection.connect()
        httpURLConnection.connectTimeout = 5 * 1000
        httpURLConnection.requestMethod = "GET"
        return BitmapFactory.decodeStream(httpURLConnection.inputStream)
    }

    override fun binaryToBytes(bitmap: Bitmap): ByteArray {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        if (!bitmap.isRecycled){
            bitmap.recycle()
        }
        return bos.toByteArray()
    }
}