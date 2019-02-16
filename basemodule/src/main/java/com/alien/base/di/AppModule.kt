package com.alien.base.di

import android.app.Application
import android.content.Context
import com.alien.base.tools.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module(includes = [(SingletonModule::class)])
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()
}