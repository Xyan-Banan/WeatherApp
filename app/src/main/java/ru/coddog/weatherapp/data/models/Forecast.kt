package ru.coddog.weatherapp.data.models
import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val timestamp: Long,
    @SerializedName("dt_txt")
    val dateTime: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("pop")
    val probabilityOfPrecipitation: Double,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)