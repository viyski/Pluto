package com.android.componentlib.router

import android.text.TextUtils
import com.android.componentlib.applicationlike.IApplicationLike
import java.util.*

class Router {

    private val services = HashMap<String, Any>()

    companion object {
        private val components = HashMap<String, IApplicationLike>()
        private var instance: Router? = null

        fun getInstance(): Router {
            if (instance == null) {
                synchronized(Router::class.java) {
                    if (instance == null) {
                        instance = Router()
                    }
                }
            }
            return instance!!
        }

        fun registerComponent(classname: String?) {
            if (TextUtils.isEmpty(classname)) {
                return
            }
            if (components.keys.contains(classname)) {
                return
            }
            try {
                val clazz = Class.forName(classname)
                val applicationLike = clazz.newInstance() as IApplicationLike
                applicationLike.onCreate()
                components[classname!!] = applicationLike
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun unregisterComponent(classname: String?) {
            if (TextUtils.isEmpty(classname)) {
                return
            }
            if (components.keys.contains(classname)) {
                components[classname]?.onStop()
                components.remove(classname)
                return
            }
            try {
                val clazz = Class.forName(classname)
                val applicationLike = clazz.newInstance() as IApplicationLike
                applicationLike.onStop()
                components.remove(classname)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    @Synchronized
    fun <T> addService(tClass: Class<T>?, t: T?) {
        if (tClass == null || t == null) {
            return
        }
        services.put(tClass.simpleName, t)
    }

    @Synchronized
    fun <T> getService(tClass: Class<T>?): T {
        return if (tClass == null || !services.containsKey(tClass.simpleName)) {
            null
        } else services[tClass.simpleName] as T?!!
    }

    @Synchronized
    fun <T> removeService(tClass: Class<T>?) {
        if (tClass == null || !services.containsKey(tClass.simpleName)) {
            return
        }
        services.remove(tClass.simpleName)
    }
}