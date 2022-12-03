package com.mansao.weatherapp.ui.earthquake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
                    tvDate.text = it.data.tanggal
                    tvTime.text = it.data.jam
                    tvLocation.text = it.data.wilayah
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