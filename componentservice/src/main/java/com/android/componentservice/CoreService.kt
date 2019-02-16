package com.android.componentservice

import android.support.v4.app.Fragment

interface CoreService {

    fun getBusinessFlag(): String

    fun getFragment(): Fragment
}