package com.alien.modulea.ui.fragment

import com.alien.base.ui.BaseFragment
import com.alien.modulea.R

class FragmentA: BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.modulea_fragment_a

    override fun setUp() {
    }

    companion object {
        fun newInstance(): FragmentA{
            return FragmentA()
        }
    }

}