package com.codecamp.weatherapp.ui.screens

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
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

    val succededLocation: MutableLiveData<Location> by lazy{ MutableLiveData<Location>() }

    @SuppressLint("MissingPermission")
    fun getLocation(
        fusedLocationClient: FusedLocationProviderClient
    ) {
        val currentLocation = Location("")
        fusedLocationClient
            .getCurrentLocation(LocationRequest.QUALITY_BALANCED_POWER_ACCURACY, null)
            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    throw IOException()
                }
                else {
                    currentLocation.latitude = location.latitude
                    currentLocation.longitude = location.longitude
                }

                succededLocation.value = currentLocation

            }
    }

    fun getDefaultLocation(): Location {
        val loc = Location("")
        loc.latitude = 0.0
        loc.longitude = 0.0
        return loc
    }


    fun getWeather(currentLocation: Location) {
        viewModelScope.launch {
            if(currentLocation.latitude == 0.0 && currentLocation.longitude == 0.0) {
                setErrorSate()
            } else {
                weatherUiState = try {
                    val result = weatherRepository.getOnlineWeather(
                        currentLocation.latitude,
                        currentLocation.longitude
                    )
                    WeatherUiState.Success(result)
                } catch (e: IOException) {
                    setErrorSate()
                } catch (e: HttpException) {
                    setErrorSate()
                }
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