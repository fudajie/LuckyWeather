package com.dajie.luckyweather.ui.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dajie.luckyweather.R

/**

 *  Created by DaJie on 2020/10/19

 */
class WeatherActivity : AppCompatActivity() {
    lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

    }
}