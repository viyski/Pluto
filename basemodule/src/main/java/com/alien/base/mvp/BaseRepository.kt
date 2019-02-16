package com.alien.base.mvp

import com.alien.base.data.pref.AppSp
import com.alien.base.data.pref.UserSp

abstract class BaseRepository: MVPRepository {

    override fun uid(): String = if (isUserLogin()) UserSp.uid().toString() else AppSp.uuid()

    override fun isUserLogin(): Boolean = UserSp.uid() != 0L

    override fun performUserLogout() = UserSp.clear()

    override fun isUuid(): String = if (isUserLogin()) UserSp.uid().toString() else AppSp.uuid()

    override fun isUid(): Long = UserSp.uid()
}