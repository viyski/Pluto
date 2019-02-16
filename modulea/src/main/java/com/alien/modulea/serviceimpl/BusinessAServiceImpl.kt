package com.alien.modulea.serviceimpl

import android.support.v4.app.Fragment
import com.alien.modulea.ui.fragment.FragmentA
import com.alien.modulea.ui.fragment.FragmentE
import com.android.componentservice.modulea.BusinessAService

class BusinessAServiceImpl: BusinessAService {

    override fun getBusinessFlag(): String = "com.alien.modulea.service.a"

    override fun getFragment(): Fragment = FragmentA.newInstance()

    override fun getFragmentE(): Fragment = FragmentE.newInstance()
}