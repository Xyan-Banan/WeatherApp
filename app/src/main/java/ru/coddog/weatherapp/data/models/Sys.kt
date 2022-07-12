package ru.coddog.weatherapp.data.models
import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val partOfDay: Char
)