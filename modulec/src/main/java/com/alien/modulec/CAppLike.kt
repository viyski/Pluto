package com.alien.modulec

import com.alien.base.di.AppComponent
import com.alien.base.modulekit.AppModuleComponentDelegate
import com.alien.base.modulekit.BModuleKit
import com.alien.base.modulekit.BaseModuleKit
import com.alien.modulec.di.DaggerModuleCAppComponent
import com.alien.modulec.serviceimpl.BusinessCServiceImpl
import com.android.componentlib.applicationlike.IApplicationLike
import com.android.componentlib.router.Router
import com.android.componentservice.modulec.BusinessCService

class CAppLike: IApplicationLike {

    override fun onCreate() {
        Router.getInstance().addService(BusinessCService::class.java, BusinessCServiceImpl())
        BModuleKit.getInstance().init(object : AppModuleComponentDelegate {
            override fun initAppComponent(): AppComponent {
                return DaggerModuleCAppComponent.builder()
                    .baseAppComponent(BaseModuleKit.getInstance().getComponent())
                    .build()
            }
        })
    }

    override fun onStop() {

    }
}