package ru.coddog.weatherapp.di

import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class LocationModule {
    @Provides
    fun provideGeocoder(context: Context): Geocoder = Geocoder(context, Locale("ru", "RU"))

    @Provides
    fun provideLocationManger(context: Context) =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @Provides
    fun provideFusedLocationClient(context: Context) =
        LocationServices.getFusedLocationProviderClient(context)
}