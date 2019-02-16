package com.alien.modulea.di

import com.alien.base.di.AppComponent
import com.alien.base.di.BaseAppComponent
import com.alien.base.di.scope.AppScope
import com.alien.base.event.RxBus
import dagger.Component


@AppScope
@Component(dependencies = [(BaseAppComponent::class)], modules = [(ModuleAAppModule::class)])
interface ModuleAAppComponent : AppComponent{

    fun rxBus(): RxBus

}