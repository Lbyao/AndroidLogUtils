package com.liubuyao.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * json工具类
 *
 * @author lby
 */
object JsonUtils {

    /**
     * 格式化字符串为json样式
     */
    fun formatJson(json: String?): String {
        return formatJson(json, 4)
    }

    /**
     * 格式化字符串为json样式
     */
    fun formatJson(json: String?, indentSpaces: Int): String {
        if (json == null) return ""
        return try {
            if (json.startsWith("{")) {
                JSONObject(json).toString(indentSpaces)
            } else if (json.startsWith("[")) {
                JSONArray(json).toString(indentSpaces)
            } else {
                json
            }
        } catch (e: JSONException) {
            ""
        }

    }

    fun toJsonString(collection: Collection<*>): String {
        return toJsonString(collection, 4)
    }

    fun toJsonString(collection: Collection<*>, indentSpaces: Int): String {
        return JSONArray(collection).toString()
    }

}