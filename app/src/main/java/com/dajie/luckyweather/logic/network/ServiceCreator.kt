package com.dajie.luckyweather.logic.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.logging.Level

/**

 *  Created by DaJie on 2020/10/10
 *  单例类

 */
object ServiceCreator {


    private const val BASE_URL = "https://api.caiyunapp.com/"
    private val mHttpLoggingInterceptor = HttpLoggingInterceptor("LuckyWeather")
    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(OkHttpClient().newBuilder().addInterceptor(mHttpLoggingInterceptor).build())
            .build()

    fun <T> create(serviceClass: Class<T>): T {
       return retrofit.create(serviceClass)
    }

    inline fun <reified T> create(): T = create(T::class.java)
}