package com.alien.base.ui.activity

interface IBaseActivity {

    fun isUseEventBus(): Boolean

    fun isUseFragment(): Boolean

    fun statusColor(): Int

    fun isFullScreen(): Boolean

    fun isImmersionBar(): Boolean
}