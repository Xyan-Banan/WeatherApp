package ru.coddog.weatherapp.domain.entities

import ru.coddog.weatherapp.data.models.Wind
import java.time.LocalTime

data class ForecastEntity(
    val description: String,
    val iconUrl: String,
    val dateTime: LocalTime,
    val temp: Temperature,
    val pressure: Pressure,
    val humidity: Int,
    val wind: Wind,
    val cloudiness: Int,
    val probabilityOfPrecipitation: Double,
    val visibility: Int
) {
    class Temperature(val avg: Int, val min: Int, val max: Int, val feelsLike: Int) {
        override fun toString(): String {
            return "Temperature(avg=$avg, min=$min, max=$max, feelsLike=$feelsLike)"
        }
    }

    class Pressure(val current: Int, val seaLevel: Int = 0, val groundLevel: Int = 0) {
        override fun toString(): String {
            return "Pressure(current=$current, seaLevel=$seaLevel, groundLevel=$groundLevel)"
        }
    }
}