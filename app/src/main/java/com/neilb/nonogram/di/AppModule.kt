package com.neilb.nonogram.di

import android.app.Application
import androidx.room.Room
import com.neilb.nonogram.data.data_source.PuzzleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

}