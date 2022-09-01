package com.liubuyao.utils

import android.app.Application
import android.content.Context
import android.os.Build

/**
 * 用于初始化context全局调用使用
 *
 * @author lby
 */
object AppUtils {

    @Volatile
    var application: Application? = null

    fun init(context: Context?) {
        if (context == null) {
            throw NullPointerException("未初始化Library")
        }
        application = context.applicationContext as Application?
    }

    fun getApp(): Application {
        if (application == null) {
            throw NullPointerException("未初始化Library")
        }
        return application!!
    }

    fun getPackageName(): String {
        return getApp().packageName
    }

    fun getVersionName(): String {
        return getVersionName(getPackageName())
    }

    fun getVersionName(packageName: String?): String {
        if (packageName.isNullOrEmpty()) {
            return ""
        }
        val pm = getApp().packageManager
        val packageInfo = pm.getPackageInfo(packageName, 0)
        return if (packageInfo == null) "" else packageInfo.packageName
    }

    fun getVersionCode(): Int {
        return getVersionCode(getPackageName())
    }

    fun getVersionCode(packageName: String?): Int {
        if (packageName.isNullOrEmpty()) {
            return -1
        }
        val pm = getApp().packageManager
        val packageInfo = pm.getPackageInfo(packageName, 0)
        return packageInfo?.versionCode ?: -1
    }

    fun getSDKVersionName(): String {
        return Build.VERSION.RELEASE
    }

    fun getSDKVersionCode(): Int {
        return Build.VERSION.SDK_INT
    }

    /**
     * 制造商
     */
    fun getManufacturer(): String {
        return Build.MANUFACTURER
    }

    /**
     * 手机厂商
     */
    fun getBrand(): String {
        return Build.BRAND
    }

    /**
     * 型号
     */
    fun getModel(): String {
        return Build.MODEL
    }

    /**
     * 获取ABI
     */
    fun getABI(): String {
        return Build.CPU_ABI
    }

}