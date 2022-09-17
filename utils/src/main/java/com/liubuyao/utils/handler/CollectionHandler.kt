package com.liubuyao.utils.handler

import com.liubuyao.utils.MyUtils

/**
 * 解析集合
 *
 * @author lby
 */
class CollectionHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Collection<*> -> {
                onFormatListener.format(this::class.simpleName + "------>" + formatData(any))
                return true
            }
        }
        return false
    }

    private fun formatData(any: Collection<*>, isFormat: Boolean = true): String {
        return MyUtils.collectionToJsonString(any, isFormat)
    }
}