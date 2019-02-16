package com.alien.base.di

import android.support.v4.util.SimpleArrayMap
import com.alien.base.http.HttpConfig
import com.alien.base.tools.ActivityListManager
import com.alien.base.tools.permission.PermissionManager
import com.alien.base.ui.activity.ActivityLife
import com.alien.base.ui.activity.IActivityLife
import com.alien.base.ui.fragment.FragmentLife
import com.alien.base.ui.fragment.IFragmentLife
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class OtherModule {

    @Provides
    fun httpConfig(): HttpConfig {
        return HttpConfig()
    }

    @Singleton
    @Provides
    fun permissionManager(): PermissionManager {
        return PermissionManager()
    }

    @Singleton
    @Provides
    fun activityListManager(): ActivityListManager {
        return ActivityListManager()
    }

    @Singleton
    @Provides
    fun iActivityLifes(): SimpleArrayMap<String, IActivityLife> {
        return SimpleArrayMap()
    }

    @Provides
    fun iActivityLife(): IActivityLife {
        return ActivityLife()
    }

    @Singleton
    @Provides
    fun iFragmentLifes(): SimpleArrayMap<String, IFragmentLife> {
        return SimpleArrayMap()
    }

    @Provides
    fun iFragmentLife(): IFragmentLife {
        return FragmentLife()
    }

}