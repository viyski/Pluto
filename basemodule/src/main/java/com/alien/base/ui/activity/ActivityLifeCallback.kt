package com.alien.base.ui.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.util.SimpleArrayMap
import android.util.Log
import com.alien.base.ui.fragment.FragmentLifeCallback
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ActivityLifeCallback @Inject constructor(): Application.ActivityLifecycleCallbacks {

    @Inject
    lateinit var mMapActivityLife: SimpleArrayMap<String, IActivityLife>
    @Inject
    lateinit var mActivityLifeProvider: Provider<IActivityLife>
    @Inject
    lateinit var mFragmentLifeCallbackProvider: Lazy<FragmentLifeCallback>

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        Log.v("ActivityLifeCallback", " ==onActivityCreated== $activity")
        if (activity is IBaseActivity) {
            var iActivityLife = mMapActivityLife.get(activity.toString())
            if (iActivityLife == null) {
                iActivityLife = mActivityLifeProvider.get()
                mMapActivityLife.put(activity.toString(), iActivityLife)
            }
            iActivityLife!!.onCreate(activity, bundle)
        }

        val isUseFragment = if (activity is IBaseActivity) (activity as IBaseActivity).isUseFragment() else true
        if (activity is FragmentActivity && isUseFragment) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(mFragmentLifeCallbackProvider.get(), true)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        val iActivityLife = mMapActivityLife.get(activity.toString())
        iActivityLife?.onStart()
    }

    override fun onActivityResumed(activity: Activity) {
        val iActivityLife = mMapActivityLife.get(activity.toString())
        iActivityLife?.onResume()
    }

    override fun onActivityPaused(activity: Activity) {
        val iActivityLife = mMapActivityLife.get(activity.toString())
        iActivityLife?.onPause()
    }

    override fun onActivityStopped(activity: Activity) {
        val iActivityLife = mMapActivityLife.get(activity.toString())
        iActivityLife?.onStop()
    }

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle?) {
        val iActivityLife = mMapActivityLife.get(activity.toString())
        iActivityLife?.onSaveInstanceState(bundle)
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d("ActivityLifeCallback", " ==onActivityDestroyed== $activity")
        val iActivityLife = mMapActivityLife.get(activity.toString())
        iActivityLife?.onDestroy()
        mMapActivityLife.remove(activity.toString())
    }

    fun getActivityLife(key: String): ActivityLife {
        return (mMapActivityLife.get(key) as ActivityLife?)!!
    }

}