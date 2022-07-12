package ru.coddog.weatherapp.data.sources.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.coddog.weatherapp.data.models.ApiResponse

interface API {
    @GET(".")
    fun getForecastByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("lang") langCode: String = DEFAULT_LANGCODE
    ): Single<ApiResponse>

    @GET(".")
    fun getForecastByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("lang") langCode: String = DEFAULT_LANGCODE
    ): Single<ApiResponse>

    companion object {
        private const val DEFAULT_LANGCODE = "ru"
    }
}