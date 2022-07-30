package ru.coddog.weatherapp.presentation

import ru.coddog.weatherapp.domain.entities.DailyForecast
import java.time.LocalDate

object DateMapper {
    fun positionToTabText(position: Int, it: List<DailyForecast>): String {
        return if (it[0].date == LocalDate.now())
            when (position) {
                0 -> "Сегодня"
                1 -> "Завтра"
                else -> it[position].date.toStr()
            }
        else
            when (position) {
                0 -> "Завтра"
                else -> it[position].date.toStr()
            }
    }

    private fun LocalDate.toStr() = "$dayOfMonth.$monthValue.$year"
}