package com.mansao.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mansao.weatherapp.R

class ListForecastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_forecast)
    }

    companion object{
        const val EXTRA_DATA ="extra_data"
    }
}