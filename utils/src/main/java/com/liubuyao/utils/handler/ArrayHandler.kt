package com.liubuyao.utils.handler

class ArrayHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Array<*> -> {
                onFormatListener.format(formatData(any))
                return true
            }
        }
        return false
    }

    private fun formatData(any: Array<*>): String {
        return any.contentToString()
    }
}