package com.liubuyao.utils

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import okhttp3.Request
import okhttp3.Response
import java.util.*

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
     * 获取当前时间，用于carsh保存文件时用
     */
    fun getNowDate(format: String): String {
        return DateUtils.getNowDateToString(format)
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
        map["Thread"] = ThreadUtils.getCurrentThreadName()
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
        map["Thread"] = ThreadUtils.getThreadName(thread)
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
     * 文件是否存在
     */
    fun fileExists(path: String): Boolean {
        return FileUtils.fileExists(path)
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

    /**
     * bundle 打印成string
     */
    fun bundleToJsonString(bundle: Bundle, isFormat: Boolean = true): String {
        return JsonUtils.bundleToJsonString(bundle, isFormat)
    }

    /**
     * bundle 打印成string
     */
    fun mapToJsonString(map: Map<*, *>, isFormat: Boolean = true): String {
        return JsonUtils.mapToString(map, isFormat)
    }

    /**
     * intent 打印成string
     */
    fun intentToString(intent: Intent, isFormat: Boolean = true): String {
        return JsonUtils.intentToString(intent, isFormat)
    }

    /**
     * uri 打印成string
     */
    fun uriToString(uri: Uri, isFormat: Boolean = true): String {
        return JsonUtils.uriToString(uri, isFormat)
    }

    /**
     * collection 打印成string
     */
    fun collectionToJsonString(collection: Collection<*>, isFormat: Boolean = true): String {
        return JsonUtils.collectionToJsonString(collection, isFormat)
    }

    /**
     * array 打印成string
     */
    fun arrayToJsonString(array: Array<*>, isFormat: Boolean = true): String {
        return JsonUtils.arrayToJsonString(array, isFormat)
    }

    /**
     * any 打印成string
     */
    fun anyToJsonString(any: Any, isFormat: Boolean = true): String {
        return JsonUtils.anyToString(any, isFormat)
    }

    /**
     * 截取字符串
     */
    fun subStr(bytes: ByteArray?, subLength: Int): String {
        return StringUtils.subStr(bytes, subLength)
    }

    /**
     * 删除文件
     */
    fun deleteFile(path: String): Boolean {
        return FileUtils.deleteFile(path)
    }

    /**
     * 获取某个文件同级的过滤后的文件列表
     */
    fun getFileList(path: String?, filterFilename: String, suffix: String): Array<String>? {
        return FileUtils.getFileListByFile(path, filterFilename, suffix)
    }

    /**
     * 从日志文件名获取日期
     */
    fun getDateByFileName(path: String?): String? {
        return StringUtils.getDateByFileName(path)
    }

    fun getDataByStr(dateStr: String?, format: String = DateUtils.FORMAT_YMD_upload): Date? {
        return DateUtils.getDataByStr(dateStr, format)
    }

    fun getNowDateTime(date: String): Long? {
        return getDataByStr(date)?.time
    }

    fun getRequestToString(request: Request?): Map<String, String> {
        return StringUtils.getRequestToString(request)
    }

    fun getResponseToString(response: Response?): Map<String, String> {
        return StringUtils.getResponseToString(response)
    }

    fun formatJson(json: String?, indentSpaces: Int = 4): String {
        return JsonUtils.formatJson(json, indentSpaces)
    }
}