package com.dajie.luckyweather.logic.model

import com.google.gson.annotations.SerializedName

/**

 *  Created by DaJie on 2020/10/15
 *  实时天气实体类
 *  将所有的数据模型实体类都定义在了RealTimeResponse里面，可以防止出现和其他接口的数据模型类有同名冲突的情况

 */

data class RealTimeResponse(val status:String,val result:Result){
    data class Result(val realTime:RealTime)
    data class RealTime(val skycon:String,val temperature:Float,@SerializedName("air_quality") val airQuality:AirQuality)
    data class AirQuality(val aqi:AQI)
    data class AQI(val chn:Float)
}