package com.alien.modulea.ui.fragment

import com.alien.base.modulekit.AModuleKit
import com.alien.base.ui.BaseFragment
import com.alien.modulea.di.DaggerFragmentComponent
import com.alien.modulea.di.FragmentComponent
import com.alien.modulea.di.ModuleAAppComponent

abstract class BaseInjectFragment: BaseFragment() {

    fun fragmentComponent(): FragmentComponent {
        return DaggerFragmentComponent.builder()
            .moduleAAppComponent(AModuleKit.getInstance().getComponent() as ModuleAAppComponent?)
            .build()
    }
}