package com.codecamp.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codecamp.weatherapp.ui.WeatherApp
import com.codecamp.weatherapp.ui.screens.WeatherViewModel
import com.codecamp.weatherapp.ui.theme.WeatherAppTheme
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()
        setContent {
            WeatherAppTheme {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)
                val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory)
                viewModel.getLocation(fusedLocationClient)

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    WeatherApp(
                        viewModel
                    )
                }
            }
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
    }
}