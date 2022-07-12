package ru.coddog.weatherapp.domain.repositories

import io.reactivex.Single
import ru.coddog.weatherapp.data.models.ApiResponse
import ru.coddog.weatherapp.domain.entities.DailyForecast

interface IRepository {
    fun getForecastByCity(city: String): Single<List<DailyForecast>>

    fun getForecastByCoordinates(lat: Double, lon: Double): Single<List<DailyForecast>>
}
