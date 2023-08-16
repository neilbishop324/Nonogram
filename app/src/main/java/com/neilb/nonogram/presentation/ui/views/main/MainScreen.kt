package com.neilb.nonogram.presentation.ui.views.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.neilb.nonogram.R
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.presentation.ui.lib.game_end_dialog.GameEndDialog
import com.neilb.nonogram.presentation.ui.views.error.ErrorPage
import com.neilb.nonogram.presentation.ui.views.main.components.BlockSelector
import com.neilb.nonogram.presentation.ui.views.main.components.GameView
import com.neilb.nonogram.presentation.ui.views.main.components.RemainingLivesView
import com.neilb.nonogram.presentation.util.createGame
import com.neilb.nonogram.presentation.util.getGameTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {

    val game: Game? by mainViewModel.game.observeAsState()
    val selectedBlock: Block by mainViewModel.selectedBlock.observeAsState(Block(Block.empty))

    if (game == null) {
        ErrorPage(errorMessage = stringResource(id = R.string.game_not_found))
        return
    }

    var moreExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = getGameTitle(gameType = game!!.type))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { moreExpanded = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = moreExpanded,
                        onDismissRequest = { moreExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    stringResource(id = R.string.new_game),
                                    style = TextStyle(fontSize = 16.sp)
                                )
                            },
                            onClick = {
                                mainViewModel.createGame(createGame(mainViewModel.game.value!!.id, mainViewModel.game.value!!.type))
                                moreExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    stringResource(id = R.string.restart),
                                    style = TextStyle(fontSize = 16.sp)
                                )
                            },
                            onClick = {
                                mainViewModel.restartGame()
                                moreExpanded = false
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        var showLevelCompletedDialog by remember { mutableStateOf(false) }
        var showLevelFailedDialog by remember { mutableStateOf(false) }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            val (remainingLivesView, gameView, blockSelector) = createRefs()

            RemainingLivesView(
                modifier = Modifier.constrainAs(remainingLivesView) {
                    bottom.linkTo(gameView.top, margin = 36.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                game = game!!,
                mainViewModel = mainViewModel
            )

            GameView(
                modifier = Modifier.constrainAs(gameView) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                game = game!!,
                mainViewModel = mainViewModel,
                onLevelFinished = {
                    showLevelCompletedDialog = true
                },
                onLevelFailed = {
                    showLevelFailedDialog = true
                }
            )

            BlockSelector(
                modifier = Modifier.constrainAs(blockSelector) {
                    top.linkTo(gameView.bottom, margin = 36.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                game = game!!,
                selectedBlock = selectedBlock,
                updateSelectedBlock = {
                    mainViewModel.updateSelectedBlock(it)
                }
            )

        }

        GameEndDialog(
            visibility = showLevelCompletedDialog,
            dismissDialog = { showLevelCompletedDialog = false },
            success = true,
            navController = navController,
            mainViewModel = mainViewModel
        )

        GameEndDialog(
            visibility = showLevelFailedDialog,
            dismissDialog = { showLevelFailedDialog = false },
            success = false,
            navController = navController,
            mainViewModel = mainViewModel
        )

    }
}