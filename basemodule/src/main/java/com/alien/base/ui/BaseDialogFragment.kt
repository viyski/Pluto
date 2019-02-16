package com.alien.base.ui

import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.alien.base.R

abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle)
    }
}