package com.codecamp.weatherapp.model

/*
 * Stores the data of a sensor
 */
data class Sensor(
    val sensorName: String,
    var value: Float,
    val unit: String
)
