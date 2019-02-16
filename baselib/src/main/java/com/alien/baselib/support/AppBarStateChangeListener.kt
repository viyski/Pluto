package com.alien.baselib.support

import android.support.annotation.IntDef
import android.support.design.widget.AppBarLayout

abstract class AppBarStateChangeListener: AppBarLayout.OnOffsetChangedListener {

    private var state = IDLE

    companion object {
        const val EXPANDED = 0
        const val COLLAPSED = 1
        const val IDLE = 2
    }

    @IntDef(EXPANDED, COLLAPSED, IDLE)
    @Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    annotation class State{}

    abstract fun onStateChanged(appBar: AppBarLayout, @State state: Int, offset: Int)

    override fun onOffsetChanged(appBar: AppBarLayout, offset: Int) {
        if (offset == 0){
            if (state != EXPANDED){
                onStateChanged(appBar, EXPANDED, offset)
            }
            state = EXPANDED
        }else if (Math.abs(offset) >= appBar.totalScrollRange){
            if (state != COLLAPSED){
                onStateChanged(appBar, COLLAPSED, offset)
            }
            state = COLLAPSED
        }else{
            onStateChanged(appBar, IDLE, offset)
            state = IDLE
        }
    }
}