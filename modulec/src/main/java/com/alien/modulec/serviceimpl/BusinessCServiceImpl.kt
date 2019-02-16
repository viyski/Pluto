package com.alien.modulec.serviceimpl

import android.support.v4.app.Fragment
import com.alien.modulec.ui.fragment.FragmentC
import com.android.componentservice.moduleb.BusinessBService
import com.android.componentservice.modulec.BusinessCService

class BusinessCServiceImpl: BusinessCService {

    override fun getBusinessFlag(): String = "com.alien.modulec.service.c"

    override fun getFragment(): Fragment = FragmentC.newInstance()
}