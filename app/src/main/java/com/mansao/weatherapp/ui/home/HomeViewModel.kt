package com.mansao.weatherapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mansao.weatherapp.BuildConfig
import com.mansao.weatherapp.data.network.response.WeatherResponse
import com.mansao.weatherapp.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse: LiveData<WeatherResponse> = _weatherResponse

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun searchCityWeatherData(location: String) {
        _isLoading.postValue(true)

        val client =
            ApiConfig.getWeatherApiService().getCurrentWeather(BuildConfig.API_KEY, location)
        client.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _weatherResponse.postValue(responseBody)
                    _isLoading.postValue(false)
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _isLoading.postValue(false)
                t.printStackTrace()
            }
        })
    }
}