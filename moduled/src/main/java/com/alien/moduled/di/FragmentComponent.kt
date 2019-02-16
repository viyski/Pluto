package com.alien.moduled.di

import com.alien.base.di.BaseViewModule
import com.alien.base.di.scope.PerView
import com.alien.moduled.ui.fragment.FragmentD
import dagger.Component

@PerView
@Component(dependencies = [(ModuleDAppComponent::class)], modules = [(BaseViewModule::class)])
interface FragmentComponent {
    
    fun inject(fragment: FragmentD)
}