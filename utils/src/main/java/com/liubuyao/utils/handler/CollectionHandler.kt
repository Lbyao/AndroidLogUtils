package com.liubuyao.utils.handler

import com.liubuyao.utils.JsonUtils

/**
 * 解析集合
 *
 * @author lby
 */
class CollectionHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Collection<*> -> {
                onFormatListener.format(this::class.simpleName+"------>"+formatData(any))
                return true
            }
        }
        return false
    }

    fun formatData(any: Collection<*>): String {
        return JsonUtils.toJsonString(any)
    }
}