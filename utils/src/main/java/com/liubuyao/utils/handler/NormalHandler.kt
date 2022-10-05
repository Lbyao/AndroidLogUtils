package com.liubuyao.utils.handler

import com.liubuyao.utils.MyUtils

/**
 * 基本数据格式
 *
 * 责任链模式
 */
class NormalHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is String, is Boolean, is Int, is Long, is Float, is Double, is Char, is Byte -> {
                onFormatListener.format(this::class.simpleName+"------>"+formatData(any))
                return true
            }
        }
        return false
    }

    private fun formatData(any: Any): String {
        return MyUtils.formatJson(any.toString())
    }
}