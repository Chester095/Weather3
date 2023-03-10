package com.geekbrains.weather.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.concurrent.locks.Condition

// относится к Model так как отвечает за данные (как, что, где и в каком формате)
@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val condition: String = "",
    val icon: String = "",
) : Parcelable


@Parcelize
data class City(
    val name: String = "Moscow",
    val lat: Double = 0.0,
    val lon: Double = 0.0
) : Parcelable

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)


fun getWorldCities() = listOf(
    Weather(City("Лондон", 51.5085300, -0.1257400), 2, 2),
    Weather(City("Токио", 35.6895000, 139.6917100), 2, 4),
    Weather(City("Париж", 48.8534100, 2.3488000), 2, 6),
    Weather(City("Берлин", 52.52000659999999, 13.404953999999975), 2, 8),
    Weather(City("Рим", 41.9027835, 12.496365500000024), 2, 10),
    Weather(City("Минск", 53.90453979999999, 27.561524400000053), 2, 12),
    Weather(City("Стамбул", 41.0082376, 28.97835889999999), 2, 14),
    Weather(City("Вашингтон", 38.9071923, -77.03687070000001), 2, 16),
    Weather(City("Киев", 50.4501, 30.523400000000038), 2, 18),
    Weather(City("Пекин", 39.90419989999999, 116.40739630000007), 2, 20)
)


fun getRussianCities() = listOf(
    Weather(City("Москва", 55.755826, 37.617299900000035), 5, 2),
    Weather(City("Санкт-Петербург", 59.9342802, 30.335098600000038), 2, 3),
    Weather(City("Новосибирск", 55.00835259999999, 82.93573270000002), 2, 6),
    Weather(City("Екатеринбург", 56.83892609999999, 60.60570250000001), 2, 8),
    Weather(City("Нижний Новгород", 56.2965039, 43.936059), 2, 10),
    Weather(City("Казань", 55.8304307, 49.06608060000008), 2, 12),
    Weather(City("Челябинск", 55.1644419, 61.4368432), 2, 14),
    Weather(City("Омск", 54.9884804, 73.32423610000001), 2, 16),
    Weather(City("Ростов-на-Дону", 47.2357137, 39.701505), 2, 18),
    Weather(City("Уфа", 54.7387621, 55.972055400000045), 2, 20)
)
