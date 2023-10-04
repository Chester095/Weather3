package com.geekbrains.weather

import com.geekbrains.weather.model.getDefaultCity
import com.geekbrains.weather.viewmodel.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherTest {

    @Test
    fun checkMainViewModel_ReturnsTrue() {
        assertTrue(MainViewModel().isRussian)
    }

    @Test
    fun checkDefaultCityName_ReturnsTrue() {
        assertEquals(getDefaultCity().name, "Москва")
    }

    @Test
    fun checkDefaultLat_ReturnsFalse() {
        assertNotEquals(getDefaultCity().lat, 55.75582)
    }

}