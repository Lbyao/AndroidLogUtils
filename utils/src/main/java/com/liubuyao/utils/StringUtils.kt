package com.liubuyao.utils

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

}