package com.denyskostetskyi.networkrequests.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherForecastDto(
    @SerializedName("hourly")
    @SerialName("hourly")
    val hourly: HourlyData,
)
