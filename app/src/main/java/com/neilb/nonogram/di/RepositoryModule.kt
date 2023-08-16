package com.neilb.nonogram.di

import com.neilb.nonogram.data.repository.ApiRepositoryImpl
import com.neilb.nonogram.data.repository.PuzzleRepositoryImpl
import com.neilb.nonogram.domain.repository.ApiRepository
import com.neilb.nonogram.domain.repository.PuzzleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPuzzleRepository(puzzleRepositoryImpl: PuzzleRepositoryImpl): PuzzleRepository

    @Binds
    @Singleton
    abstract fun bindApiRepository(apiRepositoryImpl: ApiRepositoryImpl): ApiRepository

}