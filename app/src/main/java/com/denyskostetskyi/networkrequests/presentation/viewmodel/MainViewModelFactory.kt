package com.denyskostetskyi.networkrequests.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.denyskostetskyi.networkrequests.domain.repository.WeatherForecastRepository

class MainViewModelFactory(
    private val retrofitRepository: WeatherForecastRepository,
    private val ktorRepository: WeatherForecastRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(retrofitRepository, ktorRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
