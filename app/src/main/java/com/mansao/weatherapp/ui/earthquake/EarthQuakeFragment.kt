package com.mansao.weatherapp.ui.earthquake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel.apply {
            getRecentQuake()
            quakeData.observe(viewLifecycleOwner) {
                binding.apply {

                    //cardView2
                    tvDate.text = it.data.tanggal
                    tvTime.text = it.data.jam
                    tvLocation.text = it.data.wilayah

                    //cardView2
                    tvDateTime.text = it.data.datetime
                    tvCoordinate.text = StringBuilder(getString(R.string.coordinate)).append(" ${it.data.coordinates}")
                    tvLatitude.text = StringBuilder(getString(R.string.latitude)).append(" ${it.data.lintang}")
                    tvLongitude.text = StringBuilder(getString(R.string.longitude)).append(" ${it.data.bujur}")
                    tvMagnitude.text = StringBuilder(getString(R.string.magnitude)).append(" ${it.data.magnitude}")
                    tvDepth.text = StringBuilder(getString(R.string.depth)).append(" ${it.data.kedalaman}")
                    tvPotential.text = StringBuilder(getString(R.string.potential)).append(" ${it.data.potensi}")
                    tvFelt.text = StringBuilder(getString(R.string.felt)).append(" ${it.data.dirasakan}")
                }
            }
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
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

}