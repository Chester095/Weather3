package com.geekbrains.weather

import com.geekbrains.weather.model.WeatherLoader
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class WeatherLoaderTest {

    @Test
    fun checkCallTimeoutMillis_ReturnsTrue() {
        assertEquals(WeatherLoader.client.callTimeoutMillis, 1000)
    }

    @Test
    fun checkConnectTimeoutMillis_ReturnsFalse() {
        assertNotEquals(WeatherLoader.client.connectTimeoutMillis, 999)
    }

    @Test
    fun checkWeatherAPI_ReturnsTrue() {
        assertNotNull(WeatherLoader.weatherAPI.toString())
    }
}