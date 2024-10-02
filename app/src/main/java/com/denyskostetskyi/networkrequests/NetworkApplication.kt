package com.denyskostetskyi.networkrequests

import android.app.Application
import com.denyskostetskyi.networkrequests.data.WeatherForecastMapper
import com.denyskostetskyi.networkrequests.data.remote.OpenMeteoApiService
import com.denyskostetskyi.networkrequests.data.repository.RetrofitWeatherForecastRepository
import com.denyskostetskyi.networkrequests.domain.repository.WeatherForecastRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkApplication : Application() {
    lateinit var retrofitWeatherForecastRepository: WeatherForecastRepository

    override fun onCreate() {
        super.onCreate()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        val apiService = retrofit.create(OpenMeteoApiService::class.java)
        val mapper = WeatherForecastMapper()
        retrofitWeatherForecastRepository = RetrofitWeatherForecastRepository(apiService, mapper)
    }

    companion object {
        private const val BASE_URL = "https://api.open-meteo.com/v1/"
    }
}
