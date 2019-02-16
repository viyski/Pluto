package com.alien.base.tools.permission

interface PermissionListener {

    fun onGranted(permissionName: String?)

    fun onDenied(permissionName: String?)

    fun onDeniedWithNeverAsk(permissionName: String?)
}