package com.mansao.weatherapp.ui.earthquake

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mansao.weatherapp.R
import com.mansao.weatherapp.databinding.FragmentEarthQuakeBinding


class EarthQuakeFragment : Fragment() {
    private lateinit var binding: FragmentEarthQuakeBinding
    private val viewModel by viewModels<EarthQuakeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEarthQuakeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       showRecentEarthquakeData()
        binding.apply {
            swipeRefresh.setOnRefreshListener {
                showRecentEarthquakeData()
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun showRecentEarthquakeData(){
        viewModel.apply {
            getRecentQuake()
            quakeData.observe(viewLifecycleOwner) {
                binding.apply {
                    val url = it.data.shakemap
                    //cardView1
                    tvDate.text = it.data.tanggal
                    tvTime.text = it.data.jam
                    tvLocation.text = it.data.wilayah
                    btnShakeMap.setOnClickListener {
                        val intentToShakeMap = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intentToShakeMap)
                    }

                    //cardView2
                    tvDateTime.text = it.data.datetime
                    tvCoordinate.text =
                        StringBuilder(getString(R.string.coordinate)).append(" ${it.data.coordinates}")
                    tvLatitude.text =
                        StringBuilder(getString(R.string.latitude)).append(" ${it.data.lintang}")
                    tvLongitude.text =
                        StringBuilder(getString(R.string.longitude)).append(" ${it.data.bujur}")
                    tvMagnitude.text =
                        StringBuilder(getString(R.string.magnitude)).append(" ${it.data.magnitude}")
                    tvDepth.text =
                        StringBuilder(getString(R.string.depth)).append(" ${it.data.kedalaman}")
                    tvPotential.text =
                        StringBuilder(getString(R.string.potential)).append(" ${it.data.potensi}")
                    tvFelt.text =
                        StringBuilder(getString(R.string.felt)).append(" ${it.data.dirasakan}")
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            showToast.observe(viewLifecycleOwner){
                showErrorToast(it)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE

            }
        }
    }

    private fun showErrorToast(state: Boolean){
        if (state) Toast.makeText(activity?.applicationContext, getString(R.string.something_is_wrong), Toast.LENGTH_LONG).show()
    }

}