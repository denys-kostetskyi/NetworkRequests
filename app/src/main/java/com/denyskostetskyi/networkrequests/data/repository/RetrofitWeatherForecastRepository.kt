package com.denyskostetskyi.networkrequests.data.repository

import com.denyskostetskyi.networkrequests.data.WeatherForecastMapper
import com.denyskostetskyi.networkrequests.data.remote.retrofit.OpenMeteoApiService
import com.denyskostetskyi.networkrequests.domain.model.Location
import com.denyskostetskyi.networkrequests.domain.model.WeatherForecast
import com.denyskostetskyi.networkrequests.domain.repository.WeatherForecastRepository
import java.io.IOException

class RetrofitWeatherForecastRepository(
    private val apiService: OpenMeteoApiService,
    private val mapper: WeatherForecastMapper,
) : WeatherForecastRepository {
    override suspend fun getWeatherForecast(location: Location): Result<WeatherForecast> {
        try {
            val dto = apiService.getWeatherForecast(location.latitude, location.longitude)
            val weatherForecast = mapper.mapDtoToEntity(dto, location)
            return Result.success(weatherForecast)
        } catch (e: IOException) {
            return Result.failure(e)
        }
    }
}
