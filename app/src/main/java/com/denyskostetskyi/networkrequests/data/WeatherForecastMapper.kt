package com.denyskostetskyi.networkrequests.data

import com.denyskostetskyi.networkrequests.data.remote.dto.WeatherForecastDto
import com.denyskostetskyi.networkrequests.domain.model.Location
import com.denyskostetskyi.networkrequests.domain.model.Weather
import com.denyskostetskyi.networkrequests.domain.model.WeatherForecast

class WeatherForecastMapper {
    fun mapDtoToEntity(dto: WeatherForecastDto, location: Location): WeatherForecast {
        val weatherList = getWeatherListFromCodes(dto.hourly.weatherCodeArray)
        val temperatureList = dto.hourly.temperatureArray.toList()
        return WeatherForecast(
            temperatureList = temperatureList,
            weatherList = weatherList,
            location = location
        )
    }

    private fun getWeatherListFromCodes(codes: IntArray): List<Weather> {
        return codes.map(::getWeatherFromCode)
    }

    // https://open-meteo.com/en/docs
    private fun getWeatherFromCode(code: Int): Weather {
        return when (code) {
            0 -> Weather.CLEAR
            in 1..3 -> Weather.CLOUDY
            45, 48 -> Weather.FOG
            in 51..57, in 61..67, in 80..82 -> Weather.RAIN
            in 71..75, 77, 85, 86 -> Weather.SNOW
            95, 96, 99 -> Weather.THUNDERSTORM
            else -> Weather.UNKNOWN
        }
    }
}
