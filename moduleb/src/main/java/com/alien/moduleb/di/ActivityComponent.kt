package com.alien.moduleb.di

import com.alien.base.di.BaseViewModule
import com.alien.base.di.scope.PerView
import dagger.Component

@PerView
@Component(dependencies = [(ModuleBAppComponent::class)], modules = [(BaseViewModule::class)])
interface ActivityComponent {

}