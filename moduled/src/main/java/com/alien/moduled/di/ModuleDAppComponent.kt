package com.alien.moduled.di

import com.alien.base.di.AppComponent
import com.alien.base.di.BaseAppComponent
import com.alien.base.di.scope.AppScope
import com.alien.base.event.RxBus
import dagger.Component


@AppScope
@Component(dependencies = [(BaseAppComponent::class)], modules = [(ModuleDAppModule::class)])
interface ModuleDAppComponent : AppComponent{

    fun rxBus(): RxBus

}