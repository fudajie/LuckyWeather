package com.dajie.luckyweather.logic.network

import com.dajie.luckyweather.MyApplication
import com.dajie.luckyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**

 *  Created by DaJie on 2020/10/10

 */
interface PlaceService {
    @GET("v2/place?token=${MyApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}