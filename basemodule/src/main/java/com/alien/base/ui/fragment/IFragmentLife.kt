package com.alien.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

interface IFragmentLife {

    fun onAttach(fragment: Fragment, context: Context)

    fun onCreate(savedInstanceState: Bundle?)

    fun onCreateView(view: View, savedInstanceState: Bundle?)

    fun onActivityCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle?)

    fun onDestroyView()

    fun onDestroy()

    fun onDetach()

    fun isAdded(): Boolean
}