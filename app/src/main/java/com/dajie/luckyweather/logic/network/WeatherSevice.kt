package com.dajie.luckyweather.logic.network

import com.dajie.luckyweather.MyApplication
import com.dajie.luckyweather.logic.model.DailyResponse
import com.dajie.luckyweather.logic.model.RealTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**

 *  Created by DaJie on 2020/10/15

 */
interface WeatherSevice {
    @GET("v2.5/${MyApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealTimeWeather(
        @Path("lng") lng: String,
        @Path("lng") lat: String
    ): Call<RealTimeResponse>

    @GET("v2.5/${MyApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lng") lat: String): Call<DailyResponse>
}