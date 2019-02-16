package com.alien.base.share.manager

import android.graphics.Bitmap
import android.support.annotation.Nullable
import com.alien.base.share.data.ShareEntity
import com.sina.weibo.sdk.api.ImageObject
import com.sina.weibo.sdk.api.TextObject
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.sina.weibo.sdk.share.WbShareHandler

class SinaSharer(val shareHandler: WbShareHandler, val shareEntity: ShareEntity): BaseSharer() {

    override fun onPreExecute(): String? {
        return null
    }

    override fun doInBackground(): Bitmap? {
        return if (shareEntity.isLocalUrl) localToBitmap(shareEntity.icon)
                else toBitmap(shareEntity.icon)
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        share(shareEntity, bitmap)
    }

    override fun share(shareEntity: ShareEntity, @Nullable bitmap: Bitmap?) {
        val weiboMessage = WeiboMultiMessage()
        val textObject = TextObject()
        textObject.text = shareEntity.text
        textObject.title = shareEntity.title
        textObject.actionUrl = shareEntity.url
        if (bitmap != null) {
            val imageObject = ImageObject()
            imageObject.setImageObject(bitmap)
            weiboMessage.imageObject = imageObject
        }
        weiboMessage.textObject = textObject
        shareHandler.shareMessage(weiboMessage, false)
    }

}