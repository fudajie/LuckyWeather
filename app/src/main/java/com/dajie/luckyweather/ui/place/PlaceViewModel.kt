package com.dajie.luckyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dajie.luckyweather.logic.Repository
import com.dajie.luckyweather.logic.dao.PlaceDao
import com.dajie.luckyweather.logic.model.Place

/**

 *  Created by DaJie on 2020/10/12

 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query:String){
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)
    fun getSavedPlace() = Repository.getSavedPlace()
    fun isPlaceSaved() = Repository.isPlaceSaved()
}