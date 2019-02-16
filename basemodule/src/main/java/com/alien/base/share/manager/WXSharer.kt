package com.alien.base.share.manager

import android.graphics.Bitmap
import android.support.annotation.Nullable
import com.alien.base.share.data.ShareEntity
import com.alien.base.share.data.ShareType
import com.tencent.mm.opensdk.modelmsg.*
import com.tencent.mm.opensdk.openapi.IWXAPI

class WXSharer constructor(val mApi: IWXAPI, val shareEntity: ShareEntity): BaseSharer(), WXShareView {

    companion object {
        const val THUMB_SIZE = 150
    }

    override fun onPreExecute(): String? {
        return if (!mApi.isWXAppInstalled) {
             "你还没有安装微信客户端，请安装微信后再分享。"
        }else{
            null
        }
    }

    override fun doInBackground(): Bitmap? {
        share(shareEntity, null)
        return null
    }

    override fun onPostExecute(bitmap: Bitmap?) {
    }

    override fun share(shareEntity: ShareEntity, @Nullable bitmap: Bitmap?) {
        when (shareEntity.type) {
            ShareType.TYPE_TEXT -> shareText()
            ShareType.TYPE_PIC -> sharePic()
            ShareType.TYPE_MUSIC -> shareMusic()
            ShareType.TYPE_VIDEO -> shareVideo()
            ShareType.TYPE_WEB -> shareWeb()
            ShareType.TYPE_MINI -> shareMini()
        }
    }

    override fun shareText() {
        val textObject = WXTextObject()
        textObject.text = shareEntity.text

        val msg = WXMediaMessage()
        msg.mediaObject = textObject
        msg.description = shareEntity.text

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("text")
        req.message = msg
        req.scene = getScene()
        mApi.sendReq(req)
    }

    override fun shareWeb() {
        val webpage = WXWebpageObject()
        webpage.webpageUrl = shareEntity.url
        val msg = WXMediaMessage(webpage)
        msg.title = shareEntity.title
        msg.description = shareEntity.text

        val thumbBmp = Bitmap.createScaledBitmap(toBitmap(shareEntity.icon)!!, THUMB_SIZE, THUMB_SIZE, true)
        msg.thumbData = binaryToBytes(thumbBmp)
        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("webpage")
        req.message = msg
        req.scene = getScene()
        mApi.sendReq(req)
    }

    override fun sharePic() {
        val bitmap = if (shareEntity.isLocalUrl) {
            localToBitmap(shareEntity.url)
        } else {
            toBitmap(shareEntity.url)
        }

        if (bitmap == null) {
            shareWeb()
        } else {
            val imageObject = WXImageObject(bitmap)
            val msg = WXMediaMessage()
            msg.mediaObject = imageObject

            if (shareEntity.isLocalUrl){
                val thumbBmp = Bitmap.createScaledBitmap(localToBitmap(shareEntity.icon)!!, THUMB_SIZE, THUMB_SIZE, true)
                msg.thumbData = binaryToBytes(thumbBmp)
            }else{
                val thumbBmp = Bitmap.createScaledBitmap(toBitmap(shareEntity.icon)!!, THUMB_SIZE, THUMB_SIZE, true)
                msg.thumbData = binaryToBytes(thumbBmp)
            }

            val req = SendMessageToWX.Req()
            req.transaction = buildTransaction("img")
            req.message = msg
            req.scene = getScene()
            mApi.sendReq(req)
        }
    }

    override fun shareMusic() {
        val bitmap = toBitmap(shareEntity.icon)
        val music = WXMusicObject()
        music.musicUrl = shareEntity.url

        val msg = WXMediaMessage()
        msg.mediaObject = music
        msg.title = shareEntity.title
        msg.description = shareEntity.text

        val thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true)
        msg.thumbData = binaryToBytes(thumbBmp)

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("music")
        req.message = msg
        req.scene = getScene()
        mApi.sendReq(req)
    }

    override fun shareVideo() {
        val video = WXVideoObject()
        video.videoUrl = shareEntity.url

        val msg = WXMediaMessage(video)
        msg.title = shareEntity.title
        msg.description = shareEntity.text

        val thumbBmp = Bitmap.createScaledBitmap(toBitmap(shareEntity.icon), THUMB_SIZE, THUMB_SIZE, true)
        msg.thumbData = binaryToBytes(thumbBmp)

        val req = SendMessageToWX.Req()
        req.transaction = buildTransaction("video")
        req.message = msg
        req.scene = getScene()
        mApi.sendReq(req)
    }

    fun shareMini(){

    }

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }

    fun getScene() =
        if (ShareType.WECHATF.equals(shareEntity.platformType)) SendMessageToWX.Req.WXSceneTimeline else SendMessageToWX.Req.WXSceneSession
}