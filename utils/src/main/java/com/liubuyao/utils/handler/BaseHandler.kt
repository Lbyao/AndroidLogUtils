package com.liubuyao.utils.handler

abstract class BaseHandler {

    private var nextHandler: BaseHandler? = null

    fun handleObject(any: Any?, onFormatListener: OnFormatListener) {
        if (any == null) return
        //如果不能处理传递给下一个
        if (!handle(any, onFormatListener)) {
            if (nextHandler != null) {
                nextHandler?.handleObject(any, onFormatListener)
            }
        }
    }

    /**
     * 设置下一个节点
     */
    fun setNextHandler(handler: BaseHandler) {
        nextHandler = handler
    }

    /**
     * 处理解析数据
     */
    protected abstract fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean

    interface OnFormatListener {
        fun format(string: String)
    }
}