package com.liubuyao.androidlogutils.network

import com.liubuyao.utils.interceptor.HttpLoggingInterceptor
import com.liubuyao.utils.interceptor.NetworkLogInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitUtils {

    private lateinit var mService: ApiService

    /**
     * 初始化网络请求
     */
    fun initRetrofit() {
        val okhttp = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
//            .addInterceptor(NetworkLogInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .client(okhttp)
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mService = retrofit.create(ApiService::class.java)
    }

    /**
     * 获取service
     */
    fun getService(): ApiService {
        if (this::mService.isInitialized) {
            return mService
        }
        throw NullPointerException("未初始化网络请求")
    }

}