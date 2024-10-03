package com.denyskostetskyi.networkrequests.data.remote.ktor

import com.denyskostetskyi.networkrequests.data.remote.ApiConstants
import com.denyskostetskyi.networkrequests.data.remote.dto.WeatherForecastDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class OpenMeteoApiService(private val client: HttpClient) {
    suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double,
        hourly: String = ApiConstants.DEFAULT_HOURLY,
        timezone: String = ApiConstants.DEFAULT_TIMEZONE,
        forecastDays: Int = ApiConstants.DEFAULT_FORECAST_DAYS
    ): WeatherForecastDto {
        return client.get("${ApiConstants.BASE_URL}${ApiConstants.ENDPOINT_FORECAST}") {
            url {
                parameters.append(ApiConstants.PARAM_LATITUDE, latitude.toString())
                parameters.append(ApiConstants.PARAM_LONGITUDE, longitude.toString())
                parameters.append(ApiConstants.PARAM_HOURLY, hourly)
                parameters.append(ApiConstants.PARAM_TIMEZONE, timezone)
                parameters.append(ApiConstants.PARAM_FORECAST_DAYS, forecastDays.toString())
            }
        }.body()
    }
}
