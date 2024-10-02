package com.denyskostetskyi.networkrequests.domain.model

data class Location(
    val name: String,
    val latitude: Double,
    val longitude: Double,
) {
    companion object {
        val LVIV = Location(
            name = "Lviv",
            latitude = 49.8397,
            longitude = 24.0297
        )
    }
}
