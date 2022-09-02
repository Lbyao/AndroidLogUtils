package com.liubuyao.androidlogutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import kotlinx.coroutines.GlobalScope

/**
 * @author lby
 */
class MainActivity : AppCompatActivity() {

    val list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.btnTest).setOnClickListener {
            findViewById<TextView>(R.id.tvText).text = list[2]
        }

    }
}