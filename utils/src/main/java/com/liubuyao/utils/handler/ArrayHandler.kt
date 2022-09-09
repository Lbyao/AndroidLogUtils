package com.liubuyao.utils.handler

/**
 * 解析数组
 *
 * @author lby
 */
class ArrayHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Array<*> -> {
                onFormatListener.format(this::class.simpleName+"------>"+formatData(any))
                return true
            }
        }
        return false
    }

    private fun formatData(any: Array<*>): String {
        return any.contentToString()
    }
}