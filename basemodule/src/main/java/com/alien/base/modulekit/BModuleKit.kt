package com.alien.base.modulekit

import com.alien.base.di.AppComponent

class BModuleKit {

    private var component: AppComponent? = null

    companion object {
        private var instance: BModuleKit? = null

        fun getInstance(): BModuleKit{
            if (instance == null){
                synchronized(BModuleKit::class.java){
                    if (instance == null){
                        instance = BModuleKit()
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