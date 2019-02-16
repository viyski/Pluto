package com.alien.base.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alien.base.Constants
import com.alien.base.data.pref.UserSp
import com.alien.base.di.BaseViewComponent
import com.alien.base.di.BaseViewModule
import com.alien.base.di.DaggerBaseViewComponent
import com.alien.base.http.ApiError
import com.alien.base.mvp.MVPView
import com.alien.base.ui.fragment.IBaseFragment
import com.alien.baselib.ExtendFun.toast
import com.android.componentlib.router.Jumper

abstract class BaseFragment: Fragment(), IBaseFragment, MVPView {

    var page: Int = Constants.START_PAGE

    lateinit var mRooView: View

    protected var mContext: Context? = null

    private var mBaseViewComponent: BaseViewComponent? = null

    fun component(): BaseViewComponent? {
        if (mBaseViewComponent == null) {
            mBaseViewComponent = DaggerBaseViewComponent.builder()
                    .baseViewModule(BaseViewModule(activity!!))
                    .build()
        }
        return mBaseViewComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component()!!.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRooView = inflater.inflate(getLayoutRes(), container, false)
        return mRooView
    }

    fun getBaseActivity(): BaseActivity? {
        return if (activity != null && activity is BaseActivity) activity as BaseActivity else null
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mContext == null) {
            mContext = context
        }
        setUp()
    }

    override fun getContext(): Context? {
        return if (super.getContext() == null) mContext else super.getContext()
    }

    fun getStringIfAdded(@StringRes resId: Int): String {
        return if (isAdded) resources.getString(resId) else ""
    }

    fun getStringIfAdded(@StringRes resId: Int, vararg formatArgs: Any): String {
        return if (isAdded) resources.getString(resId, *formatArgs) else ""
    }


    override fun onSaveState(bundleToSave: Bundle) {

    }

    override fun onRestoreState(bundleToRestore: Bundle) {
    }

    override fun isUseEventBus(): Boolean = false

    abstract fun getLayoutRes(): Int

    abstract fun setUp()

    override fun onError(error: ApiError) {
        if(error.code == 1){
            UserSp.clear()
            Jumper.login(context = context!!)
        }
        toast(error.msg)
        page--
    }

    override fun context(): Context = getContext()!!

    override fun showToast(text: String) {
        toast(text)
    }
}