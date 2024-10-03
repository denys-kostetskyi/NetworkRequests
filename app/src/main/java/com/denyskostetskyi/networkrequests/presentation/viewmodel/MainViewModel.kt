package com.denyskostetskyi.networkrequests.presentation.viewmodel

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.denyskostetskyi.networkrequests.domain.model.Location
import com.denyskostetskyi.networkrequests.domain.repository.WeatherForecastRepository
import com.denyskostetskyi.networkrequests.presentation.state.WeatherForecastUiState
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    private val retrofitRepository: WeatherForecastRepository,
    private val ktorRepository: WeatherForecastRepository,
) : ViewModel() {
    private val _uiState = MutableLiveData<WeatherForecastUiState>()
    val uiState: LiveData<WeatherForecastUiState> = _uiState

    fun fetchWeatherForecastRetrofit(location: Location) {
        fetchWeatherForecast(location, retrofitRepository)
    }

    fun fetchWeatherForecastKtor(location: Location) {
        fetchWeatherForecast(location, ktorRepository)
    }

    private fun fetchWeatherForecast(location: Location, repository: WeatherForecastRepository) {
        viewModelScope.launch {
            _uiState.value = WeatherForecastUiState.Loading
            val result = repository.getWeatherForecast(location)
            if (result.isSuccess) {
                _uiState.value =
                    WeatherForecastUiState.Success(result.getOrNull().toString())
            } else {
                _uiState.value = WeatherForecastUiState.Error(result.exceptionOrNull())
            }
        }
    }

    fun downloadWeatherForecastFileRetrofit(
        location: Location,
        resolver: ContentResolver,
        destination: Uri
    ) {
        downloadWeatherForecastFile(location, resolver, destination, retrofitRepository)
    }

    fun downloadWeatherForecastFileKtor(
        location: Location,
        resolver: ContentResolver,
        destination: Uri
    ) {
        downloadWeatherForecastFile(location, resolver, destination, ktorRepository)
    }

    private fun downloadWeatherForecastFile(
        location: Location,
        resolver: ContentResolver,
        destination: Uri,
        repository: WeatherForecastRepository
    ) {
        viewModelScope.launch {
            _uiState.value = WeatherForecastUiState.Loading
            val result = repository.downloadWeatherForecastFile(location)
            if (result.isSuccess) {
                try {
                    resolver.openOutputStream(destination)?.use { output ->
                        output.write(result.getOrNull())
                    }
                    _uiState.value =
                        WeatherForecastUiState.Success(destination.path.toString())
                } catch (e: IOException) {
                    _uiState.value = WeatherForecastUiState.Error(e)
                }
            }
        }
    }
}
