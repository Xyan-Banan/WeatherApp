package ru.coddog.weatherapp.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.coddog.weatherapp.data.sources.remote.API
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor

@Module
abstract class ApiModule {
    companion object {
        @Singleton
        @Provides
        fun api(retrofit: Retrofit): API = retrofit.create(API::class.java)

        @Singleton
        @Provides
        fun retrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        @Singleton
        @Provides
        fun okHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .build()

        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/forecast/"
    }
}