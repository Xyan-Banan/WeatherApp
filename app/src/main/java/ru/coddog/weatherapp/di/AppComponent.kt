package ru.coddog.weatherapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.coddog.weatherapp.data.sources.remote.RemoteDataSource
import ru.coddog.weatherapp.domain.repositories.IRepository
import ru.coddog.weatherapp.presentation.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, RemoteDataSourceModule::class, ApiModule::class, ViewModelModule::class, LocationModule::class])
interface AppComponent {
    fun getRemoteDataSource(): RemoteDataSource
    fun getRepository(): IRepository

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context
        ): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}