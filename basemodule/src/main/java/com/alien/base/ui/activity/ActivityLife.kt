package com.alien.base.ui.activity

import android.app.Activity
import android.os.Bundle
import com.alien.base.modulekit.BaseModuleKit
import com.alien.base.tools.ActivityListManager
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.subjects.PublishSubject

class ActivityLife: IActivityLife {

    private var mActivity: Activity? = null
    private val mLifecycleSubject = PublishSubject.create<ActivityEvent>()

    override fun onCreate(activity: Activity, savedInstanceState: Bundle?) {
        mActivity = activity

        mLifecycleSubject.onNext(ActivityEvent.CREATE)

        var isNotAdd = false
        if (activity.intent != null) isNotAdd = activity.intent.getBooleanExtra(ActivityListManager.IS_NOT_ADD_ACTIVITY_LIST, false)

        if (!isNotAdd) BaseModuleKit.getInstance().activityListManager()!!.addActivity(activity)

        if ((mActivity as IBaseActivity).isUseEventBus()) {
            //            DevRing.busManager().register(mActivity);
        }
    }

    override fun onStart() {
        mLifecycleSubject.onNext(ActivityEvent.START)
    }

    override fun onResume() {
        BaseModuleKit.getInstance().activityListManager()?.setCurrentActivity(mActivity)
        mLifecycleSubject.onNext(ActivityEvent.RESUME)
    }

    override fun onPause() {
        mLifecycleSubject.onNext(ActivityEvent.PAUSE)
    }

    override fun onStop() {
        if (BaseModuleKit.getInstance().activityListManager()?.getCurrentActivity() === mActivity) {
            BaseModuleKit.getInstance().activityListManager()?.setCurrentActivity(null)
        }
        mLifecycleSubject.onNext(ActivityEvent.STOP)
    }

    override fun onSaveInstanceState(outState: Bundle?) {

    }

    override fun onDestroy() {
        mLifecycleSubject.onNext(ActivityEvent.DESTROY)

        BaseModuleKit.getInstance().activityListManager()?.removeActivity(mActivity)

        if ((mActivity as IBaseActivity).isUseEventBus()) {
//            DevRing.busManager().unregister(mActivity)
        }
        mActivity = null
    }

    fun getLifecycleSubject(): PublishSubject<ActivityEvent> {
        return mLifecycleSubject
    }

}