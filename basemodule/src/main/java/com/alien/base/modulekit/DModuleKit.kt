package com.alien.base.modulekit

import com.alien.base.di.AppComponent

class DModuleKit {

    private var component: AppComponent? = null

    companion object {
        private var instance: DModuleKit? = null

        fun getInstance(): DModuleKit{
            if (instance == null){
                synchronized(DModuleKit::class.java){
                    if (instance == null){
                        instance = DModuleKit()
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