package ru.coddog.weatherapp.data

import ru.coddog.weatherapp.data.models.ApiResponse
import ru.coddog.weatherapp.data.models.Forecast
import ru.coddog.weatherapp.domain.entities.DailyForecast
import ru.coddog.weatherapp.domain.entities.ForecastEntity
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.math.roundToInt

object ForecastsMapper {
    fun toDailyForecasts(apiResponse: ApiResponse): List<DailyForecast> =
        toDailyForecasts(apiResponse.forecasts, apiResponse.city.timezone)

    fun toDailyForecasts(forecasts: List<Forecast>, timeZone: Int): List<DailyForecast> {
        return forecasts
            .map {
                val dateTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(it.timestamp),
                    TimeZone.getDefault().toZoneId()
                )
                dateTime.toLocalDate() to toForecastEntity(it, dateTime.toLocalTime())
            }
            .groupBy(
                Pair<LocalDate, ForecastEntity>::first,
                Pair<LocalDate, ForecastEntity>::second
            )
            .map { DailyForecast(it.key, it.value) }
    }

    fun toForecastEntity(forecast: Forecast, localTime: LocalTime) =
        ForecastEntity(
            forecast.weather[0].description,
            ICON_URL_PATTERN.format(forecast.weather[0].icon),
            localTime,
            ForecastEntity.Temperature(
                forecast.main.temp.toCelsius(),
                forecast.main.tempMin.toCelsius(),
                forecast.main.tempMax.toCelsius(),
                forecast.main.feelsLike.toCelsius()
            ),
            ForecastEntity.Pressure(
                forecast.main.pressure.toMMHG(),
                forecast.main.pressureSeaLevel.toMMHG(),
                forecast.main.pressureGroundLevel.toMMHG()
            ),
            forecast.main.humidity,
            forecast.wind,
            forecast.clouds.cloudiness,
            forecast.probabilityOfPrecipitation,
            forecast.visibility
        )

    private fun Double.toCelsius() = minus(273.15).roundToInt()
    private fun Int.toMMHG() = toDouble().div(1.333).roundToInt()
    private const val ICON_URL_PATTERN = "https://openweathermap.org/img/wn/%s@2x.png"
}

