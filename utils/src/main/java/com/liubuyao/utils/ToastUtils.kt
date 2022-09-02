package com.liubuyao.utils

import android.widget.Toast

/**
 * Toast工具类
 *
 * @author lby
 */
object ToastUtils {

    fun showToast(content: String?, time: Int = Toast.LENGTH_SHORT) {
        if (content == null) return
        if (MyUtils.isSpace(content)) return
        Toast.makeText(MyUtils.getApp(), content, time).show()
    }

}