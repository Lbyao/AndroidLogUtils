package com.liubuyao.utils

/**
 * crash日志保存工具类，自动初始化
 */
object CrashUtils {

    /**
     * 初始化
     */
    fun init(path: String) {
        init(path, null)
    }

    /**
     * 初始化
     */
    fun init(path: String, onCrashListener: OnCrashListener?) {
        val realPath = if (path.isNotEmpty()) {
            ""
        } else if (path.endsWith(Constants.separtor)) {
            path
        } else {
            path + Constants.separtor
        }
        Thread.setDefaultUncaughtExceptionHandler(
            getUncaughtExceptionHandler(
                realPath,
                onCrashListener
            )
        )
    }


    /**
     * 获取到异常崩溃接收handle
     */
    private fun getUncaughtExceptionHandler(
        path: String,
        onCrashListener: OnCrashListener?
    ): Thread.UncaughtExceptionHandler {
        return Thread.UncaughtExceptionHandler { thread, throwable ->
            val crashDateTime = MyUtils.getNowDateByCrash()
            val throwableInfoToString = MyUtils.getThrowableInfoToString(throwable)
            val phoneInfo = MyUtils.getPhoneInfo()
            MyUtils.writeToFile("$path$crashDateTime.txt",phoneInfo+throwableInfoToString,false)
            onCrashListener?.onCrash(thread, throwable)
        }
    }

    interface OnCrashListener {
        fun onCrash(thread: Thread, throwable: Throwable)
    }

}