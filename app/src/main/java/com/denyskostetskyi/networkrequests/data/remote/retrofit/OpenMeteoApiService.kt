package com.denyskostetskyi.networkrequests.data.remote.retrofit

import com.denyskostetskyi.networkrequests.data.remote.ApiConstants
import com.denyskostetskyi.networkrequests.data.remote.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApiService {
    @GET(ApiConstants.ENDPOINT_FORECAST)
    suspend fun getWeatherForecast(
        @Query(ApiConstants.PARAM_LATITUDE) latitude: Double,
        @Query(ApiConstants.PARAM_LONGITUDE) longitude: Double,
        @Query(ApiConstants.PARAM_HOURLY) hourly: String = ApiConstants.DEFAULT_HOURLY,
        @Query(ApiConstants.PARAM_TIMEZONE) timezone: String = ApiConstants.DEFAULT_TIMEZONE,
        @Query(ApiConstants.PARAM_FORECAST_DAYS) forecastDays: Int = ApiConstants.DEFAULT_FORECAST_DAYS
    ): WeatherForecastDto
}
