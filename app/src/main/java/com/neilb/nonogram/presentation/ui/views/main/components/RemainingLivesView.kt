package com.neilb.nonogram.presentation.ui.views.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    mainViewModel: MainViewModel,
    remainingLives: Int? = null,
    changeRemainingLives: ((Int) -> Unit)? = null
) {

    val remainingLivesInState: Int by mainViewModel.remainingLives.observeAsState(3)
    val remainingLivesUI = remainingLives ?: remainingLivesInState
    val allLives = game.numberOfLives

    Row(
        modifier
    ) {
        repeat(allLives) {
            Icon(
                imageVector = if (it < remainingLivesUI) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = if (it < remainingLivesUI) Color.Red else oppositeColor,
                modifier = Modifier
                    .padding(end = if (it + 1 == allLives) 0.dp else 8.dp)
                    .size(30.dp)
            )
        }
        if (changeRemainingLives != null) {
            IconButton(
                onClick = { changeRemainingLives(remainingLivesUI + 1) },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = oppositeColor,
                )
            }
            if (remainingLivesUI != 1) {
                IconButton(
                    onClick = { changeRemainingLives(remainingLivesUI - 1) },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = null,
                        tint = oppositeColor,
                    )
                }
            }
        }
    }
}
