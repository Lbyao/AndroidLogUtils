package com.liubuyao.androidlogutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.liubuyao.utils.CrashUtils

/**
 * @author lby
 */
class MainActivity : AppCompatActivity() {

    val list = mutableListOf<String>()
    var isOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.btnTest).setOnClickListener {
            findViewById<TextView>(R.id.tvText).text = list[2]
        }

        findViewById<AppCompatButton>(R.id.btnOpen).setOnClickListener {
            isOpen = !isOpen
            CrashUtils.openCrashSaveFile(isOpen)
        }

    }
}