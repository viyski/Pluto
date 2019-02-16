package com.alien.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.subjects.PublishSubject

class FragmentLife: IFragmentLife {

    private val SAVED_STATE = "saved_state"
    private val mLifecycleSubject = PublishSubject.create<FragmentEvent>()

    private var mFragment: Fragment? = null
    private var mContentView: View? = null
    private var mSavedState: Bundle? = null//用于保存/恢复数据

    override fun onAttach(fragment: Fragment, context: Context) {
        mFragment = fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mLifecycleSubject.onNext(FragmentEvent.CREATE)
        if ((mFragment as IBaseFragment).isUseEventBus()) {
//            DevRing.busManager().register(mFragment)
        }
    }

    override fun onCreateView(view: View, savedInstanceState: Bundle?) {
        mLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW)
        mContentView = view
    }

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        restoreStateFromArguments()
    }

    override fun onStart() {
        mLifecycleSubject.onNext(FragmentEvent.START)
    }

    override fun onResume() {
        mLifecycleSubject.onNext(FragmentEvent.RESUME)
    }

    override fun onPause() {
        mLifecycleSubject.onNext(FragmentEvent.PAUSE)
    }

    override fun onStop() {
        mLifecycleSubject.onNext(FragmentEvent.STOP)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        saveStateToArguments()
    }

    override fun onDestroyView() {
        mLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW)

        if (mContentView != null) {
            val parent = mContentView!!.parent as ViewGroup
            parent.removeView(mContentView)
        }

        saveStateToArguments()
    }

    override fun onDestroy() {
        mLifecycleSubject.onNext(FragmentEvent.DESTROY)

        if ((mFragment as IBaseFragment).isUseEventBus()) {
//            DevRing.busManager().unregister(mFragment)
        }
        mContentView = null
        mFragment = null
    }

    override fun onDetach() {
        mLifecycleSubject.onNext(FragmentEvent.DETACH)
    }

    override fun isAdded(): Boolean {
        return mFragment != null && mFragment!!.isAdded
    }

    private fun saveStateToArguments() {
        if (mFragment!!.view != null) {
            val state = Bundle()
            (mFragment as IBaseFragment).onSaveState(state)
            mSavedState = state
        }
        if (mSavedState != null) {
            val b = mFragment!!.arguments
            b?.putBundle(SAVED_STATE, mSavedState)
        }
    }

    private fun restoreStateFromArguments() {
        val b = mFragment!!.arguments
        if (b != null) {
            mSavedState = b.getBundle(SAVED_STATE)
            if (mSavedState != null) {
                (mFragment as IBaseFragment).onRestoreState(mSavedState!!)
            }
        }
    }

    fun getLifecycleSubject(): PublishSubject<FragmentEvent> {
        return mLifecycleSubject
    }
}