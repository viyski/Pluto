package com.alien.base.event

data class BaseEvent(val type: Int, val any: Any? = null) {

    companion object {
        const val LOGIN_SUCCESS = 888
    }
}