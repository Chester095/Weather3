package com.geekbrains.weather.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.weather.R
import com.geekbrains.weather.model.Weather

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    //данные которые будут в списке лежать
    private var weatherData: List<Weather> = listOf()
    var listener: OnItemClick? = null


    //когда будет дата передаваться
    fun setWeather(data: List<Weather>) {
        weatherData = data
        notifyDataSetChanged()
    }

    // инфлейтим наш ВьюХолдер
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.main_item, parent, false) /*as View*/
        )
    }

    // получаем по позиции
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    override fun getItemCount(): Int = weatherData.size

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // по погоде будем заполнять элемент списка
        fun bind(weather: Weather) {

            itemView.apply {
                findViewById<TextView>(R.id.main_city_name_text_view).text = weather.city.name

                //когда наш Айтем вью будут кликать
                setOnClickListener {
                    listener?.onClick(weather)
                }
            }

        }
    }

    //функциональный интерфейс
    fun interface OnItemClick {
        //чтобы weather была parcelable
        fun onClick(weather: Weather)
    }
}