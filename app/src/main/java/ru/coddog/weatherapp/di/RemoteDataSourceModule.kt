package ru.coddog.weatherapp.di

import dagger.Module
import dagger.Provides
import ru.coddog.weatherapp.data.sources.DataSource
import ru.coddog.weatherapp.data.sources.remote.API
import ru.coddog.weatherapp.data.sources.remote.RemoteDataSource
import javax.inject.Singleton

@Module
class RemoteDataSourceModule {
    companion object {
        @Singleton
        @Provides
        fun provideRemoteDataSource(api: API): RemoteDataSource = RemoteDataSource(api, API_KEY)

        private const val API_KEY = "6e482111205d9a77122981b35e37f8f1"
    }
}