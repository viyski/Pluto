package com.alien.base.share

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.widget.Toast
import com.alien.base.share.data.ShareEntity
import com.alien.base.share.data.ShareType
import com.alien.base.share.manager.QQSharer
import com.alien.base.share.manager.ShareManager
import com.alien.base.share.manager.SinaSharer
import com.alien.base.share.manager.WXSharer
import com.sina.weibo.sdk.share.WbShareCallback
import com.sina.weibo.sdk.share.WbShareHandler
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError

class SharePresenter(val activity: ShareActivity, private val mShareEntity: ShareEntity) : BaseSharePresenter, WbShareCallback, IUiListener {

    lateinit var mShareCallbackView: ShareCallbackView
    lateinit var mSinaSharer: SinaSharer
    lateinit var mQQSharer: QQSharer
    lateinit var mWXSharer: WXSharer
    lateinit var mWbShareHandler: WbShareHandler

    lateinit var mWXAsyncTask: WXAsyncTask
    lateinit var mSinaAsyncTask: SinaAsyncTask

    fun init(shareCallbackView: ShareCallbackView) {
        mShareCallbackView = shareCallbackView
        when (mShareEntity.platformType) {
            ShareType.QQ -> {
                shareWithQQ()
            }
            ShareType.QZONE -> {
                shareWithQZone()
            }
            ShareType.SINA -> {
                shareWithSina()
            }
            ShareType.WECHAT -> {
                shareWithWechat()
            }
            ShareType.WECHATF -> {
                shareWithWechat()
            }
        }
    }

    override fun shareWithQQ() {
        mQQSharer = ShareManager.qqSharer(activity, SharePresenter@this, mShareEntity)
        mQQSharer.share()
    }

    override fun shareWithQZone() {
        mQQSharer = ShareManager.qqSharer(activity, SharePresenter@this, mShareEntity)
        mQQSharer.shareToQzone()
    }

    override fun shareWithWechat() {
        mWXSharer = ShareManager.wxSharer(activity.mApi, mShareEntity)
        mWXAsyncTask = WXAsyncTask(mWXSharer, activity)
        mWXAsyncTask.execute()
    }

    override fun shareWithSina() {
        mWbShareHandler = WbShareHandler(activity)
        mWbShareHandler.registerApp()
        mSinaSharer = ShareManager.sinaSharer(mWbShareHandler, mShareEntity)

        mSinaAsyncTask = SinaAsyncTask(mSinaSharer)
        mSinaAsyncTask.execute()
    }

    override fun onNewIntent(intent: Intent?) {
        if (this::mWbShareHandler.isInitialized)
            mWbShareHandler.doResultIntent(intent, SharePresenter@this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (this::mQQSharer.isInitialized) {
            Tencent.onActivityResultData(requestCode, resultCode, data, SharePresenter@this)
        }
    }

    override fun onWbShareFail() {
        mShareCallbackView.onShareFail("分享失败")
    }

    override fun onWbShareCancel() {
        mShareCallbackView.onShareCancel()
    }

    override fun onWbShareSuccess() {
        mShareCallbackView.onShareSuccess()
    }

    override fun onError(err: UiError?) {
        mShareCallbackView.onShareFail(err?.errorMessage)
    }

    override fun onComplete(any: Any?) {
        mShareCallbackView.onShareSuccess()
    }

    override fun onCancel() {
        mShareCallbackView.onShareCancel()
    }

    override fun onDestroy() {
        if (this::mSinaAsyncTask.isInitialized){
            mSinaAsyncTask.cancel(true)
        }

        if (this::mWXAsyncTask.isInitialized){
            mWXAsyncTask.cancel(true)
        }
    }

    class WXAsyncTask(val wxSharer: WXSharer, val context: Context) : AsyncTask<String, Void, Bitmap>(){
        override fun onPreExecute() {
            Toast.makeText(context, wxSharer.onPreExecute(), Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            return wxSharer.doInBackground()
        }

        override fun onPostExecute(result: Bitmap?) {
            wxSharer.onPostExecute(result)
        }

    }

    class SinaAsyncTask(val sinalSharer: SinaSharer) : AsyncTask<String, Void, Bitmap>(){

        override fun onPreExecute() {
            sinalSharer.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): Bitmap? {
            return sinalSharer.doInBackground()
        }

        override fun onPostExecute(result: Bitmap?) {
            sinalSharer.onPostExecute(result)
        }

    }

}