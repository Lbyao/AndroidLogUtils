package com.liubuyao.utils

import android.app.Application

/**
 * 工具类的中间类直接调用方法通过该类处理，适当的解耦Util直接的联系
 * ，如果后续更换，不用修改调用该功能的类
 *
 * @author lby
 */
internal object MyUtils {

    /**
     * 获取Application
     */
    fun getApp(): Application {
        return AppUtils.getApp()
    }

    /**
     * 获取sd卡是否可用
     */
    fun getSDCardCanUsed(): Boolean {
        return SDCardUtils.getSDCardCanUsed()
    }

    /**
     * 获取当前时间，用于carsh保存文件时用
     */
    fun getNowDateByCrash(): String {
        return DateUtils.getNowDateToString(DateUtils.FORMAT_YMD_HMS_SAVE)
    }

    /**
     * 获取异常信息
     */
    fun getThrowableInfoToString(throwable: Throwable): String {
        return ThrowableUtils.getThrowableInfoToString(throwable)
    }

    /**
     * 获取APP外部存储路径
     */
    fun getExternalAppPath(): String {
        return PathUtils.getExternalAppPath()
    }

    /**
     * 获取手机信息
     */
    fun getPhoneInfo(): String {
        val map = hashMapOf<String, String>()
        map["VersionCode"] = "" + AppUtils.getVersionCode()
        map["SDKVersionCode"] = AppUtils.getSDKVersionCode().toString()
        map["SDKVersionName"] = "" + AppUtils.getSDKVersionName()
        map["Manufacturer"] = "" + AppUtils.getManufacturer()
        map["Model"] = AppUtils.getModel()
        map["ABI"] = AppUtils.getABI()
        val str = StringBuilder("=".repeat(20) + "PHONE_INFO_HEAD" + "=".repeat(20) + "\n")
        for (info in map) {
            str.append(info.key).append(" ====> ").append(info.value).append("\n")
        }
        str.append("=".repeat(20) + "PHONE_INFO_HEAD_END" + "=".repeat(20) + "\n")
        return str.toString()
    }

    /**
     * 获取手机信息
     */
    fun getPhoneInfo(thread: Thread): String {
        val map = hashMapOf<String, String>()
        map["VersionCode"] = "" + AppUtils.getVersionCode()
        map["VersionName"] = "" + AppUtils.getVersionName()
        map["SDKVersionCode"] = AppUtils.getSDKVersionCode().toString()
        map["SDKVersionName"] = "" + AppUtils.getSDKVersionName()
        map["Manufacturer"] = "" + AppUtils.getManufacturer()
        map["Model"] = AppUtils.getModel()
        map["ABI"] = AppUtils.getABI()
        map["Thread"] = thread.name
        val str = StringBuilder("=".repeat(20) + "PHONE_INFO_HEAD" + "=".repeat(20) + "\n")
        for (info in map) {
            str.append(info.key).append(" ====> ").append(info.value).append("\n")
        }
        str.append("=".repeat(20) + "PHONE_INFO_HEAD_END" + "=".repeat(20) + "\n")
        return str.toString()
    }

    /**
     * 是否是空白字符串
     */
    fun isSpace(string: String?): Boolean {
        return StringUtils.isSpace(string)
    }

    /**
     * 写入文件
     */
    fun writeToFile(pathFile: String, content: String, append: Boolean) {
        FileUtils.writeToFile(pathFile, content, append)
    }

    /**
     * 是否获取到权限
     * @param permissions 要查询的多个权限
     *
     * @return 有一个没获取到权限就会返回false
     */
    fun isGrantedPermission(vararg permissions: String): Boolean {
        return PermissionsUtils.getIsGrantedPermission(*permissions)
    }

    /**
     * 是否获取到权限
     * @param permission 要查询的权限
     *
     * @return true 有 false 无
     */
    fun isGrantedPermission(permission: String): Boolean {
        return PermissionsUtils.getIsGrantedPermission(permission)
    }

    fun showToast(content: String) {
        ToastUtils.showToast(content)
    }
}