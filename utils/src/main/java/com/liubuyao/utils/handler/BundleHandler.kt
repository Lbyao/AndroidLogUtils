package com.liubuyao.utils.handler

import android.os.Bundle
import com.liubuyao.utils.MyUtils

/**
 * 解析bundle
 *
 * @author lby
 */
class BundleHandler : BaseHandler() {
    override fun handle(any: Any?, onFormatListener: OnFormatListener): Boolean {
        when (any) {
            is Bundle -> {
                onFormatListener.format(this::class.simpleName+"------>"+formatData(any))
                return true
            }
        }
        return false
    }

    private fun formatData(bundle: Bundle, isFormat: Boolean = true): String {
        return MyUtils.bundleToJsonString(bundle, isFormat)
    }
}