package com.geekbrains.weather.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.weather.R
import com.geekbrains.weather.databinding.FragmentMainBinding
import com.geekbrains.weather.model.Weather
import com.geekbrains.weather.view.*
import com.geekbrains.weather.view.details.DetailFragment
import com.geekbrains.weather.viewmodel.AppState
import com.geekbrains.weather.viewmodel.MainViewModel

class MainFragment : Fragment() {
    // фабричный статический метод
    companion object {
        fun newInstance() = MainFragment()
        const val TAG = "!!! MainFragment"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainAdapter()


    // lazy инициирует viewModel когда это будет необходимо (при первом вызове)
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, " onCreate")
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, " onViewCreated")
        initToolbar()

        // для работы RecycleView нужен адаптер, RecycleView и layoutManager
        binding.mainRecycleView.adapter = adapter
        binding.mainRecycleView.layoutManager = LinearLayoutManager(requireActivity())

        // реакция на нажатие с получением объекта weather
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

        // подписались на изменения live data (передаём жизненный цикл viewLifecycleOwner и Observer )
        // то есть когда данные изменяться вызываем перерисовку (render(state))
        viewModel.getData().observe(viewLifecycleOwner, { state -> render(state) })

        //запросили новые данные
        viewModel.getWeatherFromLocalStorageRus()

        // пока не очень красиво должно быть просто getWeatherFromLocalStorage
        // а VM должна хранить в себе значение языка 3 урок 02:45
        binding.mainFAB.setOnClickListener {
            with(viewModel) {
                getWeatherFromRemoteSource()
                isRussian = !isRussian
                when {
                    isRussian -> {
                        binding.mainFAB.setImageResource(R.drawable.ic_russia)
                    }
                    else -> {
                        binding.mainFAB.setImageResource(R.drawable.ic_baseline_outlined_flag_24)
                    }
                }
            }
        }
    }

    /*** Чтобы наш активити узнал о существовании меню.
     * Создание меню.
     * Инфлейтор заходит в notes_list_menu, пройдётся по ней
     * и для каждой создаст пункт меню и добавит в menu
     * @param menu
     * @param inflater
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d(TAG, " onCreateOptionsMenu  $menu")
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    /*** Инициализация Toolbar
     *
     */
    private fun initToolbar() {
        Log.d(TAG, " initToolbar made")
        val toolbar: Toolbar = requireView().findViewById(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
    }

    /***Реакция на нажатие кнопки меню.
     * У нас есть элемент на который нажали. Проверяем тот ли это элемент.
     * И выполняем openNoteScreen.
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            requireContext().startActivity(Intent(requireContext(), SettingsActivity::class.java))
            return true
        } else if (item.itemId == R.id.action_history) {
            requireContext().startActivity(Intent(requireContext(), HistoryActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //метод реализует реакцию на различные состояния
    private fun render(state: AppState) {
        when (state) {
            is AppState.Success<*> -> {
                // показываем список
                val weather: List<Weather> = state.data as List<Weather>
                adapter.setWeather(weather)
                binding.loadingContainer.hide()
            }
            is AppState.Error -> {
                binding.loadingContainer.show()
                binding.root.showSnackBar(state.error.message.toString(), "Попробовать снова", {
                    //запросили новые данные (список городов)
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