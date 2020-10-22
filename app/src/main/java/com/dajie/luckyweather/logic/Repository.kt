package com.dajie.luckyweather.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.dajie.luckyweather.logic.dao.PlaceDao
import com.dajie.luckyweather.logic.model.Place
import com.dajie.luckyweather.logic.model.RealTimeResponse
import com.dajie.luckyweather.logic.model.Weather
import com.dajie.luckyweather.logic.network.LuckyWeatherNetwork
import com.tianjian.loglibrary.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.RuntimeException

/**

 *  Created by DaJie on 2020/10/12

 */
object Repository {
    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = LuckyWeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result as Result<*>)
    }

//    kotlin异步的一般步骤
//    async关键字创建协程；
//    await 挂起函数，启动协程,至少要有一个挂起函数。挂起函数只能在协程/挂起函数中被调用，不能在普通函数里调用。
//    协程与线程的区别
//    协程是通过编译技术实现(不需要虚拟机VM/操作系统OS的支持)，通过插入相关代码来生效。与之相反，线程/进程是需要虚拟机VM/操作系统OS的支持，通过调度CPU执行生效。
//    线程阻塞的代价昂贵，尤其在高负载时的可用线程很少，阻塞线程会导致一些重要任务缺少可用线程而被延迟。协程挂起几乎无代价，无需上下文切换或涉及OS。

    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {
                val deferredRealTime = async {
                    LuckyWeatherNetwork.getRealTimeWeather(lng, lat)
                }
                val deferredDaily = async {
                    LuckyWeatherNetwork.getDailyWeather(lng, lat)
                }
                val realTimeResponse = deferredRealTime.await()
                val dailyResponse = deferredDaily.await()
                if (realTimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather =
                        Weather(realTimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                } else {
                    Result.failure(RuntimeException("realtime response status is ${realTimeResponse.status}" + "daily response status is ${dailyResponse.status}"))
                }
            }
        } catch (e: java.lang.Exception) {
            Result.failure<List<Place>>(e)
        }
        emit(result as Result<*>)
    }
}