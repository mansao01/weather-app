package com.mansao.weatherapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mansao.weatherapp.R
import com.mansao.weatherapp.data.network.response.HourItem
import com.mansao.weatherapp.databinding.WeatherForecastListBinding
import com.mansao.weatherapp.helper.ForecastDiffCallback

class ForecastWeatherListAdapter :
    RecyclerView.Adapter<ForecastWeatherListAdapter.ForecastWeatherViewHolder>() {
    private val listWeatherForecast = ArrayList<HourItem>()

    fun setListForecast(listForecast: List<HourItem>){
        val diffCallback = ForecastDiffCallback(this.listWeatherForecast, listForecast)
        val diffResult  = DiffUtil.calculateDiff(diffCallback)
        this.listWeatherForecast.clear()
        this.listWeatherForecast.addAll(listForecast)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastWeatherViewHolder {
        val binding =
            WeatherForecastListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastWeatherViewHolder, position: Int) {
        holder.bind(listWeatherForecast[position])
    }

    override fun getItemCount() = listWeatherForecast.size

    inner class ForecastWeatherViewHolder(private val binding: WeatherForecastListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(forecastWeather: HourItem) {
            binding.apply {
               val icon =  StringBuilder(itemView.context.getString(R.string.https)).append(forecastWeather.condition.icon)
                tvDateTime.text = forecastWeather.time
                tvTempC.text = StringBuilder(forecastWeather.tempC.toString()).append(itemView.context.getString(R.string.degree_celsius))
                tvTempF.text = StringBuilder(forecastWeather.tempF.toString()).append(itemView.context.getString(R.string.degree_fahrenheit))
                tvCondition.text = forecastWeather.condition.text
                Log.d("ForecastAdapter", icon.toString())
                Glide.with(itemView.context)
                    .load(icon.toString())
                    .centerCrop()
                    .into(ivWeather)
            }
        }
    }
}