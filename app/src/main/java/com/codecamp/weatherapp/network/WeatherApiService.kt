package com.codecamp.weatherapp.network

import com.codecamp.weatherapp.BuildConfig
import com.codecamp.weatherapp.model.DeserializedWeather
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Makes the request to the weather server
 */
interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): DeserializedWeather
}
