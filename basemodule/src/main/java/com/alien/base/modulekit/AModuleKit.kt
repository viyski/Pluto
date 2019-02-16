package com.alien.base.modulekit

import com.alien.base.di.AppComponent

class AModuleKit {

    private var component: AppComponent? = null

    companion object {
        private var instance: AModuleKit? = null

        fun getInstance(): AModuleKit{
            if (instance == null){
                synchronized(AModuleKit::class.java){
                    if (instance == null){
                        instance = AModuleKit()
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