package com.liubuyao.androidlogutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import com.liubuyao.androidlogutils.test.Utils
import com.liubuyao.utils.CrashUtils
import com.liubuyao.utils.LogUtils

/**
 * @author lby
 */
class MainActivity : AppCompatActivity() {

    val list = mutableListOf<String>()
    var isOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val map = mutableMapOf<String,User>()
        map["key1"] = User("test",2,false)
        map["key2"] = User("test2",22,true)
        LogUtils.d(map)

        findViewById<AppCompatButton>(R.id.btnTest).setOnClickListener {
//            findViewById<TextView>(R.id.tvText).text = list[2]
//            LogUtils.d("这都没超过-btnTest".repeat(2000))
            val list = mutableListOf<User>()
            list.add(User("test",2,false))
            list.add(User("这都没超过-test2".repeat(2000),22,true))
            LogUtils.d(list)

            val map = mutableMapOf<String,User>()
            map["key1"] = User("test",2,false)
            map["key2"] = User("test2",22,true)
            LogUtils.d(map)

            Utils.test()
        }

        findViewById<AppCompatButton>(R.id.btnOpen).setOnClickListener {
            isOpen = !isOpen
            CrashUtils.openCrashSaveFile(isOpen)
            LogUtils.d(User("test",2,false),User("test2",22,true))

        }

    }
}