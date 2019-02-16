package com.alien.base.ui.activity

import android.app.Activity
import android.os.Bundle

interface IActivityLife {

    fun onCreate(activity: Activity, savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle?)

    fun onDestroy()
}