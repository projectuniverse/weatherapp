package com.codecamp.weatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData

private val DarkColorPalette = darkColors(
    background = NavyBlue,
    surface = Cyan700,
    onSurface = White,
    primary = Grey900,
    onPrimary = White,
    secondary = Grey100
)

private val LightColorPalette = lightColors(
    background = LightBlue100,
    surface = LightBlue50,
    onSurface = Grey900,
    primary = Grey50,
    onPrimary = Grey900,
    secondary = Grey700
)

object ThemeState {
    var firstInit by mutableStateOf(true)
    var systemInDarkMode by mutableStateOf(false)
    var appInDarkMode by mutableStateOf(false)
    var isMetric by mutableStateOf(true)
}

@Composable
fun WeatherAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    if (ThemeState.firstInit && darkTheme) {
        ThemeState.systemInDarkMode = true
        ThemeState.appInDarkMode = true
        ThemeState.firstInit = false
    }
    // System switched from dark mode to light mode or from light mode to dark mode
    // This should overwrite the app settings
    if (ThemeState.systemInDarkMode != isSystemInDarkTheme()) {
        ThemeState.systemInDarkMode = !ThemeState.systemInDarkMode
        if (ThemeState.appInDarkMode != isSystemInDarkTheme()) {
            ThemeState.appInDarkMode = !ThemeState.appInDarkMode
        }
    }

    val colors = if (ThemeState.appInDarkMode) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}