package com.liubuyao.utils.interceptor

import com.liubuyao.utils.LogUtils
import com.liubuyao.utils.MyUtils
import okhttp3.Interceptor
import okhttp3.Response

class NetworkLogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val networkLogMap = mutableMapOf<String, String>()
        val startRequestMillis = System.currentTimeMillis()
        networkLogMap["network_start_request"] = "$startRequestMillis"

        val request = chain.request()

        val endRequestMillis = System.currentTimeMillis()
        networkLogMap["network_end_request"] = "${endRequestMillis - startRequestMillis}"

        val requestToString = MyUtils.getRequestToString(request)

        val endRequestStringMillis = System.currentTimeMillis()
        networkLogMap.putAll(requestToString)
        networkLogMap["network_end_request_string"] = "${endRequestStringMillis - endRequestMillis}"

        val response = chain.proceed(request)
        //請求時間
        val responseMillis = System.currentTimeMillis()
        networkLogMap["network_end_response"] = "${responseMillis - endRequestStringMillis}"
        //解析返回結果
        val responseToString = MyUtils.getResponseToString(response)
        val responseStringMillis = System.currentTimeMillis()
        networkLogMap.putAll(responseToString)
        //解析的時間
        networkLogMap["network_end_response_string"] = "${responseStringMillis - responseMillis}"
        LogUtils.d(networkLogMap)
        return response
    }
}