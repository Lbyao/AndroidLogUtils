package com.liubuyao.utils.handler

import com.liubuyao.utils.JsonUtils

class CollectionHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Collection<*> -> {
                onFormatListener.format(formatData(any))
                return true
            }
        }
        return false
    }

    fun formatData(any: Collection<*>): String {
        return JsonUtils.toJsonString(any)
    }
}