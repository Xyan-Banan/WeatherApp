package ru.coddog.weatherapp

import android.app.Application
import android.util.Log
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.coddog.weatherapp.data.models.ApiResponse
import ru.coddog.weatherapp.di.AppComponent
import ru.coddog.weatherapp.di.DaggerAppComponent

class WeatherApplication : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        component = DaggerAppComponent.factory().create(this)
    }
}