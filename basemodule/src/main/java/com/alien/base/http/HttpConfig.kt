package com.alien.base.http

import android.support.annotation.NonNull
import com.alien.base.Platform

class HttpConfig() {

    var platform: Int = Platform.PF_A

    var headerMap: MutableMap<String, String> = mutableMapOf()

    fun setHttpHeaders(@NonNull map: MutableMap<String, String>){
        headerMap = map
    }
}