package com.geekbrains.weather.view.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.geekbrains.weather.R
import com.geekbrains.weather.databinding.DetailFragmentBinding
import com.geekbrains.weather.model.*

class DetailFragment : Fragment() {
    // фабричный статический метод
    companion object {
        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val listener = Repository.OnLoadListener {
        RepositoryImpl.getWeatherFromServer()?.let { weather ->
            binding.weatherCondition.text = weather.condition
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeValue.text = weather.feelsLike.toString()
            Toast.makeText(context, "Данные подгрузились", Toast.LENGTH_LONG).show()
        } ?: Toast.makeText(context, "ОШИБКА", Toast.LENGTH_LONG).show()
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

        //подпишемся на репозиторий Listener
        RepositoryImpl.addLoadedListener(listener)


        arguments?.getParcelable<Weather>(getString(R.string.weather_extra))?.let { weather: Weather ->

            binding.cityName.text = weather.city.name
            binding.cityCoordinates.text = "${weather.city.lat} ${weather.city.lon}"

            //запускаем сервисы
            requireActivity().startService(Intent(requireContext(), MainIntentService::class.java).apply {
                // кладём туда данные
                putExtra(getString(R.string.weather_extra), weather)

            })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        // отписываемся от репозиторий Listener
        RepositoryImpl.removeLoadedListener(listener)
        _binding = null
    }
}