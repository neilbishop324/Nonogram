package com.neilb.nonogram.presentation.ui.views.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neilb.nonogram.presentation.ui.theme.darkButtonColor
import com.neilb.nonogram.presentation.ui.theme.sameColor
import com.neilb.nonogram.presentation.ui.theme.shadowColor

@Composable
fun MenuButton(
    backgroundColor: Color = darkButtonColor,
    gradientColors: List<Color>? = null,
    icon: ImageVector? = null,
    text: String,
    textColor: Color = sameColor,
    horizontalPadding: Dp = 60.dp,
    onClick: () -> Unit
) {
    var modifier = Modifier
        .padding(1.dp)
        .fillMaxWidth()
        .padding(horizontal = horizontalPadding, vertical = 8.dp)

    modifier = if (gradientColors != null) {
        modifier.background(
            brush = Brush.horizontalGradient(
                colors = gradientColors
            ),
            shape = RoundedCornerShape(size = 9.dp)
        )
    } else {
        modifier.background(
            color = backgroundColor,
            shape = RoundedCornerShape(size = 9.dp)
        )
    }

    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = textColor
            )
        }

        Text(
            text = text,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(600),
                color = textColor,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        )
    }
}