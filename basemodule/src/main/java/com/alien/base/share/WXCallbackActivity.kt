package com.alien.base.share

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alien.base.share.data.ShareConstant
import com.alien.base.ui.BaseActivity
import com.alien.baselib.ExtendFun.toast
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

abstract class WXCallbackActivity : BaseActivity(), IWXAPIEventHandler, ShareCallbackView {

    lateinit var mApi: IWXAPI

    var mType: Int = ShareConstant.TYPE_SHARE

    override fun onCreateInit(savedInstanceState: Bundle?) {
        mApi = WXAPIFactory.createWXAPI(this, ShareConstant.WX_APP_ID, false);
        mApi.registerApp(ShareConstant.WX_APP_ID)
        mApi.handleIntent(intent, this)
        mType = intent.getIntExtra("type", ShareConstant.TYPE_LOGIN)
    }

    override fun getResLayout(): Int = 0

    override fun onResp(resp: BaseResp?) {
        if (resp?.type == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            return
        }

        when (mType) {
            ShareConstant.TYPE_SHARE -> {
                if (resp?.errCode == BaseResp.ErrCode.ERR_OK) {
                    onShareSuccess()
                } else {
                    val result = when (resp?.errCode) {
                        BaseResp.ErrCode.ERR_USER_CANCEL -> "分享取消"
                        BaseResp.ErrCode.ERR_AUTH_DENIED -> "分享被拒绝"
                        else -> "分享失败"
                    }
                    toast(result)
                    finishCanceled()
                }
            }
            ShareConstant.TYPE_LOGIN -> {
                when (resp?.errCode) {
                    BaseResp.ErrCode.ERR_OK -> {
                        val code = (resp as SendAuth.Resp).code
                        onAuthSuccess(code)
                    }
                    BaseResp.ErrCode.ERR_USER_CANCEL -> {
                        toast("授权取消")
                        finishCanceled()
                    }
                    else -> {
                        toast("授权失败")
                        finishCanceled()
                    }
                }
            }
        }
    }

    override fun onReq(req: BaseReq?) {

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mApi.handleIntent(intent, this)
    }

    abstract fun onAuthSuccess(code: String?)

    private fun finishActivity() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onShareCancel() {
        toast("分享取消")
        finishCanceled()
    }

    override fun onShareFail(err: String?) {
        toast(err ?: "分享失败")
        finishCanceled()
    }

    override fun onShareSuccess() {
        toast("分享成功")
        finishActivity()
    }

    private fun finishCanceled() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}