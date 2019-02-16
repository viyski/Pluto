package com.alien.base.di

import android.app.Activity
import com.alien.base.di.scope.PerView
import dagger.Module
import dagger.Provides

@Module
class BaseViewModule(val activity: Activity) {

    @Provides
    @PerView
    fun provideActivity() = activity
}