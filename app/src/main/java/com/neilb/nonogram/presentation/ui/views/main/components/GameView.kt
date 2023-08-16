package com.neilb.nonogram.presentation.ui.views.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel
import com.neilb.nonogram.presentation.util.getColorsDistribution
import com.neilb.nonogram.presentation.util.getOppositeColor

@Composable
fun GameView(
    modifier: Modifier = Modifier,
    game: Game,
    mainViewModel: MainViewModel,
    onLevelFailed: (() -> Unit)? = null,
    onLevelFinished: (() -> Unit)? = null,
    table: List<List<Block>>? = null,
    onClickToCell: ((Int, Int) -> Unit)? = null
) {

    val tableInState: List<List<Block>> by mainViewModel.table.observeAsState(arrayListOf())
    val tableUI = table ?: tableInState

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp - 16.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Spacer(modifier = Modifier.size(80.dp))

            repeat(game.size) { index ->
                val columns = game.solvedTable.map { row -> row[index] }
                val colorDistribution = getColorsDistribution(game.type, ArrayList(columns))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(width = ((screenWidth - 80.dp) / game.size) - 8.dp, height = 80.dp)
                        .background(Color.Gray)
                ) {
                    Column {
                        repeat(colorDistribution.size) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .background(colorDistribution[it].second ?: Color.Black),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = colorDistribution[it].first.toString(),
                                    color = getOppositeColor(colorDistribution[it].second ?: Color.Black),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

        }
        Row {
            Column {
                repeat(game.size) { index ->
                    val rows = game.solvedTable[index]
                    val colorDistribution = getColorsDistribution(game.type, ArrayList(rows))

                    Box(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .size(
                                width = 80.dp,
                                height = ((screenWidth - 80.dp) / game.size) - 8.dp
                            )
                            .background(Color.Gray)
                    ) {
                        Row {
                            repeat(colorDistribution.size) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(colorDistribution[it].second ?: Color.Black),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = colorDistribution[it].first.toString(),
                                        color = getOppositeColor(colorDistribution[it].second ?: Color.Black),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column(modifier = Modifier.size(screenWidth - 80.dp)) {
                repeat(game.size) { y ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        repeat(game.size) { x ->
                            val color = when (tableUI[y][x].status) {
                                Block.coloured -> tableUI[y][x].color!!
                                Block.black -> Color.Black
                                else -> Color.White
                            }
                            Box(
                                modifier = Modifier
                                    .size((screenWidth - 80.dp) / game.size)
                                    .background(color)
                                    .border(1.dp, Color.Black)
                                    .clickable {
                                        onClickToCell?.invoke(x, y) ?: run {
                                            mainViewModel.clickCell(
                                                x = x,
                                                y = y,
                                                onFailedGame = { onLevelFailed?.invoke() },
                                                onFinishedGame = { onLevelFinished?.invoke() }
                                            )
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (tableUI[y][x].status == Block.empty) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}