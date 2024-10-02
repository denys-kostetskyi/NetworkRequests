package com.denyskostetskyi.networkrequests.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HourlyData(
    @SerializedName("temperature_2m")
    val temperatureArray: DoubleArray,

    @SerializedName("weather_code")
    val weatherCodeArray: IntArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HourlyData

        if (!temperatureArray.contentEquals(other.temperatureArray)) return false
        if (!weatherCodeArray.contentEquals(other.weatherCodeArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = temperatureArray.contentHashCode()
        result = 31 * result + weatherCodeArray.contentHashCode()
        return result
    }
}