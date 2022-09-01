package com.liubuyao.utils

import android.os.Environment

/**
 * sd卡工具类
 *
 * @author lby
 */
object SDCardUtils {

    /**
     * 判断sd卡是否可用
     */
    fun getSDCardCanUsed():Boolean{
        return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
    }

}