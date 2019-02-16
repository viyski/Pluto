package com.alien.modulea.ui.fragment

import com.alien.base.ui.BaseFragment
import com.alien.modulea.R

class FragmentE: BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.modulea_fragment_e

    override fun setUp() {
    }

    companion object {
        fun newInstance(): FragmentE{
            return FragmentE()
        }
    }

}