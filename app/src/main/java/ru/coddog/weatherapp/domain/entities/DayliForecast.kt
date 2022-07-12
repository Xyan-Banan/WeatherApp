package ru.coddog.weatherapp.domain.entities

import java.time.LocalDate

data class DailyForecast(val date: LocalDate, val list: List<ForecastEntity>) {
    override fun toString(): String {
        return "DailyForecast(date=$date, list=${list.joinToString(",\n","\n[", "]")})"
    }
}

