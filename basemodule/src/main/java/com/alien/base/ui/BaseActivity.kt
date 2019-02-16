package com.alien.base.ui

import android.content.Context
import android.os.Bundle
import com.alien.base.Constants
import com.alien.base.data.pref.UserSp
import com.alien.base.http.ApiError
import com.alien.baselib.ExtendFun.toast
import com.android.componentlib.router.Jumper

abstract class BaseActivity: AbstractActivity() {

    var page: Int = Constants.START_PAGE

    companion object {
        const val RESULT_DELETE = 2
        const val RESULT_QR_SUCCESS = 3
        const val RESULT_EDIT = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(getResLayout() != 0) {
            setContentView(getResLayout())
        }
        onCreateInit(savedInstanceState)
    }

    abstract fun onCreateInit(savedInstanceState: Bundle?)

    abstract fun getResLayout(): Int

    override fun isUseEventBus(): Boolean = false

    override fun isUseFragment(): Boolean = false

    override fun onError(error: ApiError){
        if(error.code == 1){
            UserSp.clear()
            Jumper.login(context = this)
        }
        toast(error.msg)
        page--
    }

    override fun context(): Context = this

    override fun showToast(text: String) {
        toast(text)
    }
}