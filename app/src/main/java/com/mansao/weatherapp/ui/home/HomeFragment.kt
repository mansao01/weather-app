package com.mansao.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.mansao.weatherapp.R
import com.mansao.weatherapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        homeViewModel.apply {
            searchCityWeatherData("jakarta")
            weatherResponse.observe(viewLifecycleOwner) {
                val iconUrl = "https:${it.current.condition.icon}"
                Log.d("Home Fragment", iconUrl)
                binding.apply {
                    textHome.text = it.location.name
                    tvTempC.text = StringBuilder(it.current.tempC.toString()).append(getString(R.string.degree_celcius))
                    Glide.with(context!!.applicationContext)
                        .load(iconUrl)
                        .into(binding.ivWeather)
                }
            }
            isLoading.observe(viewLifecycleOwner){
                showProgressBar(it)
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}