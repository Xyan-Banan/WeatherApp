package ru.coddog.weatherapp.data.sources.remote

import io.reactivex.Single
import ru.coddog.weatherapp.data.models.ApiResponse
import ru.coddog.weatherapp.data.sources.DataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: API, private val apiKey: String): DataSource {
    override fun getForecastByCity(city: String): Single<ApiResponse> {
        return api.getForecastByCity(city, apiKey)
    }

    override fun getForecastByCoordinates(lat: Double, lon: Double): Single<ApiResponse> {
        return api.getForecastByCoordinates(lat,lon, apiKey)
    }
}