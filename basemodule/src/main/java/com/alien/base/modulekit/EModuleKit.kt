package com.alien.base.modulekit

import com.alien.base.di.AppComponent

class EModuleKit {

    private var component: AppComponent? = null

    companion object {
        private var instance: EModuleKit? = null

        fun getInstance(): EModuleKit{
            if (instance == null){
                synchronized(EModuleKit::class.java){
                    if (instance == null){
                        instance = EModuleKit()
                    }
                }
            }
            return instance!!
        }
    }

    fun init(delegate: AppModuleComponentDelegate){
        component = delegate.initAppComponent()
    }

    fun getComponent() = component

}