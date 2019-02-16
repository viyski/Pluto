package com.alien.pluto

import com.alien.base.BaseApplication
import com.android.componentlib.router.Jumper
import com.android.componentlib.router.Router


class App : BaseApplication(){

    companion object {
        private var instance: App? = null
        fun getInstance(): App = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Router.registerComponent("com.alien.modulea.AAppLike")
        Router.registerComponent("com.alien.moduleb.BAppLike")
        Router.registerComponent("com.alien.modulec.CAppLike")
        Router.registerComponent("com.alien.moduled.DAppLike")
    }

    override fun initComponentDi() {

    }

    override fun registerRouter() {
        Jumper.init(BuildConfig.DEBUG, this)
    }
}