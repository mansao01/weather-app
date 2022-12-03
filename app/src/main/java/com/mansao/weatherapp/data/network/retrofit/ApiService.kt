package com.mansao.weatherapp.data.network.retrofit

import com.mansao.weatherapp.data.network.response.QuakeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("quake")
    fun getRecentQuake(): Call<QuakeResponse>
}