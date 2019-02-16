package com.alien.base.modulekit

import android.app.Application
import com.alien.base.BaseApplication
import com.alien.base.di.AppModule
import com.alien.base.di.BaseAppComponent
import com.alien.base.di.DaggerBaseAppComponent

class BaseModuleKit {

    private var component: BaseAppComponent? = null
    private var application: Application? = null

    companion object {
        private var instance: BaseModuleKit? = null

        fun getInstance(): BaseModuleKit{
            if (instance == null){
                synchronized(BaseModuleKit::class.java){
                    if (instance == null){
                        instance = BaseModuleKit()
                        instance?.application = BaseApplication.getInstance()
                        instance?.component = DaggerBaseAppComponent.builder()
                                .appModule(AppModule(instance?.application!!))
                                .build()
                    }
                }
            }

            return instance!!
        }
    }

    fun getComponent() = component

    fun getApplication() = application

    fun activityListManager() = component?.activityListManager()
}