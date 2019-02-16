package com.alien.base.tools.permission

import android.annotation.SuppressLint
import android.support.v4.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions

class PermissionManager {

    @SuppressLint("CheckResult")
    fun requestEach(activity: FragmentActivity?, listener: PermissionListener?, permissions: String) {
        if (activity != null) {
            val rxPermissions = RxPermissions(activity)
            rxPermissions.requestEach(permissions).subscribe { permission ->
                if (permission.granted) {
                    listener?.onGranted(permission.name)
                } else if (permission.shouldShowRequestPermissionRationale) {
                    listener?.onDenied(permission.name)
                } else {
                    listener?.onDeniedWithNeverAsk(permission.name)
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    fun requestEachCombined(activity: FragmentActivity?, listener: PermissionListener?, vararg permissions: String) {
        if (activity != null) {
            val rxPermissions = RxPermissions(activity)
            rxPermissions.requestEachCombined(*permissions).subscribe { permission ->
                if (permission.granted) {
                    listener?.onGranted(permission.name)
                } else if (permission.shouldShowRequestPermissionRationale) {
                    listener?.onDenied(permission.name)
                } else {
                    listener?.onDeniedWithNeverAsk(permission.name)
                }
            }
        }
    }

}