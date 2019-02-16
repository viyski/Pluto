package com.alien.moduleb.ui.fragment

import com.alien.base.ui.BaseFragment
import com.alien.moduleb.R

class FragmentB: BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.moduleb_fragment_b

    override fun setUp() {
    }

    companion object {
        fun newInstance(): FragmentB{
            return FragmentB()
        }
    }

}