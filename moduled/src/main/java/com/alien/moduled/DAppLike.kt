package com.alien.moduled

import com.alien.base.di.AppComponent
import com.alien.base.modulekit.AppModuleComponentDelegate
import com.alien.base.modulekit.BModuleKit
import com.alien.base.modulekit.BaseModuleKit
import com.alien.moduled.di.DaggerModuleDAppComponent
import com.alien.moduled.serviceimpl.BusinessDServiceImpl
import com.android.componentlib.applicationlike.IApplicationLike
import com.android.componentlib.router.Router
import com.android.componentservice.moduled.BusinessDService

class DAppLike: IApplicationLike {

    override fun onCreate() {
        Router.getInstance().addService(BusinessDService::class.java, BusinessDServiceImpl())
        BModuleKit.getInstance().init(object : AppModuleComponentDelegate {
            override fun initAppComponent(): AppComponent {
                return DaggerModuleDAppComponent.builder()
                    .baseAppComponent(BaseModuleKit.getInstance().getComponent())
                    .build()
            }
        })
    }

    override fun onStop() {

    }
}