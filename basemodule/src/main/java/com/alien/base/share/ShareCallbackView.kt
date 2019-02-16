package com.alien.base.share

interface ShareCallbackView {

    fun onShareFail(err: String?)

    fun onShareCancel()

    fun onShareSuccess()
}