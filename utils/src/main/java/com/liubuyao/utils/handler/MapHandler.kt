package com.liubuyao.utils.handler

import com.liubuyao.utils.MyUtils

/**
 * 解析map
 *
 * @author lby
 */
class MapHandler:BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Map<*,*> -> {
                onFormatListener.format(this::class.simpleName + "------>" + formatData(any))
                return true
            }
        }
        return false
    }

    private fun formatData(map: Map<*,*>): String {
        return MyUtils.mapToJsonString(map)
    }
}