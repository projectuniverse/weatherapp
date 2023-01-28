package com.codecamp.weatherapp.network

import com.codecamp.weatherapp.model.DeserializedWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = "Insert your API key"
    ): DeserializedWeather
}
