package com.denyskostetskyi.networkrequests.domain.model

data class WeatherForecast(
    val temperatureList: List<Double>,
    val weatherList: List<Weather>,
    val location: Location,
)
