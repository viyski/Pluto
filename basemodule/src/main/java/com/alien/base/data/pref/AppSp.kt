package com.alien.base.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.alien.base.modulekit.BaseModuleKit

object AppSp {

    private var mSharedPreferences: SharedPreferences? = null
    private const val UUID = "uuid"

    init {
        mSharedPreferences = BaseModuleKit.getInstance().getApplication()?.getSharedPreferences("app", Context.MODE_PRIVATE)
    }

    fun uuid(): String {
        return getString(UUID, "").toString()
    }

    fun putUuid(uuid: String?) = put(UUID, uuid)

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

    fun getString(key: String, defValue: String): String? {
        return mSharedPreferences?.getString(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return mSharedPreferences?.getBoolean(key, defValue) ?: defValue
    }

    fun getFloat(key: String, defValue: Float): Float {
        return mSharedPreferences?.getFloat(key, defValue) ?: defValue
    }

    fun getInt(key: String, defValue: Int): Int {
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