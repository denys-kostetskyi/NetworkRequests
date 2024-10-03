package com.denyskostetskyi.networkrequests.domain.repository

import com.denyskostetskyi.networkrequests.domain.model.Location
import com.denyskostetskyi.networkrequests.domain.model.WeatherForecast

interface WeatherForecastRepository {
    suspend fun getWeatherForecast(location: Location): Result<WeatherForecast>

    suspend fun downloadWeatherForecastFile(
        location: Location,
        destinationFilepath: String
    ): Result<Unit>
}
