package com.dajie.luckyweather.ui.weather

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dajie.luckyweather.R
import com.dajie.luckyweather.logic.model.Weather
import com.dajie.luckyweather.logic.model.getSky
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.layout_forecast.*
import kotlinx.android.synthetic.main.layout_life_index.*
import kotlinx.android.synthetic.main.layout_now.*
import java.text.SimpleDateFormat
import java.util.*

/**

 *  Created by DaJie on 2020/10/19

 */
class WeatherActivity : AppCompatActivity() {
    val viewModel by lazy {
        ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorWindow = window.decorView
        decorWindow.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_weather)
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }

        viewModel.weatherLiveData.observe(this, Observer {
            val weather = it.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather as Weather)
            } else {
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()
            }

        })

        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
    }


    private fun showWeatherInfo(weather: Weather) {
        //填充now布局的数据
        placeName_tv.text = viewModel.placeName
        val realTime = weather.realtime
        val daily = weather.daily
        val currentTempText = "${realTime.temperature.toInt()} ℃"
        currentTemp_tv.text = currentTempText
        currentSky_tv.text = getSky(realTime.skycon).info
        val currentPM25Text = "空气指数${realTime.airQuality.aqi.chn.toInt()}"
        currentAQI_tv.text = currentPM25Text
        now_layout.setBackgroundResource(getSky(realTime.skycon).bg)

        //填充forecast中的數據
        forecast_layout.removeAllViews()
        val days = daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view = LayoutInflater.from(this)
                .inflate(R.layout.layout_forecast_item, forecast_layout, false)
            val dateInfoTv = view.findViewById(R.id.dateInfo_tv) as TextView
            val skyIconTv = view.findViewById(R.id.skyIcon_tv) as ImageView
            val skyInfoTv = view.findViewById(R.id.skyInfo_tv) as TextView
            val temperatureInfoTv = view.findViewById(R.id.temperatureInfo_tv) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfoTv.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIconTv.setImageResource(sky.icon)
            skyInfoTv.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()}℃"
            temperatureInfoTv.text = tempText
            forecast_layout.addView(view)
        }

        //填充life中的数据
        val lifeIndex = daily.lifeIndex
        coldrisk_tv.text = lifeIndex.coldRisk[0].desc
        dressing_tv.text = lifeIndex.dressing[0].desc
        ultraviolet_tv.text = lifeIndex.ultraviolet[0].desc
        carWashing_tv.text = lifeIndex.carWashing[0].desc
        weather_layout.visibility = View.VISIBLE
    }

}