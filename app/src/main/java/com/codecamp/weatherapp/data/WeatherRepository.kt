package com.codecamp.weatherapp.data

import com.codecamp.weatherapp.model.Weather
import com.codecamp.weatherapp.network.WeatherApiService
import kotlinx.coroutines.flow.first

interface WeatherRepository {
    suspend fun getOnlineWeather(
        latitude: Double,
        longitude: Double
    ): Weather
    suspend fun getOfflineWeather(): Weather?
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultWeatherRepository(
    private val weatherApiService: WeatherApiService,
    private val weatherDao: WeatherDao
    ) : WeatherRepository {

    override suspend fun getOnlineWeather(
        latitude: Double,
        longitude: Double
    ): Weather {
        val deserializedWeather = weatherApiService.getWeather(latitude, longitude)
        val weather = Weather(
            weatherType = deserializedWeather.weather[0].main,
            temperature = deserializedWeather.main.temp,
            windSpeed = deserializedWeather.wind.speed,
            time = deserializedWeather.dt,
            sunrise = deserializedWeather.sys.sunrise,
            sunset = deserializedWeather.sys.sunset,
            cityName = deserializedWeather.name
        )
        if (!weatherDao.weatherExists().first()) {
            weatherDao.insert(weather)
        }
        else {
            weatherDao.update(weather)
        }
        return weather
    }

    override suspend fun getOfflineWeather(): Weather? {
        if (weatherDao.weatherExists().first()) {
            return weatherDao.getWeather(0).first()
        }
        return null
    }
}