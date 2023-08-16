package com.neilb.nonogram.data.repository

import com.neilb.nonogram.data.data_source.PuzzleDatabase
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.model.ProgressInGame
import com.neilb.nonogram.domain.repository.PuzzleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PuzzleRepositoryImpl @Inject constructor(
    puzzleDatabase: PuzzleDatabase
) : PuzzleRepository {

    private val puzzleDao = puzzleDatabase.puzzleDao

    override fun getPublicPuzzles(limit: Int, skip: Int): Flow<List<Game>> {
        return puzzleDao.getPublicPuzzles(limit, skip)
    }

    override fun getOwnPuzzles(limit: Int, skip: Int): Flow<List<Game>> {
        return puzzleDao.getOwnPuzzles(limit, skip)
    }

    override suspend fun getPuzzleById(id: String): Game? {
        return puzzleDao.getPuzzleById(id)
    }

    override suspend fun insertPuzzle(game: Game) {
        puzzleDao.insertPuzzle(game)
    }

    override suspend fun deletePuzzle(game: Game) {
        puzzleDao.deletePuzzle(game)
    }

    override suspend fun deleteAllPublicPuzzles() {
        puzzleDao.deleteAllPublicPuzzles()
    }

    override suspend fun getProgressById(id: String): ProgressInGame? {
        return puzzleDao.getProgressById(id)
    }

    override suspend fun insertProgress(progressInGame: ProgressInGame) {
        puzzleDao.insertProgress(progressInGame)
    }

    override suspend fun getPublicPuzzleSize(): Int? {
        return puzzleDao.getPublicPuzzleSize()
    }

    override suspend fun getOwnPuzzleSize(): Int? {
        return puzzleDao.getOwnPuzzleSize()
    }
}