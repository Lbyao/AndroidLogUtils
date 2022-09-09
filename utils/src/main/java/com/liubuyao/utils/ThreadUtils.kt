package com.liubuyao.utils

import android.os.Looper

/**
 * 线程工具类
 *
 * @author lby
 */
object ThreadUtils {

    /**
     * 通过传入的线程获取线程名
     *
     * @param thread 线程
     *
     * @return 线程名
     */
    fun getThreadName(thread: Thread): String {
        return thread.name
    }

    /**
     * 获取当前所处的线程名
     *
     * @return 线程名
     */
    fun getCurrentThreadName(): String {
        return Thread.currentThread().name
    }

    /**
     * 获取当前所处的线程组名
     *
     * @return 线程组名
     */
    fun getCurrentThreadGroupName(): String {
        return Thread.currentThread().threadGroup?.name ?: ""
    }

    /**
     * 是否是主线程
     *
     * @return 是否是主线程，true:是,false:否
     */
    fun isMainThread(): Boolean {
        return Looper.getMainLooper().thread == Thread.currentThread()
    }

}