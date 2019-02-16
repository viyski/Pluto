package com.alien.base.share

import android.content.Intent
import android.os.Bundle
import com.alien.base.share.data.ShareConstant
import com.alien.base.share.data.ShareEntity
import com.alien.base.share.manager.ShareManager

class ShareActivity: WXCallbackActivity() {

    lateinit var mShareEntity: ShareEntity
    lateinit var mPresenter: SharePresenter

    override fun onCreateInit(savedInstanceState: Bundle?) {
        super.onCreateInit(savedInstanceState)
        ShareManager.initSinaSdk(this)
        mShareEntity = intent.getSerializableExtra(ShareConstant.DATA) as ShareEntity
        mPresenter = SharePresenter(this, mShareEntity)
        mPresenter.init(this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mPresenter.onNewIntent(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onAuthSuccess(code: String?) {

    }

}