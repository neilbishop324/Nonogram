package com.neilb.nonogram.presentation.ui.views.collections.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.neilb.nonogram.R
import com.neilb.nonogram.common.NavDestinations
import com.neilb.nonogram.data.model.request.GameItem
import com.neilb.nonogram.data.model.request.SolvedTableItemItem
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.presentation.ui.theme.indigoColor
import com.neilb.nonogram.presentation.ui.theme.puzzleCardColor
import com.neilb.nonogram.presentation.ui.views.collections.CollectionsViewModel
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel
import com.neilb.nonogram.presentation.util.generateColor
import com.neilb.nonogram.presentation.util.toGame

@Composable
fun PuzzleItem(
    game: GameItem,
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: CollectionsViewModel
) {
    Box {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp, focusedElevation = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = puzzleCardColor),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    repeat((game.solvedTable ?: listOf()).size) { y ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            repeat(((game.solvedTable ?: listOf())[y] ?: listOf()).size) { x ->
                                val block = ((game.solvedTable ?: listOf())[y] ?: listOf())[x] ?: SolvedTableItemItem()
                                Box(modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .background(
                                        when (block.status) {
                                            Block.black -> Color.Black
                                            Block.coloured -> Color(
                                                android.graphics.Color.parseColor(
                                                    block.color
                                                )
                                            )

                                            else -> Color.White
                                        }
                                    )
                                )
                            }
                        }
                    }
                }
                if (game.makerName != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(contentAlignment = Alignment.CenterEnd) {
                        Text(text = game.makerName, color = generateColor(game.makerName))
                    }
                }
            }
        }

        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 8.dp, top = 8.dp)
        ) {
            IconButton(onClick = { expanded = true },
                modifier = Modifier
                    .background(indigoColor, CircleShape)
                    .size(36.dp)
                    .padding(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
                }
            }

            val context = LocalContext.current

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(id = R.string.play),
                            style = TextStyle(fontSize = 16.sp)
                        )
                    },
                    onClick = {
                        mainViewModel.createGame(game.toGame())
                        navController.navigate(NavDestinations.MAIN_SCREEN)
                        expanded = false
                    }
                )
                if (game.id!!.startsWith("own")) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                stringResource(id = R.string.delete),
                                style = TextStyle(fontSize = 16.sp)
                            )
                        },
                        onClick = {
                            viewModel.deleteGameLocally(game.toGame(), context)
                            expanded = false
                        }
                    )
                }
            }

        }
    }
}