package com.alien.moduled.di

import com.alien.base.di.BaseViewModule
import com.alien.base.di.scope.PerView
import dagger.Component

@PerView
@Component(dependencies = [(ModuleDAppComponent::class)], modules = [(BaseViewModule::class)])
interface ActivityComponent {

}