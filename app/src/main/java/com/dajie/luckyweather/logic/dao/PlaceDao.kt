package com.dajie.luckyweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.dajie.luckyweather.MyApplication
import com.dajie.luckyweather.logic.model.Place
import com.google.gson.Gson

/**

 *  Created by DaJie on 2020/10/22
 *  单例类

 */
object PlaceDao {
    fun savePlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place","")
        return Gson().fromJson(placeJson,Place::class.java)
    }

    fun isPlaceSaved()= sharedPreferences().contains("place")

    private fun sharedPreferences() = MyApplication.context.getSharedPreferences(
        "lucky_weather",
        Context.MODE_PRIVATE
    )
}