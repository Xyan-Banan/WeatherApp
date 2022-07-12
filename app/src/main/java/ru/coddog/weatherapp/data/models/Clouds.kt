package ru.coddog.weatherapp.data.models
import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val cloudiness: Int
)