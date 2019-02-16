package com.alien.modulea

import com.alien.base.di.AppComponent
import com.alien.base.modulekit.AModuleKit
import com.alien.base.modulekit.AppModuleComponentDelegate
import com.alien.base.modulekit.BaseModuleKit
import com.alien.modulea.di.DaggerModuleAAppComponent
import com.alien.modulea.serviceimpl.BusinessAServiceImpl
import com.android.componentlib.applicationlike.IApplicationLike
import com.android.componentlib.router.Router
import com.android.componentservice.modulea.BusinessAService

class AAppLike: IApplicationLike {

    override fun onCreate() {
        Router.getInstance().addService(BusinessAService::class.java, BusinessAServiceImpl())
        AModuleKit.getInstance().init(object : AppModuleComponentDelegate {
            override fun initAppComponent(): AppComponent {
                return DaggerModuleAAppComponent.builder()
                    .baseAppComponent(BaseModuleKit.getInstance().getComponent())
                    .build()
            }
        })
    }

    override fun onStop() {

    }
}