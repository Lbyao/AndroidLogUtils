package com.liubuyao.utils.handler

/**
 * 基本数据格式
 *
 * 责任链模式
 */
class NormalHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is String, is Boolean, is Int, is Long, is Float, is Double, is Char, is Byte -> {
                onFormatListener.format(formatData(any))
                return true
            }
        }
        return false
    }

    fun formatData(any: Any): String {
        return any.toString()
    }
}