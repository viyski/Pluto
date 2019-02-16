package com.alien.base.modulekit

import com.alien.base.di.AppComponent

class CModuleKit {

    private var component: AppComponent? = null

    companion object {
        private var instance: CModuleKit? = null

        fun getInstance(): CModuleKit{
            if (instance == null){
                synchronized(CModuleKit::class.java){
                    if (instance == null){
                        instance = CModuleKit()
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