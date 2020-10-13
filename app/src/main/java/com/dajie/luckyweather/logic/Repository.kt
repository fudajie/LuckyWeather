package com.dajie.luckyweather.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.dajie.luckyweather.logic.model.Place
import com.dajie.luckyweather.logic.network.LuckyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

/**

 *  Created by DaJie on 2020/10/12

 */
object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = LuckyWeatherNetwork.searchPlaces(query)
            Log.e("Repository", "placeResponse------>$placeResponse")
            if(placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result as Result<*>)
    }
}