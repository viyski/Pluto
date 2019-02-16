package com.alien.base.tools

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

@SuppressLint("MissingPermission")
object NetWorkUtils {


    fun isWifiConnected(context: Context?): Boolean {
        if (context == null) return false
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val active = manager.activeNetworkInfo
        return active != null && active.type == ConnectivityManager.TYPE_WIFI
    }

    fun isNetConnected(context: Context?): Boolean {
        if (context == null) return false
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return info != null && info.isConnected && info.state == NetworkInfo.State.CONNECTED
    }

    fun isNetAvailable(context: Context): Boolean {
        val networkInfo = getActiveNetworkInfo(context)
        return networkInfo?.isAvailable ?: false
    }


    private fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo
        } catch (e: Exception) {
            return null
        }

    }
}