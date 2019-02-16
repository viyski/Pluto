package com.alien.moduled.serviceimpl

import android.support.v4.app.Fragment
import com.alien.moduled.ui.fragment.FragmentD
import com.android.componentservice.moduled.BusinessDService

class BusinessDServiceImpl: BusinessDService {

    override fun getBusinessFlag(): String = "com.alien.moduled.service.d"

    override fun getFragment(): Fragment = FragmentD.newInstance()
}