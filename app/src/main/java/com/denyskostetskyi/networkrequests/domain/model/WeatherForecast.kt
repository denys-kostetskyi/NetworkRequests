package com.denyskostetskyi.networkrequests.domain.model

data class WeatherForecast(
    val temperature: Int,
    val weather: Weather,
    val location: Location,
)
