package com.alien.moduleb

import com.alien.base.di.AppComponent
import com.alien.base.modulekit.AppModuleComponentDelegate
import com.alien.base.modulekit.BModuleKit
import com.alien.base.modulekit.BaseModuleKit
import com.alien.moduleb.di.DaggerModuleBAppComponent
import com.alien.moduleb.serviceimpl.BusinessBServiceImpl
import com.android.componentlib.applicationlike.IApplicationLike
import com.android.componentlib.router.Router
import com.android.componentservice.moduleb.BusinessBService

class BAppLike: IApplicationLike {

    override fun onCreate() {
        Router.getInstance().addService(BusinessBService::class.java, BusinessBServiceImpl())
        BModuleKit.getInstance().init(object : AppModuleComponentDelegate {
            override fun initAppComponent(): AppComponent {
                return DaggerModuleBAppComponent.builder()
                    .baseAppComponent(BaseModuleKit.getInstance().getComponent())
                    .build()
            }
        })
    }

    override fun onStop() {

    }
}