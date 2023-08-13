package com.neilb.nonogram.presentation.ui.views.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.neilb.nonogram.R
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.presentation.ui.lib.custom_game_dialog.CustomGameDialog
import com.neilb.nonogram.presentation.ui.lib.game_end_dialog.GameEndDialog
import com.neilb.nonogram.presentation.ui.views.error.ErrorPage
import com.neilb.nonogram.presentation.ui.views.main.components.BlockSelector
import com.neilb.nonogram.presentation.ui.views.main.components.GameView
import com.neilb.nonogram.presentation.ui.views.main.components.RemainingLivesView
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
                }
            )
        }
    ) {

        var showLevelCompletedDialog by remember { mutableStateOf(false) }
        var showLevelFailedDialog by remember { mutableStateOf(false) }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
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
                mainViewModel = mainViewModel,
                selectedBlock = selectedBlock
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