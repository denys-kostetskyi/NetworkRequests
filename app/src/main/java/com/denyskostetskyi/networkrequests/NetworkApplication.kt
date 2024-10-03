package com.denyskostetskyi.networkrequests

import android.app.Application
import com.denyskostetskyi.networkrequests.data.WeatherForecastMapper
import com.denyskostetskyi.networkrequests.data.remote.ktor.KtorApi
import com.denyskostetskyi.networkrequests.data.remote.retrofit.RetrofitApi
import com.denyskostetskyi.networkrequests.data.repository.KtorWeatherForecastRepository
import com.denyskostetskyi.networkrequests.data.repository.RetrofitWeatherForecastRepository
import com.denyskostetskyi.networkrequests.domain.repository.WeatherForecastRepository

class NetworkApplication : Application() {
    lateinit var retrofitWeatherForecastRepository: WeatherForecastRepository
    lateinit var ktorWeatherForecastRepository: WeatherForecastRepository

    override fun onCreate() {
        super.onCreate()
        val retrofitApiService = RetrofitApi.openMeteoApiService
        val ktorApiService = KtorApi.openMeteoApiService
        val mapper = WeatherForecastMapper()
        retrofitWeatherForecastRepository =
            RetrofitWeatherForecastRepository(retrofitApiService, mapper)
        ktorWeatherForecastRepository = KtorWeatherForecastRepository(ktorApiService, mapper)
    }
}
