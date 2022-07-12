package ru.coddog.weatherapp.data.models
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val forecasts: List<Forecast>,
    @SerializedName("message")
    val message: Int
)