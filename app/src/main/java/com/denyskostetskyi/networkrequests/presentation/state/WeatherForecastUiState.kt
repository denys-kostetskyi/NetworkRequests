package com.denyskostetskyi.networkrequests.presentation.state

sealed interface WeatherForecastUiState {
    data class Success(val result: String) : WeatherForecastUiState
    data class Error(val error: Throwable?) : WeatherForecastUiState
    data object Loading : WeatherForecastUiState
}
