package com.alien.base.share.manager

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import com.alien.base.share.data.ShareConstant
import com.alien.base.share.data.ShareEntity
import com.alien.base.share.data.ShareType
import com.tencent.connect.share.QQShare
import com.tencent.connect.share.QzoneShare
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import java.util.*

class QQSharer constructor(val activity: Activity, val shareEntity: ShareEntity, val iUiListener: IUiListener): QQShareView {

    val mTencent: Tencent = Tencent.createInstance(ShareConstant.QQ_APP_ID, activity)

    override fun share(){
        when(shareEntity.type){
            ShareType.TYPE_MUSIC -> shareAudio()
            ShareType.TYPE_PIC -> sharePic()
            else -> shareDefault()
        }
    }

    override fun shareDefault() {
        val bundle = Bundle()
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT)
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, shareEntity.title)
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareEntity.icon)
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareEntity.url)
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareEntity.text)
        bundle.putString(QQShare.SHARE_TO_QQ_EXT_INT, ShareConstant.APP_NAME)
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, ShareConstant.APP_NAME)
        mTencent.shareToQQ(activity, bundle, iUiListener)
    }

    override fun sharePic() {
        val bundle = Bundle()
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE)
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, shareEntity.url)
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "迷说")
        mTencent.shareToQQ(activity, bundle, iUiListener)
    }

    override fun shareAudio() {
        val bundle = Bundle()
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO)
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, shareEntity.title)
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareEntity.text)
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareEntity.url)
        bundle.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, shareEntity.url)
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareEntity.icon)
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, ShareConstant.APP_NAME)
        mTencent.shareToQQ(activity, bundle, iUiListener)
    }

    override fun shareToQzone() {
        val title = shareEntity.title
        val content = shareEntity.text

        val params = Bundle()
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT)
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, if (title.length > 36) title.substring(0, 36) else title)
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, if (content.length > 80) content.substring(0, 80) else content)
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareEntity.url)
        if (!TextUtils.isEmpty(shareEntity.icon)) {
            val imageUrls = ArrayList<String>()
            imageUrls.add(shareEntity.icon)
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls)
        }

        mTencent.shareToQzone(activity, params, iUiListener)

    }

}