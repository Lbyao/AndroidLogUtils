package com.liubuyao.androidlogutils

import android.app.Application
import com.liubuyao.androidlogutils.network.RetrofitUtils

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitUtils.initRetrofit()
    }

}