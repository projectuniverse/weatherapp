package com.codecamp.weatherapp.viewmodel

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class SensorsViewmodel(
    private val sensorManager: SensorManager,
    sensorList: List<Float>
) : ViewModel() {

    val sensorLiveData = SensorLiveData(sensorManager, sensorList.toMutableList())

    inner class SensorLiveData(sensorManager: SensorManager, oldsensorList: MutableList<Float>) : LiveData<List<Float>>(), SensorEventListener {

        private val sensorList = listOf(
            sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
            sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
            sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),
            sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        )

        private var defaultSensors = oldsensorList

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            return
        }

        override fun onSensorChanged(event: SensorEvent) {
            val newValue = event.values[0]
            if (defaultSensors[2] != newValue) {
                when (event.sensor?.type) {
                    Sensor.TYPE_AMBIENT_TEMPERATURE -> defaultSensors[0] = newValue
                    Sensor.TYPE_LIGHT -> defaultSensors[1] = newValue
                    Sensor.TYPE_PRESSURE -> defaultSensors[2] = newValue
                    Sensor.TYPE_RELATIVE_HUMIDITY -> defaultSensors[3] = newValue
                }
                postValue(defaultSensors)
            }
        }

        override fun onActive() {
            sensorList.forEach {sensor ->
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            }
        }

        override fun onInactive() {
            sensorManager.unregisterListener(this)
        }
    }
}