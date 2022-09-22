package com.liubuyao.utils

import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import okio.IOException
import java.util.*

object StringUtils {

    fun isSpace(string: String?): Boolean {
        if (string == null) {
            return true
        }
        if (string.trim().isEmpty()) {
            return true
        }
        for (s in string) {
            if (!s.isWhitespace()) {
                return false
            }
        }
        return true
    }

    /**
     * 根据指定长度截取字节数组为字符串
     */
    fun subStr(bytes: ByteArray?, subLength: Int): String {
        if (bytes == null || subLength < 1) {
            return ""
        }
        // 超出范围直接返回
        if (subLength >= bytes.size) {
            return String(bytes)
        }

        // 复制出指定长字节数组，转为字符串
        val subStr = String(Arrays.copyOf(bytes, subLength))

        // 避免末尾字符是被拆分的，这里减1使字符串保持完整
        return subStr.substring(0, subStr.length - 1)
    }

    fun getDateByFileName(fileName: String?): String? {
        if (fileName == null) return null
        //YYYY-MM-DD.*
        if (fileName.length < 12) return null
        val indexOf = fileName.indexOf(".")
        if (indexOf < 10) return null
        return fileName.substring(indexOf - 10, indexOf)
    }

    /**
     * 获取request内容
     */
    fun getRequestToString(request: Request?): Map<String, String> {
        if (request == null) {
            return emptyMap()
        }
        val map = mutableMapOf<String, String>()
        map["URL"] = request.url.toString()
        map["Method"] = request.method
        map["Headers"] = request.headers.toString()
        val bodyStr = try {
            val requestBody = request.body ?: return emptyMap()
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            buffer.readString(Charsets.UTF_8)
        } catch (e: IOException) {
            MyUtils.getThrowableInfoToString(e)
        }
        map["Body"] = bodyStr
        return map
    }

    /**
     * 获取response内容
     */
    fun getResponseToString(response: Response?): Map<String, String> {
        if (response == null) {
            return emptyMap()
        }
        val map = mutableMapOf<String, String>()
        map["Headers"] =response.headers.toString()
        map["Code"] =response.code.toString()
        map["isSuccessful"] =response.isSuccessful.toString()
        map["Message"] = response.message
        map["Url"] = response.request.url.toString()
        map["EncodedPathSegments"] = response.request.url.encodedPathSegments.toString()
        var gzippedLength: Long? = null

        val bodyStr = try {
            val responseBody = response.body ?: return emptyMap()
            val contentLength = responseBody.contentLength()
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            source.buffer.clone().readString(Charsets.UTF_8)
        } catch (e: IOException) {
            MyUtils.getThrowableInfoToString(e)
        }
        map["Body"] = bodyStr
        return map
    }
}