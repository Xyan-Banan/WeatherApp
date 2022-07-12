package ru.coddog.weatherapp.data

import ru.coddog.weatherapp.data.models.ApiResponse
import ru.coddog.weatherapp.data.models.Forecast
import ru.coddog.weatherapp.domain.entities.DailyForecast
import ru.coddog.weatherapp.domain.entities.ForecastEntity
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

object ForecastsMapper {
    fun toDailyForecasts(apiResponse: ApiResponse): List<DailyForecast> =
        toDailyForecasts(apiResponse.forecasts, apiResponse.city.timezone)

    fun toDailyForecasts(forecasts: List<Forecast>, timeZone: Int): List<DailyForecast> {
        return forecasts
            .map {
                val dateTime = LocalDateTime
//                    .parse(it.dateTime, DateTimeFormatter.ofPattern())
                    .ofInstant(
                        Instant.ofEpochSecond(it.timestamp),
                        TimeZone.getDefault().toZoneId()
                    )
                    .plusSeconds(timeZone.toLong())
                dateTime.toLocalDate() to toForecastEntity(it, dateTime.toLocalTime())
            }
            .groupBy(
                Pair<LocalDate, ForecastEntity>::first,
                Pair<LocalDate, ForecastEntity>::second
            )
            .map { DailyForecast(it.key, it.value) }
//            .sortedBy { it.date }
    }

    fun toForecastEntity(forecast: Forecast, localTime: LocalTime) =
        ForecastEntity(
            forecast.weather[0].description,
            localTime
//            (forecast.timestamp - timeZone).toDate()
        )

    private fun Int.toDate() = Date(times(1000L))

}
