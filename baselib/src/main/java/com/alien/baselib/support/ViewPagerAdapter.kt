package com.alien.baselib.support

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter

class ViewPagerAdapter(fm: FragmentManager,
                       private val fragments: MutableList<Fragment>,
                       private val titles: MutableList<String> = mutableListOf()): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return if (titles.isEmpty()){
            super.getPageTitle(position)
        }else{
            titles[position]
        }
    }

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}