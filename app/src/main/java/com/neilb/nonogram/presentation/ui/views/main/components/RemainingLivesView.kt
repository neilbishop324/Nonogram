package com.neilb.nonogram.presentation.ui.views.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.presentation.ui.theme.oppositeColor
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel

@Composable
fun RemainingLivesView(
    modifier: Modifier,
    game: Game,
    mainViewModel: MainViewModel
) {

    val remainingLives: Int by mainViewModel.remainingLives.observeAsState(3)
    val allLives = game.numberOfLives

    Row(
        modifier
    ) {
        repeat(allLives) {
            Icon(
                imageVector = if (it < remainingLives) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = if (it < remainingLives) Color.Red else oppositeColor,
                modifier = Modifier.padding(end = if (it + 1 == allLives) 0.dp else 8.dp).size(30.dp)
            )
        }
    }

}
