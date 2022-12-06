package com.mansao.weatherapp.data.network.retrofit

import com.mansao.weatherapp.data.network.response.QuakeResponse
import com.mansao.weatherapp.data.network.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("quake")
    fun getRecentQuake(): Call<QuakeResponse>

    @GET("v1/forecast.json?")
    fun getCurrentWeather(
        @Query("key")
        key: String,
        @Query("q")
        q: String
    ): Call<WeatherResponse>
}