package ru.coddog.weatherapp.data.sources

import io.reactivex.Single
import ru.coddog.weatherapp.data.models.ApiResponse

interface DataSource {
    fun getForecastByCity(city: String): Single<ApiResponse>

    fun getForecastByCoordinates(lat: Double, lon: Double): Single<ApiResponse>
}
