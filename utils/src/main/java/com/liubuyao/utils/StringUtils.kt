package com.liubuyao.utils

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import java.lang.ref.Reference

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

    fun AnyToString(any: Any?): String {
        if (any == null) return "can`t print null"
        return when (any) {
            is String, is Boolean, is Int, is Long, is Float, is Double, is Char, is Byte -> {
                any.toString()
            }
            is Collection<*> -> {
                ""
            }
            is Map<*, *> -> {
                ""
            }
            is Bundle -> {
                ""
            }
            is Reference<*> -> {
                ""
            }
            is Intent -> {
                ""
            }
            is Uri -> {
                ""
            }
            is Throwable -> {
                MyUtils.getThrowableInfoToString(any)
            }
            else -> {
                ""
            }
        }
    }

}