package com.denyskostetskyi.networkrequests.data.remote.retrofit

import com.denyskostetskyi.networkrequests.data.remote.ApiConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(ApiConstants.BASE_URL)
    .build()

object RetrofitApi {
    val openMeteoApiService: OpenMeteoApiService by lazy {
        retrofit.create(OpenMeteoApiService::class.java)
    }
}
