package com.dajie.luckyweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dajie.luckyweather.ui.place.PlaceViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: PlaceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
