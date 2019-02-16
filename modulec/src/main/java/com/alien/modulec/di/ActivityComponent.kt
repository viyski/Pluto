package com.alien.modulec.di

import com.alien.base.di.BaseViewModule
import com.alien.base.di.scope.PerView
import dagger.Component

@PerView
@Component(dependencies = [(ModuleCAppComponent::class)], modules = [(BaseViewModule::class)])
interface ActivityComponent {

}