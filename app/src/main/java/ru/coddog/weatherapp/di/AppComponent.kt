package ru.coddog.weatherapp.di

import dagger.Component
import ru.coddog.weatherapp.data.sources.DataSource
import ru.coddog.weatherapp.data.sources.remote.RemoteDataSource
import ru.coddog.weatherapp.domain.repositories.IRepository
import ru.coddog.weatherapp.presentation.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, RemoteDataSourceModule::class, ApiModule::class])
interface AppComponent {
    fun getRemoteDataSource(): RemoteDataSource
    fun getRepository(): IRepository

    fun inject(mainActivity: MainActivity)
}