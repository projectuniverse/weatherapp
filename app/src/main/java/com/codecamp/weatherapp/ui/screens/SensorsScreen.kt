package com.codecamp.weatherapp.ui.screens

import android.content.Context
import android.hardware.SensorManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.weatherapp.R
import com.codecamp.weatherapp.model.Sensor
import com.codecamp.weatherapp.ui.theme.ThemeState
import com.codecamp.weatherapp.viewmodel.SensorsViewmodel
import kotlin.math.roundToInt


@Composable
fun SensorsScreen() {
    val sensorList = remember {
        mutableStateListOf(0f, 0f, 0f, 0f)
    }
    val sensorsViewmodel = SensorsViewmodel(
        LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager,
        sensorList
    )
    sensorsViewmodel.sensorLiveData.observe(LocalLifecycleOwner.current) {
        it.forEachIndexed { index, fl ->
            if(fl != sensorList[index]) sensorList[index] = fl
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier.weight(10.0f)
        )
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(90.0f)
        ) {
            SensorInfoScreen(
                sensorList = sensorList
            )
        }
    }
}

@Composable
fun SensorInfoScreen(sensorList: List<Float>) {
    val newSensorList = mutableListOf<Sensor>()
    val sensorNames = stringArrayResource(R.array.sensorNames)
    val sensorUnits = stringArrayResource(R.array.sensorUnits)

    sensorList.forEachIndexed { index, fl ->
        newSensorList.add(
            Sensor(sensorNames[index], (fl * 10.0f).roundToInt() / 10.0f, sensorUnits[index])
        )
    }

    for (sensor in newSensorList) {
        SensorCard(sensor = sensor)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun SensorCard(sensor: Sensor) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.65f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = sensor.sensorName,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            val unitArray = stringArrayResource(R.array.otherUnits)
            val value = if (sensor.unit != unitArray[0]) {
                sensor.value.toString() + " " + sensor.unit
            } else {
                if (ThemeState.isMetric) {
                    sensor.value.toString() + " " + sensor.unit
                } else {
                    (((sensor.value * 1.8f + 32) * 10f).roundToInt() / 10f).toString() + " " + unitArray[1]
                }
            }
            Text(
                text = value,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
        }
    }
}