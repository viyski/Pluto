package com.alien.base.di

import com.alien.base.Constants
import com.alien.base.event.RxBus
import com.alien.base.http.HttpApiService
import com.alien.base.http.RestApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SingletonModule {

    @Provides
    @Singleton
    fun rxBus(): RxBus = RxBus.getInstance()

    @Provides
    @Singleton
    fun provideHttpApiService(restApi: RestApi): HttpApiService {
        return restApi.retrofitNet(Constants.BASE_URL).create(HttpApiService::class.java)
    }
}