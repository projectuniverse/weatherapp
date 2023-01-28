package com.codecamp.weatherapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codecamp.weatherapp.R
import com.codecamp.weatherapp.ui.screens.SensorsScreen
import com.codecamp.weatherapp.ui.screens.WeatherScreen
import com.codecamp.weatherapp.ui.screens.WeatherViewModel
import com.codecamp.weatherapp.ui.theme.ThemeState

enum class AppScreen(@StringRes val title: Int) {
    Weather(title = R.string.weather),
    Sensors(title = R.string.sensors)
}

@Composable
fun WeatherApp(
    weatherViewModel: WeatherViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Weather.name
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarTop(
                currentScreen = currentScreen
            )
        },
        bottomBar = {
            AppBarBottom(
                currentScreen = currentScreen,
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Weather.name
        ) {
            composable(route = AppScreen.Weather.name) {
                WeatherScreen(
                    weatherViewModel.weatherUiState
                )
            }
            composable(route = AppScreen.Sensors.name) {
                SensorsScreen()
            }
        }
    }
}

@Composable
fun AppBarTop(
    currentScreen: AppScreen
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(currentScreen.title),
            fontSize = 40.sp,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.h1,
        )
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            val unitText = when (ThemeState.isMetric) {
                true -> stringResource(R.string.metric)
                else -> stringResource(R.string.imperial)
            }
            Text(
                text = unitText,
                fontSize = 28.sp,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.h1,
                modifier = Modifier.clickable {
                    ThemeState.isMetric = !ThemeState.isMetric
                }
            )
            Spacer(
                modifier = Modifier.size(8.dp)
            )
            val icon = when (ThemeState.appInDarkMode) {
                true -> Icons.Filled.DarkMode
                else -> Icons.Filled.LightMode
            }
            IconButton(
                onClick = {
                    ThemeState.appInDarkMode = !ThemeState.appInDarkMode
                }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.turn_on_light_mode),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun AppBarBottom(
    currentScreen: AppScreen,
    navController: NavHostController
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                if (currentScreen.name == "Weather") {
                    navController.navigate(AppScreen.Sensors.name)
                }
                else {
                    navController.navigate(AppScreen.Weather.name)
                } },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(bottom = 50.dp)
        ) {
            Text(
                text =
                if (currentScreen.name == "Weather") { stringResource(R.string.sensors) }
                else { stringResource(R.string.weather) },
                color = MaterialTheme.colors.onSurface,
                fontSize = 20.sp
            )
        }
    }
}