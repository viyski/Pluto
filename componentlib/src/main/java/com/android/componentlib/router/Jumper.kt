package com.android.componentlib.router

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter

object Jumper {

    fun check(context: Context, uid: Long): Boolean{
        if (uid == 0L){
            login(context)
            return false
        }
        return true
    }

    fun init(debug: Boolean, application: Application){
        if (debug){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }

    fun main(){
        ARouter.getInstance().build(RouterPath.MAIN_PATH).navigation()
    }

    fun login(context: Context){
        ARouter.getInstance().build(RouterPath.PATH_LOGIN).navigation(context)
    }


}