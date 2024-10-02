package com.denyskostetskyi.networkrequests.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherForecastDto(
    @SerializedName("hourly")
    val hourly: HourlyData,
)
