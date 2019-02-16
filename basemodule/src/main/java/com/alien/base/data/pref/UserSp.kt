package com.alien.base.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.alien.base.data.entity.User
import com.alien.base.modulekit.BaseModuleKit

object UserSp {

    private var mSharedPreferences: SharedPreferences? = null
    private const val UID = "uid"
    private const val LOGIN_KEY = "login_key"
    private const val TOKEN = "token"
    private const val USER_HEAD = "user_head"
    private const val USER_NAME = "user_name"
    private const val USER_GENDER = "user_gender"
    private const val USER_ABOUT = "user_about"
    private const val USER_BIRDAY = "user_birday"

    init {
        mSharedPreferences = BaseModuleKit.getInstance().getApplication()?.getSharedPreferences("user", Context.MODE_PRIVATE)
    }

    fun uid(): Long = getLong(UID, 0L)

    fun loginKey(): String  = getString(LOGIN_KEY, "").toString()

    fun token(): String  = getString(TOKEN, "").toString()

    fun name(): String  = getString(USER_NAME, "").toString()

    fun head(): String  = getString(USER_HEAD, "").toString()

    fun about(): String  = getString(USER_ABOUT, "").toString()

    fun saveUser(user: User, loginKey: String) {
        put(UID, user.uid)
        put(LOGIN_KEY, loginKey)
    }

    fun put(key: String, value: String?) {
        if (mSharedPreferences == null) {
            return
        }
        mSharedPreferences?.edit()?.putString(key, value)?.apply()
    }

    fun put(key: String, value: Boolean) {
        if (mSharedPreferences == null) {
            return
        }
        mSharedPreferences?.edit()?.putBoolean(key, value)?.apply()
    }

    fun put(key: String, value: Float) {
        if (mSharedPreferences == null) {
            return
        }
        mSharedPreferences?.edit()?.putFloat(key, value)?.apply()
    }

    fun put(key: String, value: Int) {
        if (mSharedPreferences == null) {
            return
        }
        mSharedPreferences?.edit()?.putInt(key, value)?.apply()
    }

    fun put(key: String, value: Long) {
        if (mSharedPreferences == null) {
            return
        }
        mSharedPreferences?.edit()?.putLong(key, value)?.apply()
    }

    fun put(key: String, value: Set<String>) {
        if (mSharedPreferences == null) {
            return
        }
        mSharedPreferences?.edit()?.putStringSet(key, value)?.apply()
    }

    fun getString(key: String, defValue: String = ""): String? {
        return mSharedPreferences?.getString(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return mSharedPreferences?.getBoolean(key, defValue) ?: defValue
    }

    fun getFloat(key: String, defValue: Float): Float {
        return mSharedPreferences?.getFloat(key, defValue) ?: defValue
    }

    fun getInt(key: String, defValue: Int = 0): Int {
        return mSharedPreferences?.getInt(key, defValue) ?: defValue
    }

    fun getLong(key: String, defValue: Long): Long {
        return mSharedPreferences?.getLong(key, defValue) ?: defValue
    }

    fun getStringSet(key: String, defValue: Set<String>): Set<String>? {
        return if (mSharedPreferences == null) defValue else mSharedPreferences?.getStringSet(key, defValue)
    }

    fun remove(key: String) {
        if (mSharedPreferences == null) {
            return
        }
        mSharedPreferences?.edit()?.remove(key)?.apply()
    }

    fun getAll(): Map<String, *>? {
        return mSharedPreferences?.all
    }

    fun clear() {
        mSharedPreferences!!.edit().clear().apply()
    }

}