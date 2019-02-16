package com.alien.base.http

class HttpThrowable(val code: Int, private val msg: String): Exception() {

    override val message: String?
        get() = msg
}