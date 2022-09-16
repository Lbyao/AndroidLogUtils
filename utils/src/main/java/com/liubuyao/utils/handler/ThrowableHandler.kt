package com.liubuyao.utils.handler

import com.liubuyao.utils.MyUtils

/**
 * 解析异常
 *
 * @author lby
 */
class ThrowableHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Throwable -> {
                onFormatListener.format(this::class.simpleName + "------>" + formatData(any))
                return true
            }
        }
        return false
    }

    private fun formatData(throwable: Throwable): String {
        return MyUtils.getThrowableInfoToString(throwable)
    }
}