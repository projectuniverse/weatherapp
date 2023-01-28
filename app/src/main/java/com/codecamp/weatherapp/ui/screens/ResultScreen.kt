package com.codecamp.weatherapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.weatherapp.R
import com.codecamp.weatherapp.model.Weather
import com.codecamp.weatherapp.ui.theme.ThemeState
import java.math.RoundingMode

@Composable
fun ResultScreen(
    weather: Weather
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier.weight(20.0f)
        )
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(80.0f)
        ) {
            Text(
                text = weather.cityName,
                color = MaterialTheme.colors.onSurface,
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.size(20.dp)
            )
            val temperature = when (ThemeState.isMetric) {
                true -> {
                    val temp = (weather.temperature - 273.15).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                    "$temp ℃"
                }
                else -> {
                    val temp = ((weather.temperature - 273.15) * (9.0/5.0) + 32).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                    "$temp ℉"
                }
            }
            Text(
                text = temperature,
                color = MaterialTheme.colors.onSurface,
                fontSize = 36.sp
            )
            Spacer(
                modifier = Modifier.size(5.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val weatherImage = when (weather.weatherType) {
                    "Clear" -> {
                        if (weather.time >= weather.sunrise && weather.time < weather.sunset) {
                            painterResource(id = R.drawable.sunny)
                        } else {
                            painterResource(id = R.drawable.moon)
                        }
                    }
                    "Clouds" -> painterResource(id = R.drawable.cloudy)
                    "Rain" -> painterResource(id = R.drawable.rainy)
                    "Drizzle" -> {
                        if (weather.time >= weather.sunrise && weather.time < weather.sunset) {
                            painterResource(id = R.drawable.partly_cloudy_day)
                        } else {
                            painterResource(id = R.drawable.partly_cloudy_night)
                        }
                    }
                    "Snow" -> painterResource(id = R.drawable.snowing)
                    "Thunderstorm" -> painterResource(id = R.drawable.thunderstorm)
                    else -> painterResource(id = R.drawable.foggy)
                }
                Image(
                    painter = weatherImage,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                    modifier = Modifier.size(45.dp)
                )
                Spacer(
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = weather.weatherType,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 30.sp,
                )
            }
            Spacer(
                modifier = Modifier.size(5.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter =  painterResource(id = R.drawable.windblowing),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface),
                    modifier = Modifier.size(45.dp)
                )
                Spacer(
                    modifier = Modifier.size(15.dp)
                )
                val windSpeed = when (ThemeState.isMetric) {
                    true -> {
                        val speed = (weather.windSpeed * 3.6).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                        "$speed km/h"
                    }
                    else -> {
                        val speed = (weather.windSpeed * 2.237).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
                        "$speed mph"
                    }
                }
                Text(
                    text = windSpeed,
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 30.sp,
                )
            }
        }
    }
}