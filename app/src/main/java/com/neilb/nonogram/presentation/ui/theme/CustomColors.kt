package com.neilb.nonogram.presentation.ui.theme

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val oppositeColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else Color.Black

val sameColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Color.White

val helperColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else Color(0xFF939393)

val darkButtonColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.White else Color(0xFF212121)