package com.neilb.nonogram.di

import android.app.Application
import androidx.room.Room
import com.neilb.nonogram.common.Constants
import com.neilb.nonogram.data.data_source.PuzzleDatabase
import com.neilb.nonogram.data.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePuzzleDatabase(app: Application): PuzzleDatabase {
        return Room.databaseBuilder(
            app,
            PuzzleDatabase::class.java,
            "puzzle_db"
        ).build()
    }

    @Provides
    @Singleton
    @Named("BaseUrl")
    fun provideBaseUrl() = Constants.API_URL

    @Provides
    @Singleton
    fun provideApiService(
        @Named("BaseUrl") baseUrl: String,
        okHttpClient: OkHttpClient,
    ): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(Interceptor {
                val requestBuilder = it.request().newBuilder()
                requestBuilder.addHeader("Accept", "application/json")
                it.proceed(requestBuilder.build())
            })
            .build()
    }

}