package com.neilb.nonogram.presentation.util

import androidx.compose.ui.graphics.Color

fun generateRandomColor() : Color {
    val letters = "0123456789ABCDEF".toCharArray()
    var colorText = "#"
    repeat(6) {
        colorText += letters.random()
    }
    return Color(android.graphics.Color.parseColor(colorText))
}