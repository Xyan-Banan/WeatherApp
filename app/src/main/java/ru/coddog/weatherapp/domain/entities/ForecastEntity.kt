package ru.coddog.weatherapp.domain.entities

import java.time.LocalTime
import java.util.*

data class ForecastEntity(
    val description: String,
    val dateTime: LocalTime
)
