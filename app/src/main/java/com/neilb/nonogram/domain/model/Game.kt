package com.neilb.nonogram.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "puzzle")
data class Game(
    @PrimaryKey val id: String,
    val size: Int,
    val type: Int,
    val solvedTable: List<List<Block>>,
    val numberOfLives: Int
)

