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
//        return MyUtils.bundleToJsonString(bundle, isFormat)
        return bundle2String(bundle)?:""
    }

    private fun bundle2String(bundle: Bundle): String? {
        val iterator: Iterator<String> = bundle.keySet().iterator()
        if (!iterator.hasNext()) {
            return "Bundle {}"
        }
        val sb = StringBuilder(128)
        sb.append("Bundle { ")
        while (true) {
            val key = iterator.next()
            val value = bundle[key]
            sb.append(key).append('=')
            if (value is Bundle) {
                sb.append(if (value === bundle) "(this Bundle)" else bundle2String(value))
            } else {
                sb.append(MyUtils.anyToJsonString(value?:Any()))
            }
            if (!iterator.hasNext()) return sb.append(" }").toString()
            sb.append(',').append(' ')
        }
    }
}