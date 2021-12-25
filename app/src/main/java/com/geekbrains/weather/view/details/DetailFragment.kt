package com.geekbrains.weather.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.weather.databinding.DetailFragmentBinding
import com.geekbrains.weather.model.Weather

class DetailFragment : Fragment() {
    companion object {
        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Weather>("WEATHER_EXTRA")
            ?.let {
                binding.cityName.text = it.city.name
                binding.temperature.text = it.temperature.toString()
            } ?: throw NullPointerException("Weather is null!")

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}