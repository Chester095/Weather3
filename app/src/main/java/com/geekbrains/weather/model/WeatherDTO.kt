package com.geekbrains.weather.model

import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    val now: Long?,
    val fact: FactDTO?,
    val forecast: ForecastDTO?
)

data class ForecastDTO(
    val sunrise: String?,
    val sunset: String?,
)

data class FactDTO(
    @SerializedName("obs_time")
    val obsTime: Long?,
    val temp: Int?,
    @SerializedName("feels_like")
    val feelsLike: Int?,
    val icon: String?,
    val condition: String?,
    val wind_speed: Double?,
    val wind_dir: String?,
    val pressure_mm: Int?,
    val pressure_pa: Int?,
    val humidity: Int?,
    val daytime: String?,
    val polar: Boolean?,
    val season: String?,
    val wind_gust: Double?
)