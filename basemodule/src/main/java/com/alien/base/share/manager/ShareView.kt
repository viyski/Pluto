package com.alien.base.share.manager

import android.graphics.Bitmap
import android.support.annotation.Nullable
import com.alien.base.share.data.ShareEntity

interface ShareView {

    fun onPreExecute(): String?

    fun doInBackground(): Bitmap?

    fun onPostExecute(bitmap: Bitmap?)

    fun share(shareEntity: ShareEntity, @Nullable bitmap: Bitmap?)

    fun binaryToBytes(bitmap: Bitmap) : ByteArray

    fun toBitmap(url: String) : Bitmap?

    fun localToBitmap(path: String) : Bitmap?

}