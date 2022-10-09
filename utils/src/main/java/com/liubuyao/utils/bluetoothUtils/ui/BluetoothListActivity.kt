package com.liubuyao.utils.bluetoothUtils.ui

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liubuyao.utils.R
import com.liubuyao.utils.bluetoothUtils.BluetoothDeviceAdapter
import com.liubuyao.utils.bluetoothUtils.BluetoothHelper

class BluetoothListActivity : Activity() {

    private lateinit var bluetoothDeviceAdapter: BluetoothDeviceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_list)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initView()

        BluetoothHelper.setOnScanListener(object : BluetoothHelper.OnScanListener {
            override fun deviceList(device: BluetoothDevice) {
                bluetoothDeviceAdapter.addData(device)
            }

            override fun deviceConnectList(device: BluetoothDevice) {
                bluetoothDeviceAdapter.addData(device)
            }

        })
    }

    private fun initView() {
        val rvList = findViewById<RecyclerView>(R.id.rvList)
        bluetoothDeviceAdapter = BluetoothDeviceAdapter()
        rvList.adapter = bluetoothDeviceAdapter
        rvList.layoutManager = LinearLayoutManager(this)
        bluetoothDeviceAdapter.setOnClickListener(object :BluetoothDeviceAdapter.OnClickListener{
            override fun connectClick(device: BluetoothDevice) {
                val intent = Intent()
                intent.putExtra("device",device)
                setResult(RESULT_OK,intent)
                finish()
            }

        })
    }

}