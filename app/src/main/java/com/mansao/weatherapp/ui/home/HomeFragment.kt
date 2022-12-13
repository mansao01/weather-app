package com.mansao.weatherapp.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mansao.weatherapp.R
import com.mansao.weatherapp.data.network.response.FullWeatherResponse
import com.mansao.weatherapp.databinding.FragmentHomeBinding
import com.mansao.weatherapp.ui.adapter.ForecastWeatherListAdapter
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2
    private lateinit var adapter: ForecastWeatherListAdapter

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

        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(context!!.applicationContext)

        adapter = ForecastWeatherListAdapter()
        binding.apply {
            rvForecast.setHasFixedSize(true)
            rvForecast.adapter = adapter
            rvForecast.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        getLocation()
    }


    private fun isLocationIsEnable(): Boolean {
        val locationManager: LocationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermission(): Boolean {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), permissionId
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getLocation() {
        if (checkPermission()) {
            if (isLocationIsEnable()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geoCoder = context?.let { Geocoder(it, Locale.getDefault()) }
                        val list: MutableList<Address>? =
                            geoCoder?.getFromLocation(location.latitude, location.longitude, 1)

//                            get latitude and longitude from gms
                        val latitudeLongitude =
                            "${list?.get(0)?.latitude},${list?.get(0)?.longitude}"
                        Log.d(TAG, "Latitude&Longitude : $latitudeLongitude")

                        homeViewModel.apply {
                            searchCityWeatherData(latitudeLongitude)
                            weatherResponse.observe(viewLifecycleOwner) {
                                setAllData(it)
                            }
                            isLoading.observe(viewLifecycleOwner) {
                                showProgressBar(it)
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Please Turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun setAllData(data: FullWeatherResponse) {
        binding.apply {
            val iconUrl =
                StringBuilder(getString(R.string.https)).append(data.current.condition.icon)
            tvCity.text = data.location.name
            tvTempC.text =
                StringBuilder(data.current.tempC.toString()).append(
                    getString(R.string.degree_celsius)
                )
            tvTempF.text =
                StringBuilder(data.current.tempF.toString()).append(
                    getString(R.string.degree_fahrenheit)
                )
            tvCondition.text = data.current.condition.text
            tvLocalTime.text = data.location.localtime
            Glide.with(context!!.applicationContext)
                .load(iconUrl.toString())
                .into(binding.ivWeather)

            //inside cardView air Quality
            tvUv.text = data.current.uv.toString()
            tvIon.text = data.location.lon.toString()
            tvHumidity.text = data.current.humidity.toString()

            for (i in data.forecast.forecastday) {
                adapter.setListForecast(i.hour)

            }
        }
    }

    private fun showProgressBar(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
                tvCity.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                tvCity.visibility = View.VISIBLE

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
    }
}