package com.liubuyao.utils.handler

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

    fun formatData(any: Any): String {
        return any.toString()
    }
}