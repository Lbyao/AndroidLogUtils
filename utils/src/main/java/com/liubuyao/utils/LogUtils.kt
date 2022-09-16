package com.liubuyao.utils

import android.util.Log
import com.liubuyao.utils.handler.*

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
     * 日志打印开关
     */
    @Volatile
    private var isLogFormatSwitch = true

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
        handlers.add(BundleHandler())
        handlers.add(IntentHandler())
        handlers.add(MapHandler())
        handlers.add(ThrowableHandler())
        handlers.add(UriHandler())
        handlers.add(DefaultHandler())

        val len = handlers.size
        for (i in 0 until len) {
            if (i > 0) {
                handlers[i - 1].setNextHandler(handlers[i])
            }
        }
        startHandler = handlers[0]
    }

    fun addHandler(handler: BaseHandler) {
        handlers.add(handlers.size - 1, handler)
        d(handlers)
    }

    fun v(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun v(tag: String = mTag, vararg content: Any?) {
        print(V, tag, *content)
    }

    fun v(tag: String = mTag, content: Any?) {
        print(V, tag, content)
    }


    fun d(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun d(vararg content: Any?, tag: String = mTag) {
        print(V, tag, *content)
    }

    fun d(content: Any?, tag: String = mTag) {
        print(V, tag, content)
    }

    fun i(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun i(vararg content: Any?, tag: String = mTag) {
        print(V, tag, *content)
    }

    fun i(content: Any?, tag: String = mTag) {
        print(V, tag, content)
    }

    fun w(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun w(vararg content: Any?, tag: String = mTag) {
        print(V, tag, *content)
    }

    fun w(content: Any?, tag: String = mTag) {
        print(V, tag, content)
    }

    fun e(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun e(vararg content: Any?, tag: String = mTag) {
        print(V, tag, *content)
    }

    fun e(content: Any?, tag: String = mTag) {
        print(V, tag, content)
    }

    fun a(vararg content: Any?) {
        print(V, mTag, *content)
    }

    fun a(vararg content: Any?, tag: String = mTag) {
        print(V, tag, *content)
    }

    fun a(content: Any?, tag: String = mTag) {
        print(V, tag, content)
    }

    fun print(type: Int = D, tag: String, vararg content: Any?) {
        startHandler.handleObject(content, object : BaseHandler.OnFormatListener {
            override fun format(string: String) {
                if (isLogSwitch){
                    val stringBuilder = StringBuilder()
                    stringBuilder.append("=".repeat(20)).append("start").append("=".repeat(20))
                        .append("\n").append(string).append("\n").append("=".repeat(20)).append("end")
                        .append("=".repeat(20)).append("\n")
                    Log.println(type, tag, stringBuilder.toString())
                }
                if (isFileSwitch){

                }
            }
        })
    }

    fun print(type: Int = D, tag: String, content: Any?) {
        startHandler.handleObject(content, object : BaseHandler.OnFormatListener {
            override fun format(string: String) {
                if (isLogSwitch){
                    val stringBuilder = StringBuilder()
                    stringBuilder.append("=".repeat(20)).append("start").append("=".repeat(20))
                        .append("\n").append(string).append("\n").append("=".repeat(20)).append("end")
                        .append("=".repeat(20)).append("\n")
                    Log.println(type, tag, stringBuilder.toString())
                }
                if (isFileSwitch){

                }
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

    fun setLogFormatSwitch(open: Boolean): LogUtils {
        isLogFormatSwitch = open
        return this
    }

}