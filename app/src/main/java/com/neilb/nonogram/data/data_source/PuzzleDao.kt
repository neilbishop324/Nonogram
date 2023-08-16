package com.neilb.nonogram.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.model.ProgressInGame
import kotlinx.coroutines.flow.Flow

@Dao
interface PuzzleDao {

    @Query("SELECT * FROM puzzle WHERE id LIKE 'public%' LIMIT :limit OFFSET :offset")
    fun getPublicPuzzles(limit: Int, offset: Int): Flow<List<Game>>

    @Query("SELECT COUNT(*) FROM puzzle WHERE id LIKE 'public%'")
    suspend fun getPublicPuzzleSize(): Int?

    @Query("SELECT * FROM puzzle WHERE id LIKE 'own%' LIMIT :limit OFFSET :offset")
    fun getOwnPuzzles(limit: Int, offset: Int): Flow<List<Game>>

    @Query("SELECT COUNT(*) FROM puzzle WHERE id LIKE 'own%'")
    suspend fun getOwnPuzzleSize(): Int?

    @Query("SELECT * FROM puzzle WHERE id = :id")
    suspend fun getPuzzleById(id: String): Game?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPuzzle(game: Game)

    @Delete
    suspend fun deletePuzzle(game: Game)

    @Query("DELETE FROM puzzle WHERE id LIKE 'public%'")
    suspend fun deleteAllPublicPuzzles()

    @Query("SELECT * FROM progress WHERE id = :id")
    suspend fun getProgressById(id: String): ProgressInGame?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progressInGame: ProgressInGame)

}