package com.mansao.weatherapp.ui.weather

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mansao.weatherapp.R
import com.mansao.weatherapp.data.network.response.FullWeatherResponse
//import com.mansao.weatherapp.data.network.response.WeatherResponse
import com.mansao.weatherapp.databinding.FragmentWeatherBinding


class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
    private val weatherViewModel by viewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(context, "Still working on this features", Toast.LENGTH_SHORT).show()

        showProgressBar(false)
        searchLocationWeather()
        weatherViewModel.weatherResponse.observe(viewLifecycleOwner){
            setData(it)
        }
    }

    private fun searchLocationWeather(){
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.apply {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            searchView.queryHint = getString(R.string.search_city_weather)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    weatherViewModel.apply {
                        query?.let { searchCityWeather(it) }
                        isLoading.observe(viewLifecycleOwner){
                            showProgressBar(it)
                        }
                        weatherResponse.observe(viewLifecycleOwner){
                            setData(it)
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }

    private fun setData(data: FullWeatherResponse){
        binding.tvCity.text = data.location.region

    }

    private fun showProgressBar(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

}