package com.neilb.nonogram.presentation.ui.views.create_nonogram

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.neilb.nonogram.R
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.presentation.ui.lib.create_nonogram_dialog.CreateNonogramDialog
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel
import com.neilb.nonogram.presentation.ui.views.main.components.BlockSelector
import com.neilb.nonogram.presentation.ui.views.main.components.GameView
import com.neilb.nonogram.presentation.ui.views.main.components.RemainingLivesView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNonogramScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {

    val context = LocalContext.current

    val viewModel = hiltViewModel<CreateNonogramViewModel>()
    val game: Game? by viewModel.game.observeAsState()
    val selectedBlock: Block by viewModel.selectedBlock.observeAsState(Block(Block.empty))
    var initialNumberOfLives by remember { mutableIntStateOf(0) }

    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val onStart = {
        mainViewModel.game.value?.let { game ->
            viewModel.updateGame(game)
            initialNumberOfLives = game.numberOfLives
        }
    }

    val currentOnStart by rememberUpdatedState(onStart)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    var saveAlertDialogVisibility by remember { mutableStateOf(false) }

    CreateNonogramDialog(
        dismissDialog = { saveAlertDialogVisibility = false },
        visibility = saveAlertDialogVisibility,
        onSharePublicly = {
            viewModel.submitNonogram(context, makerName = it) {
                navController.navigateUp()
            }
        },
        onSaveToCollection = {
            viewModel.submitNonogram(context) {
                navController.navigateUp()
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.create_your_nonogram))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.resetDesign(initialNumberOfLives)
                    }) {
                        Icon(imageVector = Icons.Default.RestartAlt, contentDescription = null)
                    }

                    IconButton(onClick = {
                        saveAlertDialogVisibility = true
                    }) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->

        if (game == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
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
                    mainViewModel = mainViewModel,
                    remainingLives = game!!.numberOfLives,
                    changeRemainingLives = {
                        viewModel.updateGame(game!!.copy(numberOfLives = it))
                    }
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
                    table = game!!.solvedTable,
                    onClickToCell = { x, y ->
                        val remainingBlock = game!!.solvedTable[y][x]
                        viewModel.clickToCell(x, y,
                            value = if (remainingBlock == selectedBlock) Block(Block.notSelected) else null)
                    }
                )

                BlockSelector(
                    modifier = Modifier.constrainAs(blockSelector) {
                        top.linkTo(gameView.bottom, margin = 18.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    game = game!!,
                    selectedBlock = selectedBlock,
                    updateSelectedBlock = {
                        viewModel.updateSelectedBlock(it)
                    },
                    isDesigner = true
                )

            }
        }

    }

}