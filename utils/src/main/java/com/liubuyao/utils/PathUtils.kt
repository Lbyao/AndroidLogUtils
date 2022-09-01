package com.liubuyao.utils

import android.os.Environment
import java.io.File

/**
 * Android路径工具类
 *
 * @author lby
 */
object PathUtils {



    /**
     * 获取外部存储路径 --> /storage/emulated/0
     */
    fun getExternalStoragePath(): String {
        return if (!MyUtils.getSDCardCanUsed()) {
            ""
        } else {
            getAbsolutePathByFile(Environment.getExternalStorageDirectory())
        }
    }

    /**
     * 获取APP外部存储路径
     */
    fun getExternalAppPath(): String {
        return if (!MyUtils.getSDCardCanUsed()) {
            return ""
        } else {
            val externalCacheDir = AppUtils.getApp().externalCacheDir ?: return ""
            getAbsolutePathByFile(externalCacheDir)
        }
    }


    /**
     * 通过文件获取绝对路径
     */
    fun getAbsolutePathByFile(file: File?): String {
        return if (file == null) {
            ""
        } else {
            file.absolutePath
        }
    }
}