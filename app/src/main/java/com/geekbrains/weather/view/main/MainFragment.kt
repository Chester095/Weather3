package com.geekbrains.weather.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.weather.R
import com.geekbrains.weather.databinding.MainFragmentBinding
import com.geekbrains.weather.model.Weather
import com.geekbrains.weather.view.details.DetailFragment
import com.geekbrains.weather.view.hide
import com.geekbrains.weather.view.show
import com.geekbrains.weather.view.showSnackBar
import com.geekbrains.weather.viewmodel.AppState
import com.geekbrains.weather.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion
    object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainAdapter()

    private var isRussian = true


    // lazy инициирует viewModel когда это будет необходимо (при первом вызове)
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRecycleView.adapter = adapter

        binding.mainRecycleView.layoutManager = LinearLayoutManager(requireActivity())

        adapter.listener = MainAdapter.OnItemClick { weather ->

            // apply сразу производит операцию над объектом
            // also же работает с копией объекта
            val bundle = Bundle().apply { putParcelable("WEATHER_EXTRA", weather) }

            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commit()
            }
        }

        // подписались на изменения live data
        viewModel.getData().observe(viewLifecycleOwner, { state -> render(state) })

        //запросили новые данные
        viewModel.getWeatherFromLocalStorageRus()

        binding.mainFAB.setOnClickListener {
            isRussian = !isRussian
            when {
                isRussian -> {
                    viewModel.getWeatherFromLocalStorageRus()
                    binding.mainFAB.setImageResource(R.drawable.ic_russia)
                }
                else -> {
                    viewModel.getWeatherFromLocalStorageWorld()
                    binding.mainFAB.setImageResource(R.drawable.ic_baseline_outlined_flag_24)
                }
            }
        }
    }


    private fun render(state: AppState) {
        when (state) {
            is AppState.Success<*> -> {
                val weather: List<Weather> = state.data as List<Weather>
                adapter.setWeather(weather)
                binding.loadingContainer.hide()
            }
            is AppState.Error -> {
                binding.loadingContainer.show()
                binding.root.showSnackBar(state.error.message.toString(), "Попробовать снова", {
                    //запросили новые данные
                    viewModel.getWeatherFromLocalStorageRus()
                })
            }
            is AppState.Loading -> {
                binding.loadingContainer.show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}