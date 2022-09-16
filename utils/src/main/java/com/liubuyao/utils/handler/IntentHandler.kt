package com.liubuyao.utils.handler

import android.content.Intent
import com.liubuyao.utils.MyUtils

/**
 * intent解析
 *
 * @author lby
 */
class IntentHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Intent -> {
                onFormatListener.format(this::class.simpleName + "------>" + formatData(any))
                return true
            }
        }
        return false
    }

    private fun formatData(intent: Intent): String {
        return MyUtils.intentToString(intent)
    }
}