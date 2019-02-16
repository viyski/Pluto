package com.alien.base

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.alien.base.di.BaseAppComponent
import com.alien.base.modulekit.BaseModuleKit
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

abstract class BaseApplication : MultiDexApplication() {

    private var baseAppComponent: BaseAppComponent? = null

    fun baseAppComponent(): BaseAppComponent {
        if (baseAppComponent == null)
            baseAppComponent = BaseModuleKit.getInstance().getComponent()

        return baseAppComponent!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Logger.addLogAdapter(AndroidLogAdapter(PrettyFormatStrategy.newBuilder()
                .tag("LOG_TAG")
                .build()))
        initComponentDi()
        registerRouter()
        registerActivityLifecycleCallbacks(baseAppComponent()?.activityLifeCallback())

        // 配置httpHeaders
        baseAppComponent().httpConfig().setHttpHeaders(mutableMapOf())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        private var instance: BaseApplication? = null

        fun getInstance(): BaseApplication = instance!!
    }

    abstract fun initComponentDi()

    abstract fun registerRouter()
}