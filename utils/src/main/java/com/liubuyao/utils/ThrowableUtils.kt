package com.liubuyao.utils

import java.io.PrintWriter
import java.io.StringWriter

/**
 * 异常解析处理工具类
 *
 * @author lby
 */
object ThrowableUtils {

    /**
     * 获取异常信息
     */
    fun getThrowableInfoToString(throwable: Throwable?): String {
        return getExceptionInfo(throwable)
    }

    /**
     * 获取异常信息
     */
    private fun getExceptionInfo(throwable: Throwable?):String{
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        throwable?.printStackTrace(pw)
        return sw.toString()
    }
}