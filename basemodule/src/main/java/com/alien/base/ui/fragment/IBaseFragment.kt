package com.alien.base.ui.fragment

import android.os.Bundle

interface IBaseFragment {

    fun onSaveState(bundleToSave: Bundle)

    fun onRestoreState(bundleToRestore: Bundle)

    fun isUseEventBus(): Boolean
}