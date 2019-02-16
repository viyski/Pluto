package com.alien.base.di

import android.app.Activity
import com.alien.base.di.scope.PerView
import com.alien.base.ui.AbstractActivity
import com.alien.base.ui.BaseFragment
import dagger.Component

@PerView
@Component(modules = [(BaseViewModule::class)])
interface BaseViewComponent {

    fun activity(): Activity

    fun inject(activity: AbstractActivity)

    fun inject(fragment: BaseFragment)

}