package com.alien.base.mvp

interface MVPRepository {

    fun uid(): String

    fun isUserLogin(): Boolean

    fun isUuid(): String

    fun isUid(): Long

    fun performUserLogout()
}