package com.alien.base.tools

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import com.alien.base.Constants

object AppUtil {

    @SuppressLint("MissingPermission", "HardwareIds")
    fun getImei(context: Context) : String {
        var imei = ""
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            imei = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                telephonyManager.imei
            }else{
                telephonyManager.deviceId
            }
            if (TextUtils.isEmpty(imei)){
                imei = getAndroidId(context)
            }
        } catch (e: Exception) {
        }
        return imei
    }

    fun getAndroidId(context: Context): String {
        var aId = ""
        try {
            aId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        } catch (e: Exception) {
        }

        return aId
    }

    fun getChannel(context: Context) : String {
        val applicationInfo: ApplicationInfo = context.packageManager?.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)!!
        return applicationInfo.metaData.getString("umeng_channel", "")
    }

    fun getVersionCode(context: Context) : Int {
        val packageInfo = context.packageManager?.getPackageInfo(context.packageName, 0)
        return packageInfo?.versionCode!!
    }

    fun hasMore(list: MutableList<*>?) = list != null && list.size >= Constants.PAGE_MAX_SIZE

    fun statusBarHeight(): Int{
        return Resources.getSystem()
                .getDimensionPixelSize(Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
    }
}