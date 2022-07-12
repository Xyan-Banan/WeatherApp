package ru.coddog.weatherapp.data.repositories

import io.reactivex.Single
import ru.coddog.weatherapp.data.ForecastsMapper
import ru.coddog.weatherapp.data.models.ApiResponse
import ru.coddog.weatherapp.data.sources.remote.RemoteDataSource
import ru.coddog.weatherapp.domain.entities.DailyForecast
import ru.coddog.weatherapp.domain.repositories.IRepository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val remoteDataSource: RemoteDataSource,
//    localDataSource: LocalDataSource
) : IRepository {
    override fun getForecastByCity(city: String): Single<List<DailyForecast>> {
        return remoteDataSource.getForecastByCity(city).map { apiResponse: ApiResponse ->
            ForecastsMapper.toDailyForecasts(apiResponse)
        }
    }

    override fun getForecastByCoordinates(lat: Double, lon: Double): Single<List<DailyForecast>> {
        TODO("Not yet implemented")
    }
}
