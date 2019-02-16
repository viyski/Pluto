package com.alien.modulec.ui.fragment

import com.alien.base.ui.BaseFragment
import com.alien.modulec.R

class FragmentC: BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.modulec_fragment_c

    override fun setUp() {
    }

    companion object {
        fun newInstance(): FragmentC{
            return FragmentC()
        }
    }

}