package com.liubuyao.utils

import android.util.Log
import com.liubuyao.utils.handler.*
import java.util.*

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
    private var isFileSwitch = false

    /**
     * 保存文件的日志等级
     */
    @Volatile
    private var saveFileLevel = Log.DEBUG

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
     * 字符串最大字节数量，超出的截取出来
     */
    private var MAX_LENGTH = 4000

    /**
     * 日志本地存储路径
     */
    @Volatile
    private var mLogFilePath = MyUtils.getExternalAppPath()

    /**
     * 日志保存本地的名称
     */
    @Volatile
    private var mLogFileName = "log_file"

    /**
     * 日志保存本地的后缀
     */
    @Volatile
    private var mLogFileSuffix = "log"

    /**
     * 日志保存本地的时间
     */
    @Volatile
    private var mLogFileDays = 1

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
        print(D, mTag, *content)
    }

    fun d(vararg content: Any?, tag: String = mTag) {
        print(D, tag, *content)
    }

    fun d(content: Any?, tag: String = mTag) {
        print(D, tag, content)
    }

    fun i(vararg content: Any?) {
        print(I, mTag, *content)
    }

    fun i(vararg content: Any?, tag: String = mTag) {
        print(I, tag, *content)
    }

    fun i(content: Any?, tag: String = mTag) {
        print(I, tag, content)
    }

    fun w(vararg content: Any?) {
        print(W, mTag, *content)
    }

    fun w(vararg content: Any?, tag: String = mTag) {
        print(W, tag, *content)
    }

    fun w(content: Any?, tag: String = mTag) {
        print(W, tag, content)
    }

    fun e(vararg content: Any?) {
        print(E, mTag, *content)
    }

    fun e(vararg content: Any?, tag: String = mTag) {
        print(E, tag, *content)
    }

    fun e(content: Any?, tag: String = mTag) {
        print(E, tag, content)
    }

    fun a(vararg content: Any?) {
        print(A, mTag, *content)
    }

    fun a(vararg content: Any?, tag: String = mTag) {
        print(A, tag, *content)
    }

    fun a(content: Any?, tag: String = mTag) {
        print(A, tag, content)
    }

    fun print(type: Int = D, tag: String, vararg content: Any?) {
        startHandler.handleObject(content, object : BaseHandler.OnFormatListener {
            override fun format(string: String) {
                printLog(string, type, tag)
            }
        })
    }

    fun print(type: Int = D, tag: String, content: Any?) {
        startHandler.handleObject(content, object : BaseHandler.OnFormatListener {
            override fun format(string: String) {
                printLog(string, type, tag)
            }
        })
    }

    @Synchronized
    private fun printLog(string: String, type: Int = D, tag: String) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("=".repeat(20)).append("start").append("=".repeat(20)).append("\n")
            .append(MyUtils.getNowDate(DateUtils.FORMAT_YMD_HMS_SAVE)).append("\n")
        getClassInfo(stringBuilder)
        stringBuilder.append(string).append("\n").append("=".repeat(20))
            .append("end")
            .append("=".repeat(20)).append("\n")
        if (isLogSwitch) {
            if (stringBuilder.length <= 1000) {
                Log.println(type, tag, stringBuilder.toString())
            } else {
                val encodeToByteArray = stringBuilder.toString().encodeToByteArray()
                if (encodeToByteArray.size <= MAX_LENGTH) {
                    Log.println(type, tag, stringBuilder.toString())
                } else {
                    //较长的字符串，先用换行符分隔打印
                    //为啥用换行符呢，因为换行符本来就换行打印，不会影响一致性
                    val split = stringBuilder.split("\n")
                    for (str in split) {
                        var strByteArray = str.encodeToByteArray()
                        //判断字节数是否超出
                        if (strByteArray.size <= MAX_LENGTH) {
                            Log.println(type, tag, str)
                        } else {
                            //如果超出，截取字符串
                            while (MAX_LENGTH < strByteArray.size) {
                                val subStr = MyUtils.subStr(strByteArray, MAX_LENGTH)
                                Log.println(type, tag, subStr)
                                strByteArray = strByteArray.copyOfRange(
                                    subStr.encodeToByteArray().size,
                                    strByteArray.size
                                )
                            }
                            //把截取后剩余的字符串打印
                            Log.println(type, tag, String(strByteArray))
                        }
                    }
                }
            }
        }
        if (isFileSwitch) {
            if (type >= saveFileLevel) {
                val filePath =
                    mLogFilePath + Constants.separtor + mLogFileName + "_" + MyUtils.getNowDate(
                        DateUtils.FORMAT_YMD_upload
                    ) + "." + mLogFileSuffix
                deleteLog(mLogFilePath, MyUtils.getNowDate(DateUtils.FORMAT_YMD_upload))
                MyUtils.writeToFile(
                    filePath,
                    if (MyUtils.fileExists(filePath)) stringBuilder.toString() else MyUtils.getPhoneInfo() + "\n" + stringBuilder.toString(),
                    true
                )
            }
        }
    }

    /**
     * 删除日志
     */
    fun deleteLog(path: String, date: String) {
        val fileList = MyUtils.getFileList(
            if (path.endsWith(Constants.separtor)) path else (path + Constants.separtor),
            mLogFileName,
            mLogFileSuffix
        )
        if (fileList != null) {
            for (file in fileList) {
                val dateByFileName = MyUtils.getDateByFileName(file) ?: continue
                val dataByStr = MyUtils.getDataByStr(dateByFileName) ?: continue
                val nowDateTime =
                    MyUtils.getNowDateTime(date)
                        ?: continue
                val deleteTime = nowDateTime - mLogFileDays * 86400000L
                if (dataByStr.time <= deleteTime) {
                    MyUtils.deleteFile(mLogFilePath + Constants.separtor + file)
                }
            }
        }
    }

    private fun getClassInfo(stringBuilder: StringBuilder) {
        val stackTraces = Thread.currentThread().stackTrace
        var index = 0
        for (stack in stackTraces) {
            if (!stack.className.contains("com.liubuyao.utils")) {
                if (index==2){
                    val head: String = Formatter()
                        .format(
                            "%s, %s.%s(%s:%d)",
                            Thread.currentThread().name,
                            stack.className,
                            stack.methodName,
                            stack.fileName,
                            stack.lineNumber
                        )
                        .toString()
                    stringBuilder.append("[").append(head).append("]").append("\n")
                }
                index++
            }
        }
    }

    /**
     * 设置日志的tag
     */
    fun setLogTag(tag: String?): LogUtils {
        if (StringUtils.isSpace(tag)) return this
        mTag = tag!!
        return this
    }

    /**
     * 设置日志是否打印到控制台
     */
    fun setLogSwitch(open: Boolean): LogUtils {
        isLogSwitch = open
        return this
    }

    /**
     * 设置日志是否保存到文件
     */
    fun setFileSwitch(open: Boolean): LogUtils {
        isFileSwitch = open
        return this
    }

    /**
     * 设置日志是否保存到的等级
     *  例如：LogUtils.D
     */
    fun setSaveFileLevel(level: Int): LogUtils {
        when (level) {
            V, D, I, W, E, A -> {
                saveFileLevel = level
            }
            else -> {
                return this
            }
        }
        return this
    }

    /**
     * 设置日志是否格式化
     */
    fun setLogFormatSwitch(open: Boolean): LogUtils {
        isLogFormatSwitch = open
        return this
    }

    /**
     * 设置日志文件保存的路径
     */
    fun setLogFilePath(path: String): LogUtils {
        if (MyUtils.isSpace(path)) return this
        mLogFilePath = path
        return this
    }

    /**
     * 设置保存文件的名称
     */
    fun setLogFileName(name: String): LogUtils {
        if (MyUtils.isSpace(name)) return this
        mLogFileName = name
        return this
    }

    /**
     * 设置保存文件的后缀名如：log
     */
    fun setLogFileSuffix(suffix: String): LogUtils {
        if (MyUtils.isSpace(suffix)) return this
        mLogFileSuffix = suffix
        return this
    }

}