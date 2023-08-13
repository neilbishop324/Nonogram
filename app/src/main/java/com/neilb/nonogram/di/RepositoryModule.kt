package com.neilb.nonogram.di

import com.neilb.nonogram.data.repository.PuzzleRepositoryImpl
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

}