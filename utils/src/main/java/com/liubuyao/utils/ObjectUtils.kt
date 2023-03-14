package com.liubuyao.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

object ObjectUtils {

    private fun getClassFromObject(obj: Any): Class<*>? {
        val objClass: Class<*> = obj.javaClass
        if (objClass.isAnonymousClass || objClass.isSynthetic) {
            val genericInterfaces: Array<Type> = objClass.genericInterfaces
            var className: String
            if (genericInterfaces.size == 1) { // interface
                var type: Type = genericInterfaces[0]
                while (type is ParameterizedType) {
                    type = (type as ParameterizedType).rawType
                }
                className = type.toString()
            } else { // abstract class or lambda
                var type: Type = objClass.genericSuperclass
                while (type is ParameterizedType) {
                    type = (type as ParameterizedType).rawType
                }
                className = type.toString()
            }
            if (className.startsWith("class ")) {
                className = className.substring(6)
            } else if (className.startsWith("interface ")) {
                className = className.substring(10)
            }
            try {
                return Class.forName(className)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
        return objClass
    }

    private fun <T> getTypeClassFromParadigm(formatter: Class<*>): Class<*>? {
        val genericInterfaces: Array<Type> = formatter.genericInterfaces
        var type: Type
        type = if (genericInterfaces.size == 1) {
            genericInterfaces[0]
        } else {
            formatter.genericSuperclass as Type
        }
        type = (type as ParameterizedType).actualTypeArguments[0]
        while (type is ParameterizedType) {
            type = type.rawType
        }
        var className = type.toString()
        if (className.startsWith("class ")) {
            className = className.substring(6)
        } else if (className.startsWith("interface ")) {
            className = className.substring(10)
        }
        try {
            return Class.forName(className)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return null
    }


}