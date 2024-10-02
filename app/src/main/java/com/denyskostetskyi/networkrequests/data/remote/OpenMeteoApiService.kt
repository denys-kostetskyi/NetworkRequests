package com.denyskostetskyi.networkrequests.data.remote

import com.denyskostetskyi.networkrequests.data.remote.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenMeteoApiService {
    @GET("forecast?latitude={latitude}&longitude={longitude}&hourly=temperature_2m,weather_code&timezone=auto&forecast_days=1")
    suspend fun getWeatherForecast(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): WeatherForecastDto
}
