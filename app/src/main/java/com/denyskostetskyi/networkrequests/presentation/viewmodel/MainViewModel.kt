package com.denyskostetskyi.networkrequests.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denyskostetskyi.networkrequests.domain.model.Location
import com.denyskostetskyi.networkrequests.domain.repository.WeatherForecastRepository
import com.denyskostetskyi.networkrequests.presentation.state.WeatherForecastUiState
import kotlinx.coroutines.launch

class MainViewModel(
    private val retrofitRepository: WeatherForecastRepository,
    private val ktorRepository: WeatherForecastRepository,
) : ViewModel() {
    private val _weatherForecast = MutableLiveData<WeatherForecastUiState>()
    val weatherForecast: LiveData<WeatherForecastUiState> = _weatherForecast

    fun fetchWeatherForecastRetrofit(location: Location) {
        fetchWeatherForecast(location, retrofitRepository)
    }

    fun fetchWeatherForecastKtor(location: Location) {
        fetchWeatherForecast(location, ktorRepository)
    }

    private fun fetchWeatherForecast(location: Location, repository: WeatherForecastRepository) {
        viewModelScope.launch {
            _weatherForecast.value = WeatherForecastUiState.Loading
            val result = repository.getWeatherForecast(location)
            if (result.isSuccess) {
                _weatherForecast.value = WeatherForecastUiState.Success(result.getOrNull())
            } else {
                _weatherForecast.value = WeatherForecastUiState.Error(result.exceptionOrNull())
            }
        }
    }
}
