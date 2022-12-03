package com.mansao.weatherapp.ui.earthquake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mansao.weatherapp.data.network.response.QuakeResponse
import com.mansao.weatherapp.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthQuakeViewModel: ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _quakeData = MutableLiveData<QuakeResponse>()
    val quakeData: LiveData<QuakeResponse> = _quakeData

    fun getRecentQuake(){
        val client = ApiConfig.getApiService().getRecentQuake()
        client.enqueue(object : Callback<QuakeResponse>{
            override fun onResponse(call: Call<QuakeResponse>, response: Response<QuakeResponse>) {
                _isLoading.postValue(false)
                if (response.isSuccessful){
                    val responseBody  = response.body()
                    _quakeData.postValue(responseBody)
                }
            }

            override fun onFailure(call: Call<QuakeResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}