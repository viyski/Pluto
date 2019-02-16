package com.alien.base.mvp

import android.content.Context
import com.alien.base.http.ApiError

interface MVPView {

    fun onError(error: ApiError)

    fun context(): Context

    fun showToast(text: String)
}