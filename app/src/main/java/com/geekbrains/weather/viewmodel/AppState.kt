package com.geekbrains.weather.viewmodel

// создаём силв класс, так как он может хранить разные типы
// в данном случае для создание рекации на различные состояния
// относится к VM так как является оберткой для данных и отвечает за то что и когда отображаем
sealed class AppState {
    data class Success<T>(val data: T) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}