package com.denyskostetskyi.networkrequests

import android.app.Application
import com.denyskostetskyi.networkrequests.data.WeatherForecastMapper
import com.denyskostetskyi.networkrequests.data.remote.retrofit.RetrofitApi
import com.denyskostetskyi.networkrequests.data.repository.RetrofitWeatherForecastRepository
import com.denyskostetskyi.networkrequests.domain.repository.WeatherForecastRepository

class NetworkApplication : Application() {
    lateinit var retrofitWeatherForecastRepository: WeatherForecastRepository

    override fun onCreate() {
        super.onCreate()
        val apiService = RetrofitApi.openMeteoApiService
        val mapper = WeatherForecastMapper()
        retrofitWeatherForecastRepository = RetrofitWeatherForecastRepository(apiService, mapper)
    }
}
