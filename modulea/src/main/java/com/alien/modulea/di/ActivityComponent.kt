package com.alien.modulea.di

import com.alien.base.di.BaseViewModule
import com.alien.base.di.scope.PerView
import dagger.Component

@PerView
@Component(dependencies = [(ModuleAAppComponent::class)], modules = [(BaseViewModule::class)])
interface ActivityComponent {

}