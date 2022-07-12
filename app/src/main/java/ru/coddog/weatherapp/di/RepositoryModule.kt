package ru.coddog.weatherapp.di

import dagger.Binds
import dagger.Module
import ru.coddog.weatherapp.data.repositories.RepositoryImpl
import ru.coddog.weatherapp.domain.repositories.IRepository
import javax.inject.Singleton

@Module
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): IRepository
}