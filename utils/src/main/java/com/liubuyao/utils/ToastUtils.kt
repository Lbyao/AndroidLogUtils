package com.liubuyao.utils

import android.os.Looper
import android.util.Log
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
        if (ThreadUtils.isMainThread()) {
            Log.d("ToastUtils",ThreadUtils.getCurrentThreadGroupName())
            Toast.makeText(MyUtils.getApp(), content, time).show()
        } else {

//            Toast.makeText(MyUtils.getApp(), content, time).show()
        }
    }

}