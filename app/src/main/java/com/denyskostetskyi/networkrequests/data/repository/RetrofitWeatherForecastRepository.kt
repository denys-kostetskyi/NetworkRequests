package com.denyskostetskyi.networkrequests.data.repository

import com.denyskostetskyi.networkrequests.data.WeatherForecastMapper
import com.denyskostetskyi.networkrequests.data.remote.retrofit.OpenMeteoApiService
import com.denyskostetskyi.networkrequests.domain.model.Location
import com.denyskostetskyi.networkrequests.domain.model.WeatherForecast
import com.denyskostetskyi.networkrequests.domain.repository.WeatherForecastRepository

class RetrofitWeatherForecastRepository(
    private val apiService: OpenMeteoApiService,
    private val mapper: WeatherForecastMapper,
) : WeatherForecastRepository {
    override suspend fun getWeatherForecast(location: Location): Result<WeatherForecast> {
        return try {
            val dto = apiService.getWeatherForecast(location.latitude, location.longitude)
            val weatherForecast = mapper.mapDtoToEntity(dto, location)
            Result.success(weatherForecast)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun downloadWeatherForecastFile(location: Location): Result<ByteArray> {
        return try {
            val response = apiService.getWeatherForecastFile(location.latitude, location.longitude)
            Result.success(response.bytes())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
