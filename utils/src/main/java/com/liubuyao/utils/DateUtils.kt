package com.liubuyao.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * 日期工具类
 *
 * @author lby
 */
object DateUtils {
    /**
     * 保存用，中间没有空格
     */
    const val FORMAT_YMD_HMS_SAVE = "yyyy_MM_dd-HH_mm_ss"

    /**
     * 正常格式的日期格式用年月日用-分隔，时分秒用：分隔
     */
    const val FORMAT_YMD_HMS_upload = "yyyy-MM-dd HH:mm:ss"

    /**
     * 正常格式的日期格式用年月日用-分隔
     */
    const val FORMAT_YMD_upload = "yyyy-MM-dd"

    /**
     * 正常格式的日期格式用年月日用/分隔，时分秒用：分隔
     */
    const val FORMAT_YMD_HMS_upload2 = "yyyy/MM/dd HH:mm:ss"

    /**
     * 正常格式的日期格式用年月日用/分隔
     */
    const val FORMAT_YMD_upload2 = "yyyy/MM/dd"

    /**
     * @return string
     */
    fun getNowDateToString(): String {
        return formatDate(Date(), FORMAT_YMD_HMS_upload)
    }

    /**
     * @param format 格式化的样式
     * @return string
     */
    fun getNowDateToString(format: String): String {
        return formatDate(Date(), format)
    }

    /**
     * @return Date
     */
    fun getNowDate() = Date()

    /**
     * 格式化日期
     * @return string
     */
    fun formatDate(date: Date, format: String): String {
        return SimpleDateFormat(format, Locale.getDefault()).format(date)
    }

}