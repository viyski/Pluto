package com.alien.base.share.manager

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.alien.base.share.ShareActivity
import com.alien.base.share.data.ShareConstant
import com.alien.base.share.data.ShareEntity
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.share.WbShareHandler
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.tauth.IUiListener

object ShareManager {

    fun share(activity: Activity, shareEntity: ShareEntity){
        val intent = Intent(activity, ShareActivity::class.java)
        intent.putExtra(ShareConstant.DATA, shareEntity)
        intent.putExtra("type", ShareConstant.TYPE_SHARE)
        activity.startActivityForResult(intent, ShareConstant.ACTIVITY_RESULT_CODE)
    }

    fun initSinaSdk(context: Context){
        WbSdk.install(context, AuthInfo(context,
                ShareConstant.SINA_APP_KEY,
                ShareConstant.SINA_REDIRECT_URL,
                ShareConstant.SINA_SCOPE))
    }


    fun qqSharer(activity: Activity, iUiListener: IUiListener, shareEntity: ShareEntity) = QQSharer(activity, shareEntity, iUiListener)

    fun wxSharer(api: IWXAPI, shareEntity: ShareEntity) = WXSharer(api, shareEntity)

    fun sinaSharer(shareHandler: WbShareHandler, shareEntity: ShareEntity) = SinaSharer(shareHandler, shareEntity)

}