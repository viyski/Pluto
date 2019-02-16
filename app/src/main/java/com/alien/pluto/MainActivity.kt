package com.alien.pluto

import android.Manifest
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alien.base.tools.permission.PermissionListener
import com.alien.base.ui.BaseActivity
import com.alien.baselib.support.ViewPagerAdapter
import com.alien.baselib.widget.TabRadioLayout
import com.android.componentlib.router.Router
import com.android.componentlib.router.RouterPath
import com.android.componentservice.modulea.BusinessAService
import com.android.componentservice.moduleb.BusinessBService
import com.android.componentservice.modulec.BusinessCService
import com.android.componentservice.moduled.BusinessDService
import kotlinx.android.synthetic.main.activity_main.*


@Route(path = RouterPath.MAIN_PATH)
class MainActivity: BaseActivity(), PermissionListener {

    override fun getResLayout(): Int = R.layout.activity_main

    override fun onCreateInit(savedInstanceState: Bundle?) {
        requestPermissions()
        setupTab()
    }

    private fun requestPermissions(){
        App.getInstance().baseAppComponent().permissionManager().requestEachCombined(this, this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun setupTab() {
        val fragments = mutableListOf(
            Router.getInstance().getService(BusinessAService::class.java).getFragment(),
            Router.getInstance().getService(BusinessBService::class.java).getFragment(),
            Router.getInstance().getService(BusinessCService::class.java).getFragment(),
            Router.getInstance().getService(BusinessDService::class.java).getFragment(),
            Router.getInstance().getService(BusinessAService::class.java).getFragmentE())
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager,fragments = fragments, titles = mutableListOf())
        viewPager.offscreenPageLimit = fragments.size
        tabLayout.addTab(tabLayout.newTab().setTabText(R.string.str_tab_home).setIcon(R.drawable.btn_tab_home))
        tabLayout.addTab(tabLayout.newTab().setTabText(R.string.str_tab_live).setIcon(R.drawable.btn_tab_live))
        tabLayout.addTab(tabLayout.newTab().setTabText(R.string.str_tab_more).setIcon( R.drawable.btn_tab_dynamic))
        tabLayout.addTab(tabLayout.newTab().setTabText(R.string.str_tab_message).setIcon( R.drawable.btn_tab_message))
        tabLayout.addTab(tabLayout.newTab().setTabText(R.string.str_tab_me).setIcon( R.drawable.btn_tab_me))
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(position: Int) {
            }

            override fun onPageScrolled(position: Int, p1: Float, p2: Int) {
            }

            override fun onPageSelected(position: Int) {
                tabLayout.onPageSelected(position)
            }

        })
        tabLayout.addOnTabSelectedListener(TabRadioLayout.ViewPagerOnTabSelectedListener(viewPager))
    }

    override fun onGranted(permissionName: String?) {

    }

    override fun onDenied(permissionName: String?) {

    }

    override fun onDeniedWithNeverAsk(permissionName: String?) {

    }
}
