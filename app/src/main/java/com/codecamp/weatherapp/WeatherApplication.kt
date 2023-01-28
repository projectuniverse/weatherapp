package com.codecamp.weatherapp

import android.app.Application
import com.codecamp.weatherapp.data.AppContainer
import com.codecamp.weatherapp.data.AppDataContainer

class WeatherApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}