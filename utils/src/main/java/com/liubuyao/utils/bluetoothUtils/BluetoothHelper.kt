package com.liubuyao.utils.bluetoothUtils

import android.Manifest
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.liubuyao.utils.LogUtils
import com.liubuyao.utils.ToastUtils.showToast
import java.lang.ref.WeakReference

object BluetoothHelper {

    private const val TAG = "BluetoothHelper"

    private lateinit var mContext: WeakReference<ComponentActivity>

    // 蓝牙适配器
    private val mBluetoothAdapter: BluetoothAdapter by lazy {
        // 初始化蓝牙适配器
        val bluetoothManager =
            mContext.get()!!.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private var mOnScanListener: OnScanListener? = null

    // 蓝牙扫描广播接收器
    private val mBluetoothScanReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val action = intent.action
                if (BluetoothDevice.ACTION_FOUND == action) {// 扫描到蓝牙设备
                    // 获取扫描到的设备
                    val device = intent.getParcelableExtra<BluetoothDevice>(
                        BluetoothDevice.EXTRA_DEVICE
                    )
                    // 设备相关信息
                    // device.name?：设备名
                    // device.address：设备MAC地址
                    // device.bluetoothClass.majorDeviceClass：设备类型
                    if (device != null) {
                        if (!device.name.isNullOrEmpty()) {
                            mOnScanListener?.deviceList(device)
                        }
                    }
                } else if (BluetoothDevice.ACTION_ACL_CONNECTED == action) {// 扫描到已经被连接的蓝牙设备（附近已经建立连接的蓝牙设备，该连接不一定是和本中央设备）
                    val device = intent.getParcelableExtra<BluetoothDevice>(
                        BluetoothDevice.EXTRA_DEVICE
                    )
                    if (device != null) {
                        if (!device.name.isNullOrEmpty()) {
                            mOnScanListener?.deviceConnectList(device)
                        }
                    }
                }
            }
        }
    }

    lateinit var systemLauncher: ActivityResultLauncher<Array<String>>
    lateinit var btLauncher: ActivityResultLauncher<Intent>


    /**
     * 初始化
     */
    fun init(context: ComponentActivity): BluetoothHelper {
        mContext = WeakReference(context)
        systemLauncher = mContext.get()!!.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            result?.let {
                showToast("result:$it")
                val b = it[Manifest.permission.ACCESS_FINE_LOCATION]
                if (b != true){
                    showToast("请进入设置打开位置权限，否则无法扫描到蓝牙。")
                }
            }
        }
        btLauncher = mContext.get()!!.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            showToast("result:$it")
        }
        systemLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        initBluetoothStateReceiver()
        return this
    }

    /**
     * 初始化蓝牙广播
     */
    fun initBluetoothStateReceiver() {
        val stateIntentFilter = IntentFilter()
        //监听蓝牙状态改变Action
        stateIntentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        mContext.get()!!.registerReceiver(mBluetoothStateReceiver, stateIntentFilter)
        val scanIntentFilter = IntentFilter()
        //监听蓝牙扫描的列表
        scanIntentFilter.addAction(BluetoothDevice.ACTION_FOUND)
        mContext.get()!!.registerReceiver(mBluetoothScanReceiver, scanIntentFilter)
    }

    // 是否开启蓝牙
    fun isBluetoothEnabled(): Boolean {
        return mBluetoothAdapter.isEnabled
    }

    // 开启蓝牙
    fun openBluetooth(): BluetoothHelper {
        if (!mBluetoothAdapter.isEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ActivityCompat.checkSelfPermission(
                        mContext.get()!!, Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    systemLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_CONNECT))
                    showToast("建议允许开启蓝牙权限")
                    return this
                }
            }
            if (!mBluetoothAdapter.enable()) {
                // 尝试开启蓝牙，返回值为是否成功开启蓝牙
                btLauncher.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            }
        }
        return this
    }

    // 蓝牙状态改变广播接收器
    private val mBluetoothStateReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) {
                    BluetoothAdapter.STATE_ON -> {
                        showToast("蓝牙已打开")
                    }
                    BluetoothAdapter.STATE_OFF -> {
                        showToast("蓝牙已关闭")
                    }
                    BluetoothAdapter.STATE_TURNING_ON -> {
                        showToast("蓝牙正在打开")
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        showToast("蓝牙正在关闭")
                    }
                }
            }
        }
    }

    // 搜索蓝牙设备
    fun searchDevices() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(
                    mContext.get()!!, Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                systemLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_SCAN))
                showToast("建议允许开启蓝牙权限")
                return
            }
        }
        if (!isBluetoothEnabled()) {
            openBluetooth()
        } else {
            if (mBluetoothAdapter.isDiscovering) {// 如果正在搜索则取消搜索
                mBluetoothAdapter.cancelDiscovery()
            } else {
                mBluetoothAdapter.startDiscovery()
            }
        }
    }

    // 停止扫描蓝牙设备
    fun stopSearchDevices(): BluetoothHelper {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(
                    mContext.get()!!, Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                systemLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_SCAN))
                showToast("建议允许开启蓝牙权限")
                return this
            }
        }
        if (mBluetoothAdapter.isDiscovering) {
            mBluetoothAdapter.cancelDiscovery()
        }
        return this
    }

    fun searchLeDevices() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(
                    mContext.get()!!, Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                systemLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_SCAN))
                showToast("建议允许开启蓝牙权限")
                return
            }
        }
        if (!isBluetoothEnabled()) {
            openBluetooth()
        } else {
            mBluetoothAdapter.bluetoothLeScanner.startScan(mScanCallback)
        }
    }

    private val mScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ActivityCompat.checkSelfPermission(
                        mContext.get()!!, Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    systemLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_CONNECT))
                    showToast("建议允许开启蓝牙权限")
                    return
                }
            }
            val deviceName = result?.device?.name ?: return
            if (result.device != null) {
                mOnScanListener?.deviceList(result.device)
            }
            LogUtils.d(deviceName)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)

        }
    }

    // 停止扫描蓝牙设备
    fun stopSearchLeDevices(): BluetoothHelper {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(
                    mContext.get()!!, Manifest.permission.BLUETOOTH_SCAN
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                systemLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_SCAN))
                showToast("建议允许开启蓝牙权限")
                return this
            }
        }
        mBluetoothAdapter.bluetoothLeScanner.stopScan(mScanCallback)
        return this
    }


    // 蓝牙gatt
    private var mBluetoothGatt: BluetoothGatt? = null

    // gatt连接蓝牙设备
    fun connect(device: BluetoothDevice, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(
                    mContext.get()!!, Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                systemLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_CONNECT))
                showToast("建议允许开启蓝牙权限")
                return
            }
        }
        mBluetoothGatt = device.connectGatt(context, false, mGattCallback)
    }

    // gatt连接回调
    private val mGattCallback by lazy {
        object : BluetoothGattCallback() {
            // 连接状态改变时回调
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                if (newState == BluetoothProfile.STATE_CONNECTED) {// 成功连接
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        if (ActivityCompat.checkSelfPermission(
                                mContext.get()!!, Manifest.permission.BLUETOOTH_CONNECT
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            systemLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_CONNECT))
                            showToast("建议允许开启蓝牙权限")
                            return
                        }
                    }
                    mBluetoothGatt?.discoverServices() // 调用发现服务（即获取到蓝牙设备所提供的所有服务）
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {// 断开连接
                }
            }

            // 调用发现服务时回调
            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {// 当前仍处于连接状态的话
                    val bluetoothGattServices = gatt!!.services // 获取蓝牙设备提供的所有服务
                    // 遍历所有服务
                    bluetoothGattServices.forEachIndexed { index, bluetoothGattService ->
                        val uuid = bluetoothGattService.uuid // 获取到此服务的UUID
                        val bluetoothGattCharacteristics =
                            bluetoothGattService.characteristics // 获取到此服务的所有特征
                        Log.d(TAG, "服务${index + 1}的UUID为：$uuid")
                        Log.d(TAG, "服务${index + 1}有以下特征：")
                        // 遍历该服务的所有特征
                        bluetoothGattCharacteristics.forEachIndexed { index, bluetoothGattCharacteristic ->
                            Log.d(
                                TAG, "  特征${index + 1}的UUID：${bluetoothGattCharacteristic.uuid}"
                            ) // 输出当前特征的UUID
                            val charaProp = bluetoothGattCharacteristic.properties// 获取当前特征的所有属性
                            // bluetoothGattCharacteristic.descriptors // 获取当前特征的所有描述符
                            if (charaProp and BluetoothGattCharacteristic.PROPERTY_READ > 0) {
                                Log.d(TAG, "    特征${index + 1}有read属性")
                            }
                            if (charaProp and BluetoothGattCharacteristic.PROPERTY_WRITE > 0) {
                                Log.d(TAG, "    特征${index + 1}有write属性")
                            }
                            if (charaProp and BluetoothGattCharacteristic.PROPERTY_NOTIFY > 0) {
                                Log.d(TAG, "    特征${index + 1}有notify属性")
                            }
                            if (charaProp and BluetoothGattCharacteristic.PROPERTY_INDICATE > 0) {
                                Log.d(TAG, "    特征${index + 1}有indicate属性")
                            }
                        }
                    }
                }
            }

            // 读数据时回调
            override fun onCharacteristicRead(
                gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int
            ) {
            }

            // 写数据时回调
            override fun onCharacteristicWrite(
                gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int
            ) {
                super.onCharacteristicWrite(gatt, characteristic, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    // 发送成功
                } else {
                    // 发送失败
                }
            }

            // 远程数据改变回调
            override fun onCharacteristicChanged(
                gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?
            ) {
                super.onCharacteristicChanged(gatt, characteristic)
                characteristic?.apply {// 发生数据变化的特征
                    Log.d(TAG, "onCharacteristicChanged: ${characteristic.uuid}")
                }
            }
        }
    }

    //某个特征
    private var mBluetoothGattCharacteristic: BluetoothGattCharacteristic? = null

    //发送数据
    fun sendMess(mess: String) {
        mBluetoothGattCharacteristic?.setValue(mess)// 修改此特征的值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(
                    mContext.get()!!, Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                systemLauncher.launch(arrayOf(Manifest.permission.BLUETOOTH_CONNECT))
                showToast("建议允许开启蓝牙权限")
                return
            }
        }
        mBluetoothGatt?.writeCharacteristic(mBluetoothGattCharacteristic) // 写入此特征来向蓝牙设备发送数据
    }

    fun setOnScanListener(onScanListener: OnScanListener) {
        mOnScanListener = onScanListener
    }

    interface OnScanListener {
        fun deviceList(device: BluetoothDevice)
        fun deviceConnectList(device: BluetoothDevice)
    }

}