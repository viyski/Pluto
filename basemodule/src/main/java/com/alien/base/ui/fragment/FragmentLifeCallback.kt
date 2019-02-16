package com.alien.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.util.SimpleArrayMap
import android.view.View
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class FragmentLifeCallback @Inject constructor(): FragmentManager.FragmentLifecycleCallbacks() {

    @Inject
    lateinit var mMapFragmentLife: SimpleArrayMap<String, IFragmentLife>
    @Inject
    lateinit var mFragmentLifeProvider: Provider<IFragmentLife>

    override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
        if (f is IBaseFragment) {
            var iFragmentLife = mMapFragmentLife.get(f.toString())
            if (iFragmentLife == null || !iFragmentLife.isAdded()) {
                iFragmentLife = mFragmentLifeProvider.get()
                mMapFragmentLife.put(f.toString(), iFragmentLife)
            }
            iFragmentLife?.onAttach(f, context)
        }
    }

    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onCreate(savedInstanceState)
    }

    override fun onFragmentActivityCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onActivityCreate(savedInstanceState)
    }

    override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onCreateView(v, savedInstanceState)
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onStart()
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onResume()
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onPause()
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onStop()
    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onSaveInstanceState(outState)
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onDestroyView()
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onDestroy()

        mMapFragmentLife.remove(f.toString())
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
        val iFragmentLife = mMapFragmentLife.get(f.toString())
        iFragmentLife?.onDetach()
    }

    fun getFragmentLife(key: String): FragmentLife? {
        return mMapFragmentLife.get(key) as FragmentLife?
    }
}