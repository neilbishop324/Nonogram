package com.neilb.nonogram.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "progress")
data class ProgressInGame(
    @PrimaryKey val id: String,
    val table: List<List<Block>>,
    val remainingLives: Int,
)
