package com.liubuyao.utils

import android.util.Log
import com.liubuyao.utils.handler.ArrayHandler
import com.liubuyao.utils.handler.BaseHandler
import com.liubuyao.utils.handler.CollectionHandler
import com.liubuyao.utils.handler.NormalHandler

/**
 * 日志工具类
 *
 * @author lby
 */
object LogUtils {

    private val handlers = mutableListOf<BaseHandler>()
    private lateinit var startHandler: BaseHandler

    const val V = Log.VERBOSE
    const val D = Log.DEBUG
    const val I = Log.INFO
    const val W = Log.WARN
    const val E = Log.ERROR
    const val A = Log.ASSERT

    /**
     * 日志打印开关
     */
    @Volatile
    private var isLogSwitch = true

    /**
     * 保存文件开关
     */
    @Volatile
    private var isFileSwitch = true

    /**
     * 日志打印tag
     */
    @Volatile
    private var mTag = "AndroidLogUtil"

    /**
     * 是否格式化控制台打印
     */
    @Volatile
    private var formatPrint = true

    /**
     * 最大行数
     */
    private var MAX_LINE = 500

    fun init() {
        handlers.add(NormalHandler())
        handlers.add(ArrayHandler())
        handlers.add(CollectionHandler())

        val len = handlers.size
        for (i in 0 until len) {
            if (i > 0) {
                handlers[i - 1].setNextHandler(handlers[i])
            }
        }
        startHandler = handlers[0]
    }

    fun addHandler(handler: BaseHandler){
        handlers.add(handler)
    }

    fun v(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun v(tag: String?, vararg content: Any?) {
        print(V, tag, *content)
    }

    fun d(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun d(tag: String?, vararg content: Any?) {
        print(V, tag, *content)
    }

    fun i(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun i(tag: String?, vararg content: Any?) {
        print(V, tag, *content)
    }

    fun w(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun w(tag: String?, vararg content: Any?) {
        print(V, tag, *content)
    }

    fun e(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun e(tag: String?, vararg content: Any?) {
        print(V, tag, *content)
    }

    fun a(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun a(tag: String?, vararg content: Any?) {
        print(V, tag, *content)
    }

    fun print(type: Int = D, tag: String?, vararg content: Any?) {
        startHandler.handleObject(content, object : BaseHandler.OnFormatListener {
            override fun format(string: String) {
                Log.println(type, tag, string)
            }
        })
    }

    fun setLogTag(tag: String?): LogUtils {
        if (StringUtils.isSpace(tag)) return this
        mTag = tag!!
        return this
    }

    fun setLogSwitch(open: Boolean): LogUtils {
        isLogSwitch = open
        return this
    }

    fun setFileSwitch(open: Boolean): LogUtils {
        isFileSwitch = open
        return this
    }

}