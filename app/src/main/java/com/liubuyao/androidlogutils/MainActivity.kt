package com.liubuyao.androidlogutils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import com.liubuyao.androidlogutils.network.RetrofitUtils
import com.liubuyao.androidlogutils.test.Utils
import com.liubuyao.utils.CrashUtils
import com.liubuyao.utils.LogUtils
import com.liubuyao.utils.ToastUtils
import com.liubuyao.utils.bluetoothUtils.BluetoothHelper
import com.liubuyao.utils.bluetoothUtils.ui.BluetoothListActivity
import kotlinx.coroutines.*

/**
 * @author lby
 */
class MainActivity : AppCompatActivity() {

    val list = mutableListOf<String>()
    var isOpen = true

    lateinit var acLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        BluetoothHelper.init(this@MainActivity)
        BluetoothHelper.init(this)

        acLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            ToastUtils.showToast("result:$it")
        }

        val map = mutableMapOf<String, User>()
        map["key1"] = User("test", 2, false)
        map["key2"] = User("test2", 22, true)
        LogUtils.d(map)



        findViewById<AppCompatButton>(R.id.btnTest).setOnClickListener {
//            findViewById<TextView>(R.id.tvText).text = list[2]
//            LogUtils.d("这都没超过-btnTest".repeat(2000))
            val list = mutableListOf<User>()
            list.add(User("test", 2, false))
            list.add(User("这都没超过-test2".repeat(2000), 22, true))
            LogUtils.setFileSwitch(true)
            for (i in 0..100) {
                LogUtils.d(list)
            }

            val map = mutableMapOf<String, User>()
            map["key1"] = User("test", 2, false)
            map["key2"] = User("test2", 22, true)
            LogUtils.d(map)

            Utils.test()
        }

        findViewById<AppCompatButton>(R.id.btnOpen).setOnClickListener {
            isOpen = !isOpen
            CrashUtils.openCrashSaveFile(isOpen)
            LogUtils.d(User("test", 2, false), User("test2", 22, true))

        }

        findViewById<AppCompatButton>(R.id.btnNetWork).setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val articleList = RetrofitUtils.getService().getArticleList(0)
//                LogUtils.d(articleList)
            }
        }

        findViewById<AppCompatButton>(R.id.btnScanBT).setOnClickListener {
            BluetoothHelper.searchLeDevices()
//            BluetoothHelper.searchDevices()
            acLauncher.launch(Intent(this, BluetoothListActivity::class.java))
        }
        findViewById<AppCompatButton>(R.id.btnStopScanBT).setOnClickListener {
            BluetoothHelper.stopSearchLeDevices()
        }

    }
}