package com.alien.base.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.WindowManager
import com.alien.base.di.BaseViewComponent
import com.alien.base.di.BaseViewModule
import com.alien.base.di.DaggerBaseViewComponent
import com.alien.base.mvp.MVPView
import com.alien.base.ui.activity.IBaseActivity
import com.github.zackratos.ultimatebar.ultimateBarBuilder

abstract class AbstractActivity : AppCompatActivity(), IBaseActivity, MVPView {
    private var mBaseViewComponent: BaseViewComponent? = null

    fun component(): BaseViewComponent? {
        if (mBaseViewComponent == null) {
            mBaseViewComponent = DaggerBaseViewComponent.builder()
                    .baseViewModule(BaseViewModule(this))
                    .build()
        }
        return mBaseViewComponent
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFontScale()
        component()!!.inject(this)
        ultimateBarBuilder().statusDark(true)
                .statusDrawable(ColorDrawable(statusColor()))
                .create()
                .drawableBar()

        if (isFullScreen()) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    private fun initFontScale() {
        val configuration = resources.configuration
        configuration.fontScale = 1.toFloat()
        //0.85 小, 1 标准大小, 1.15 大，1.3 超大 ，1.45 特大
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }

    override fun statusColor(): Int = Color.WHITE

    override fun isFullScreen(): Boolean = false

    override fun isImmersionBar(): Boolean = false
}