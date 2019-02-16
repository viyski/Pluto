package com.alien.base.modulekit

import com.alien.base.di.AppComponent

interface AppModuleComponentDelegate {

    fun initAppComponent(): AppComponent
}