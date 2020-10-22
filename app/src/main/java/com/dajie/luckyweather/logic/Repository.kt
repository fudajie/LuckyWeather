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