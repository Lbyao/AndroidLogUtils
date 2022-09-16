package com.liubuyao.utils.handler

import android.net.Uri
import com.liubuyao.utils.MyUtils

/**
 * uri 解析
 *
 * @author lby
 */
class UriHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Uri -> {
                onFormatListener.format(this::class.simpleName + "------>" + formatData(any))
                return true
            }
        }
        return false
    }

    private fun formatData(uri: Uri, isFormat: Boolean = true): String {
        return MyUtils.uriToString(uri)
    }
}