package com.dajie.luckyweather.logic.model

import com.google.gson.annotations.SerializedName

/**

 *  Created by DaJie on 2020/10/10
 *  创建一些实体类

 */
data class PlaceResponse (val status:String,val places:List<Place>)

/**
 * @SerializedName("formatted_address")
 * Json字段和Kotlin中的命名不太一致，用@SerializedName注解的方式让JSON
 */
data class Place(val name:String, val location: Location, @SerializedName("formatted_address") val  address:String)

data class Location(val lng:String,val lat:String)
