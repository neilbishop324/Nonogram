package com.neilb.nonogram.domain.model

import androidx.compose.ui.graphics.Color

data class Block(
    val status: Int,
    val color: Color? = null
) {
    companion object {
        const val notSelected = 0
        const val black = 1
        const val empty = 2
        const val coloured = 3
    }
}