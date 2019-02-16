package com.alien.moduled.ui.fragment

import com.alien.base.ui.BaseFragment
import com.alien.moduled.R

class FragmentD: BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.moduled_fragment_d

    override fun setUp() {
    }

    companion object {
        fun newInstance(): FragmentD{
            return FragmentD()
        }
    }

}