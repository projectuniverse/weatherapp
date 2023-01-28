package com.codecamp.weatherapp.ui.screens

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.codecamp.weatherapp.WeatherApplication
import com.codecamp.weatherapp.data.WeatherRepository
import com.codecamp.weatherapp.model.Weather
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface WeatherUiState {
    data class Success(val weather: Weather) : WeatherUiState
    data class Error(val weather: Weather?) : WeatherUiState
    object Loading : WeatherUiState
}

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set

    /*
    init {
        getWeather(fusedLocationClient)
    }
    */

    //TODO: Sollte private sein oder nicht? Nur einmal aufrufen bei init?
    //TODO: Änder der Parameter den du getWeather übergibst zur tatsächlichen location
    @SuppressLint("MissingPermission")
    fun getWeather(
        fusedLocationClient: FusedLocationProviderClient
    ) {
        viewModelScope.launch {
            weatherUiState = try {
                var currentLocation: Location = Location("")
                fusedLocationClient
                    .getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location: Location? ->
                        if (location == null) {
                            throw IOException()
                        }
                        else {
                            currentLocation.latitude = location.latitude
                            currentLocation.longitude = location.longitude
                        }

                    }
                val result = weatherRepository.getOnlineWeather(
                    51.31,
                    9.48
                    /*
                    currentLocation.latitude,
                    currentLocation.longitude
                     */
                )
                WeatherUiState.Success(result)
            } catch (e: IOException) {
                setErrorSate()
            } catch (e: HttpException) {
                setErrorSate()
            }
        }
    }

    private suspend fun setErrorSate(): WeatherUiState.Error {
        if (!upToDateOfflineWeatherExists()) {
            return WeatherUiState.Error(null)
        }
        return WeatherUiState.Error(weatherRepository.getOfflineWeather())
    }

    /*
     * Returns true if offline weather exists which is not older than 1h.
     * Otherwise, returns false.
     */
    private suspend fun upToDateOfflineWeatherExists() : Boolean {
        //Current Unix time
        val currentTime = System.currentTimeMillis() / 1000
        val weather = weatherRepository.getOfflineWeather()
        if (weather == null) {
            return false
        }
        val difference = (currentTime - weather.time) / 60.0
        return difference <= 60.0
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as WeatherApplication)
                val weatherRepository = application.container.weatherRepository
                WeatherViewModel(
                    weatherRepository = weatherRepository
                )
            }
        }
    }
}