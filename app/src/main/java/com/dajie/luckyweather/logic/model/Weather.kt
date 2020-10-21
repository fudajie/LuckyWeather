package com.dajie.luckyweather.logic.model

/**

 *  Created by DaJie on 2020/10/15

 */
data class Weather(val realtime: RealTimeResponse.RealTime,val daily: DailyResponse.Daily)
