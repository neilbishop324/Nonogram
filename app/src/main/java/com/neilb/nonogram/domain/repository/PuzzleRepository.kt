package com.neilb.nonogram.domain.repository

import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.model.ProgressInGame
import kotlinx.coroutines.flow.Flow

interface PuzzleRepository {

    fun getPublicPuzzles(): Flow<List<Game>>

    fun getOwnPuzzles(): Flow<List<Game>>

    suspend fun getPuzzleById(id: String): Game?

    suspend fun insertPuzzle(game: Game)

    suspend fun deletePuzzle(game: Game)

    suspend fun deleteAllPublicPuzzles()

    suspend fun getProgressById(id: String): ProgressInGame?

    suspend fun insertProgress(progressInGame: ProgressInGame)

}