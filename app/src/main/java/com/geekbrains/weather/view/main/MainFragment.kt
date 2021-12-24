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
import com.geekbrains.weather.viewmodel.AppState
import com.geekbrains.weather.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    companion
    object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainAdapter()

    private var isRussian = true

    private lateinit var viewModel: MainViewModel

/*    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(weather: Weather) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(DetailFragment.BUNDLE_EXTRA, weather)
                manager.beginTransaction()
                    .add(R.id.container, DetailFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })
    private var isDataSetRus: Boolean = true*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRecycleView.adapter = adapter

        binding.mainRecycleView.layoutManager = LinearLayoutManager(requireActivity())


        adapter.listener = MainAdapter.OnItemClick { weather ->
            val bundle = Bundle()
            bundle.putParcelable("WEATHER_EXTRA", weather)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, DetailFragment.newInstance(bundle))
                .addToBackStack("")
                .commit()
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // подписались на изменения live data
        viewModel.getData().observe(viewLifecycleOwner, { state -> render(state) })

        //запросили новые данные
        viewModel.getWeatherFromLocalStorageRus()

        binding.mainFAB.setOnClickListener {
            isRussian = !isRussian
            if (isRussian) {
                viewModel.getWeatherFromLocalStorageRus()
                binding.mainFAB.setImageResource(R.drawable.ic_russia)
            } else {
                viewModel.getWeatherFromLocalStorageWorld()
                binding.mainFAB.setImageResource(R.drawable.ic_baseline_outlined_flag_24)
            }
        }
    }


    private fun render(state: AppState) {

        when (state) {
            is AppState.Success<*> -> {
                val weather: List<Weather> = state.data as List<Weather>
                adapter.setWeather(weather)
                binding.loadingContainer.visibility = View.GONE
            }
            is AppState.Error -> {
                binding.loadingContainer.visibility = View.VISIBLE
                Snackbar.make(
                    binding.root,
                    state.error.message.toString(),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction("Попробовать снова") {
                        //запросили новые данные
                        viewModel.getWeatherFromLocalStorageRus()
                    }.show()
            }
            is AppState.Loading -> {

            }
        }
    }

/*    interface OnItemViewClickListener {
        fun onItemViewClick(weather: Weather)
    }*/

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}