package com.neilb.nonogram.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neilb.nonogram.data.data_source.converter.PuzzleConverter
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.model.ProgressInGame

@TypeConverters(PuzzleConverter::class)
@Database(
    entities = [Game::class, ProgressInGame::class],
    version = 1
)
abstract class PuzzleDatabase : RoomDatabase() {

    abstract val puzzleDao: PuzzleDao

}