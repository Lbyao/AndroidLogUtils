package com.liubuyao.utils

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * @author lby
 */
object PermissionsUtils {

    /**
     * 是否获取到权限
     *
     * @return true 有权限  false 无
     */
    fun getIsGrantedPermission(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (!getIsGrantedPermission(permission)) {
                return false
            }
        }
        return true
    }

    /**
     * 是否获取到权限
     *
     * @return true 有权限  false 无
     */
    fun getIsGrantedPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            MyUtils.getApp(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

}