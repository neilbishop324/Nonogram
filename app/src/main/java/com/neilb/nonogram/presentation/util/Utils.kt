package com.neilb.nonogram.presentation.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

fun generateRandomColor() : Color {
    val letters = "0123456789ABCDEF".toCharArray()
    var colorText = "#"
    repeat(6) {
        colorText += letters.random()
    }
    return Color(android.graphics.Color.parseColor(colorText))
}

fun generateRandomString(len: Int = 10, onlyDigits: Boolean = false) : String {
    val allChars = if (!onlyDigits) ('a'..'z') + ('A'..'Z') else listOf<Char>() + ('0'..'9')
    return (1..len).map { allChars.random() }.joinToString("")
}

fun colorIsLight(color: Color) : Boolean {
    return ColorUtils.calculateLuminance(color.toArgb()) >= 0.5
}

fun Color.toHex(): String {
    return String.format("#%02X%02X%02X%02X", (this.alpha * 255).toInt(), (this.red * 255).toInt(), (this.green * 255).toInt(), (this.blue * 255).toInt())
}

fun getOppositeColor(color: Color) : Color {
    return if (colorIsLight(color)) Color.Black else Color.White
}