package com.neilb.nonogram.presentation.ui.views.menu.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.neilb.nonogram.R
import com.neilb.nonogram.presentation.ui.theme.oppositeColor

@Composable
fun Title() {
    Text(
        text = stringResource(id = R.string.app_name),
        style = TextStyle(
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.libre_baskerville)),
            fontWeight = FontWeight(400),
            color = oppositeColor,
            textAlign = TextAlign.Center,
        )
    )
}