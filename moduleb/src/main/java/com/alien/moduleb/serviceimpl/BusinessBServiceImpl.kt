package com.alien.moduleb.serviceimpl

import android.support.v4.app.Fragment
import com.alien.moduleb.ui.fragment.FragmentB
import com.android.componentservice.modulea.BusinessAService
import com.android.componentservice.moduleb.BusinessBService

class BusinessBServiceImpl: BusinessBService {

    override fun getBusinessFlag(): String = "com.alien.moduleb.service.a"

    override fun getFragment(): Fragment = FragmentB.newInstance()
}