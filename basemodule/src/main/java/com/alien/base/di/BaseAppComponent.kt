package com.alien.base.di

import com.alien.base.event.RxBus
import com.alien.base.http.HttpApiService
import com.alien.base.http.HttpConfig
import com.alien.base.http.RestApi
import com.alien.base.tools.ActivityListManager
import com.alien.base.tools.SchedulerProvider
import com.alien.base.tools.permission.PermissionManager
import com.alien.base.ui.activity.ActivityLifeCallback
import com.alien.base.ui.fragment.FragmentLifeCallback
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (SingletonModule::class), (OtherModule::class)])
interface BaseAppComponent {

    fun restApi(): RestApi

    fun rxBus(): RxBus

    fun apiService(): HttpApiService

    fun permissionManager(): PermissionManager

    fun activityListManager(): ActivityListManager

    fun activityLifeCallback(): ActivityLifeCallback

    fun fragmentLifeCallback(): FragmentLifeCallback

    fun schedulerProvider(): SchedulerProvider

    fun httpConfig(): HttpConfig

}