package com.geekbrains.weather.model

import android.os.Bundle
import android.util.Log
import android.view.View
import com.geekbrains.weather.databinding.DetailFragmentBinding
import com.geekbrains.weather.view.showSnackBar

class WeatherServer(view: View, savedInstanceState: Bundle?) {
    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    arguments?.getParcelable<Weather>("WEATHER_EXTRA")?.let { weather: Weather ->

        binding.cityName.text = weather.city.name
        binding.cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"


        WeatherLoader.load(weather.city, object : WeatherLoader.OnWeatherLoadListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                weatherDTO.fact?.let { fact ->
                    binding.weatherCondition.text = fact.condition
                    binding.temperatureValue.text = fact.temp?.toString()
                    binding.feelsLikeValue.text = fact.feelsLike?.toString()
                }
            }

            override fun onFailed(throwable: Throwable) {
                Log.e("DEBUGLOG", throwable.message.toString())
                binding.root.showSnackBar("Сбой загрузки данных", "Попробовать снова", {
                    //запросили новые данные
                    WeatherLoader.load(weather.city, this )
                })
//                    Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}