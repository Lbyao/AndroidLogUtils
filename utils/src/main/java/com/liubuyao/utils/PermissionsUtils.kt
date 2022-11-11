package com.liubuyao.utils

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.liubuyao.utils.bluetoothUtils.BluetoothHelper
import java.lang.ref.WeakReference

/**
 * @author lby
 */
object PermissionsUtils {

    lateinit var systemLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var mContext: WeakReference<ComponentActivity>

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
            MyUtils.getApp(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(vararg permissions: String, context: ComponentActivity) {
        systemLauncher = mContext.get()!!.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            result?.let {
                ToastUtils.showToast("result:$it")
                for (permission in permissions){
                    val b = it[permission]
                    if (b != true) {
                        ToastUtils.showToast("权限获取失败:$permission")
                    }
                }
            }
        }
        systemLauncher.launch(permissions)
    }
}