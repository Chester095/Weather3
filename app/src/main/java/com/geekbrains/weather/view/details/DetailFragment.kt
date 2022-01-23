package com.geekbrains.weather.view.details

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.geekbrains.weather.R
import com.geekbrains.weather.databinding.DetailFragmentBinding
import com.geekbrains.weather.model.MainIntentService
import com.geekbrains.weather.model.Repository
import com.geekbrains.weather.model.RepositoryImpl
import com.geekbrains.weather.model.Weather
import com.geekbrains.weather.viewmodel.DetailViewModel

class DetailFragment : Fragment() {
    // фабричный статический метод
    companion object {
        val TAG = "DetailFragment"
        const val TEST_BROADCAST_INTENT_FILTER = "TEST BROADCAST INTENT FILTER"
        const val THREADS_FRAGMENT_BROADCAST_EXTRA = "THREADS_FRAGMENT_EXTRA"
        fun newInstance(bundle: Bundle): DetailFragment {
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }


    private val listener = Repository.OnLoadListener {


        Log.d(
            TAG, "RepositoryImpl.getWeatherFromServer()  "
                    + RepositoryImpl.getWeatherFromServer()?.condition
        )
        RepositoryImpl.getWeatherFromServer()?.let { weather ->
            binding.weatherCondition.text = weather.condition
            binding.temperatureValue.text = weather.temperature.toString()
            binding.feelsLikeValue.text = weather.feelsLike.toString()

            Log.d(TAG, "https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
            val request = ImageRequest.Builder(requireContext())
                // указываем от куда будем загружать
                .data("https://yastatic.net/weather/i/icons/funky/dark/${weather.icon}.svg")
                // и куда
                .target(binding.weatherImageView)
                .build()

            ImageLoader.Builder(requireContext())
                .componentRegistry { add(SvgDecoder(requireContext())) }
                .build()
                .enqueue(request)

            viewModel.saveHistory(weather)



            Toast.makeText(context, "Данные подгрузились", Toast.LENGTH_LONG).show()
        } ?: Toast.makeText(context, "ОШИБКА DetailFragment: listener", Toast.LENGTH_LONG).show()
    }

    private val testReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //Достаём данные из интента
            intent.getStringExtra(THREADS_FRAGMENT_BROADCAST_EXTRA)?.let {
                Log.d(
                    TAG, "testReceiver:  "
                            + intent.getStringExtra(THREADS_FRAGMENT_BROADCAST_EXTRA)
                )
            }
        }
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(testReceiver, IntentFilter(TEST_BROADCAST_INTENT_FILTER))
            Log.d(
                TAG, "onCreateView:  "
                        + LocalBroadcastManager.getInstance(it).toString()
            )
        }

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
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(testReceiver)
        }
        _binding = null
    }
}