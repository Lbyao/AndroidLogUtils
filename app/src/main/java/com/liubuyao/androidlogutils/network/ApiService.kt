package com.liubuyao.androidlogutils.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {

    @Headers("Cache-Control: max-age=640000")
    @GET("article/list/{pageIndex}/json")
    suspend fun getArticleList(@Path("pageIndex") pageIndex: Int): Any

}