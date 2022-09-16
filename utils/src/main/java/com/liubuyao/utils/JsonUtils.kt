package com.liubuyao.utils

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * json工具类
 *
 * @author lby
 */
object JsonUtils {

    private val mGson = Gson()

    /**
     * 格式化字符串为json样式
     */
    fun formatJson(json: String?, indentSpaces: Int = 4): String {
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

    fun anyToString(any: Any?, isFormat: Boolean = true): String {
        if (any == null) return ""
        return if (isFormat) formatJson(mGson.toJson(any)) else mGson.toJson(any)
    }

    /**
     * 集合转json字符串
     */
    fun collectionToJsonString(collection: Collection<*>?, isFormat: Boolean = true): String {
        if (collection == null) return ""
        return if (isFormat) formatJson(mGson.toJson(collection)) else mGson.toJson(collection)
    }

    /**
     * 集合转json字符串
     */
    fun arrayToJsonString(array: Array<*>?, isFormat: Boolean = true): String {
        if (array == null) return ""
        return if (isFormat) formatJson(mGson.toJson(array)) else mGson.toJson(array)
    }

    /**
     * bundle转json字符串
     */
    fun bundleToJsonString(bundle: Bundle?, isFormat: Boolean = true): String {
        if (bundle == null) return ""
        val jsonObject = JSONObject()
        bundle.keySet().map { key ->
            bundle[key]?.also { value ->
                jsonObject.put(key, value)
            }
        }
        return objectToString(jsonObject,isFormat)
    }

    /**
     * JSONObject转json字符串
     */
    fun objectToString(jsonObject: JSONObject?, isFormat: Boolean = true): String {
        if (jsonObject == null) return ""
        return  if (isFormat) formatJson(mGson.toJson(jsonObject)) else mGson.toJson(jsonObject)
    }

    /**
     * JsonObject转json字符串
     */
    fun objectToString(jsonObject: JsonObject?, isFormat: Boolean = true): String {
        if (jsonObject == null) return ""
        return  if (isFormat) formatJson(mGson.toJson(jsonObject)) else mGson.toJson(jsonObject)
    }

    /**
     * map转json字符串
     */
    fun mapToString(map: Map<*, *>?, isFormat: Boolean = true): String {
        if (map == null) return ""
        return if (isFormat) formatJson(mGson.toJson(map)) else mGson.toJson(map)
    }

    /**
     * uri转json字符串
     */
    fun uriToString(uri: Uri?, isFormat: Boolean = true): String {
        if (uri == null) return ""
        return if (isFormat) formatJson(mGson.toJson(uri)) else mGson.toJson(uri)
    }

    /**
     *  intent转json字符串
     */
    fun intentToString(intent: Intent?, isFormat: Boolean = true): String {
        if (intent == null) return ""
        val jsonObject = JsonObject()
        jsonObject.addProperty("intent", intent.toString())
        jsonObject.addProperty("package", intent.`package`)
        jsonObject.addProperty("extras", bundleToJsonString(intent.extras))
        jsonObject.addProperty("scheme", intent.scheme)
        return if (isFormat) formatJson(mGson.toJson(jsonObject)) else mGson.toJson(jsonObject)
    }

}