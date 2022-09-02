package com.liubuyao.utils

/**
 * crash日志保存工具类，自动初始化
 */
object CrashUtils {

    private val DEFAULT = Thread.getDefaultUncaughtExceptionHandler()
    private var isOpen = true

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
        val realPath = if (MyUtils.isSpace(path)) {
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
            val isGranted = MyUtils.isGrantedPermission(Constants.WRITE_EXTERNAL_STORAGE)
            if (isGranted && isOpen) {
                val crashDateTime = MyUtils.getNowDateByCrash()
                val throwableInfoToString = MyUtils.getThrowableInfoToString(throwable)
                val phoneInfo = MyUtils.getPhoneInfo(thread)
                MyUtils.writeToFile(
                    "${path}crash_log_${crashDateTime}.txt",
                    phoneInfo + throwableInfoToString,
                    false
                )
            } else {
                if (isOpen) {
                    MyUtils.showToast("无存储权限，异常未写入文件！!")
                } else {
                    MyUtils.showToast("关闭写入文件！!")
                }
            }
            onCrashListener?.onCrash(thread, throwable)
            //需要用默认的接收器处理，否则会卡死一段时间
            DEFAULT?.uncaughtException(thread, throwable)
        }
    }

    /**
     * 设置是否保存到文件
     */
    fun openCrashSaveFile(open: Boolean) {
        isOpen = open
    }

    interface OnCrashListener {
        fun onCrash(thread: Thread, throwable: Throwable)
    }

}