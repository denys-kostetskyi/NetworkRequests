package com.denyskostetskyi.networkrequests.data.repository

import com.denyskostetskyi.networkrequests.data.WeatherForecastMapper
import com.denyskostetskyi.networkrequests.data.remote.retrofit.OpenMeteoApiService
import com.denyskostetskyi.networkrequests.domain.model.Location
import com.denyskostetskyi.networkrequests.domain.model.WeatherForecast
import com.denyskostetskyi.networkrequests.domain.repository.WeatherForecastRepository
import java.io.File

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

    override suspend fun downloadWeatherForecastFile(
        location: Location,
        destinationFilepath: String
    ): Result<Unit> {
        return try {
            val responseBody =
                apiService.getWeatherForecastFile(location.latitude, location.longitude)
            val file = File(destinationFilepath)
            responseBody.byteStream().use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
