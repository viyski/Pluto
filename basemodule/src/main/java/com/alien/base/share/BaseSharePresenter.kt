package com.alien.base.share

import android.content.Intent

interface BaseSharePresenter {

    fun shareWithQQ()

    fun shareWithQZone()

    fun shareWithWechat()

    fun shareWithSina()

    fun onNewIntent(intent: Intent?)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun onDestroy()
}