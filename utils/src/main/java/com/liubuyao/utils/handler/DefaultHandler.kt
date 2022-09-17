package com.liubuyao.utils.handler

import com.liubuyao.utils.MyUtils

/**
 * 所有分支的结尾
 *
 * @author lby
 */
class DefaultHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        if (any == null) return false
        onFormatListener.format(this::class.simpleName + "------>" + formatData(any))
        return true
    }

    private fun formatData(any: Any, isFormat: Boolean = true): String {
        return MyUtils.anyToJsonString(any, isFormat)
    }
}