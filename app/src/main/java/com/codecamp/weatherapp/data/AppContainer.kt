package com.codecamp.weatherapp.data

import android.content.Context
import com.codecamp.weatherapp.network.WeatherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

// App Container for dependency injection
interface AppContainer {
    val weatherRepository: WeatherRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val BASE_URL = "https://api.openweathermap.org"

    @kotlinx.serialization.ExperimentalSerializationApi
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory(MediaType.get("application/json")))
        .baseUrl(BASE_URL)
        .build()

    @kotlinx.serialization.ExperimentalSerializationApi
    private val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    @kotlinx.serialization.ExperimentalSerializationApi
    override val weatherRepository: WeatherRepository by lazy {
        DefaultWeatherRepository(
            retrofitService,
            WeatherDatabase.getDatabase(context).weatherDao()
        )
    }
}