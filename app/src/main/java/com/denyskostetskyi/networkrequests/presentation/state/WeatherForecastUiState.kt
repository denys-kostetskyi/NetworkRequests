package com.denyskostetskyi.networkrequests.presentation.state

import com.denyskostetskyi.networkrequests.domain.model.WeatherForecast

sealed interface WeatherForecastUiState {
    data class Success(val weatherForecast: WeatherForecast?) : WeatherForecastUiState
    data object Error : WeatherForecastUiState
    data object Loading : WeatherForecastUiState
}
