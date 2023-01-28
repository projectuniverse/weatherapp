package com.codecamp.weatherapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WeatherScreen(
    weatherUiState: WeatherUiState,
    modifier: Modifier = Modifier
) {
    when (weatherUiState) {
        is WeatherUiState.Success -> ResultScreen(
            weather = weatherUiState.weather
        )
        is WeatherUiState.Loading -> LoadingScreen(modifier)
        is WeatherUiState.Error -> {
            if (weatherUiState.weather == null) {
                NoConnectionScreen()
            }
            else {
                // Show old weather data
                ResultScreen(
                    weather = weatherUiState.weather
                )
            }
        }
    }
}