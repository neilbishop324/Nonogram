package com.neilb.nonogram.presentation.ui.views.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.InvertColorsOff
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.neilb.nonogram.R
import com.neilb.nonogram.common.GameTypes
import com.neilb.nonogram.common.NavDestinations
import com.neilb.nonogram.presentation.ui.lib.custom_game_dialog.CustomGameDialog
import com.neilb.nonogram.presentation.ui.theme.goldColor
import com.neilb.nonogram.presentation.ui.theme.greenColor
import com.neilb.nonogram.presentation.ui.theme.indigoColor
import com.neilb.nonogram.presentation.ui.theme.orangeColor
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel
import com.neilb.nonogram.presentation.ui.views.menu.components.ActionDescriber
import com.neilb.nonogram.presentation.ui.views.menu.components.MenuButton
import com.neilb.nonogram.presentation.ui.views.menu.components.Title
import com.neilb.nonogram.presentation.util.createGame

@Composable
fun MenuScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {

    var customGameDialogVisibility by remember { mutableStateOf(false) }

    CustomGameDialog(
        visibility = customGameDialogVisibility,
        dismissDialog = { customGameDialogVisibility = false },
        title = stringResource(id = R.string.create_a_custom_game),
        context = LocalContext.current,
        onSubmit = {
            mainViewModel.createGame(it)
            navController.navigate(NavDestinations.MAIN_SCREEN)
        }
    )

    var createYourGameDialogVisibility by remember { mutableStateOf(false) }

    CustomGameDialog(
        visibility = createYourGameDialogVisibility,
        dismissDialog = { createYourGameDialogVisibility = false },
        title = stringResource(id = R.string.create_your_nonogram),
        context = LocalContext.current,
        onSubmit = {
            mainViewModel.createGame(it, saveLocally = false)
            navController.navigate(NavDestinations.CREATE_NONOGRAM_SCREEN)
        },
        emptyTable = true,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {

        Spacer(modifier = Modifier.height(100.dp))

        Title()

        Spacer(modifier = Modifier.height(16.dp))

        ActionDescriber(text = stringResource(id = R.string.play))

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton(
            icon = Icons.Default.InvertColorsOff,
            text = stringResource(id = R.string.black_and_white)
        ) {
            mainViewModel.openGame(createGame(GameTypes.blackAndWhite, MainViewModel.blackAndWhite))
            navController.navigate(NavDestinations.MAIN_SCREEN)
        }

        MenuButton(
            icon = Icons.Default.InvertColors,
            text = stringResource(id = R.string.coloured),
            textColor = Color.White,
            gradientColors = listOf(
                Color(0xff13d8eb),
                Color(0xff0363ba),
                Color(0xffc5078e),
            ),
        ) {
            mainViewModel.openGame(createGame(GameTypes.coloured, MainViewModel.coloured))
            navController.navigate(NavDestinations.MAIN_SCREEN)
        }

        MenuButton(
            icon = Icons.Default.Edit,
            text = stringResource(id = R.string.custom),
            backgroundColor = goldColor,
            textColor = Color.White
        ) {
            customGameDialogVisibility = true
        }

        Spacer(modifier = Modifier.height(16.dp))

        ActionDescriber(text = stringResource(id = R.string.collections))

        MenuButton(
            icon = Icons.Default.Public,
            text = stringResource(id = R.string.public_collections),
            backgroundColor = greenColor,
            textColor = Color.White
        ) {
            navController.navigate("${NavDestinations.COLLECTIONS_SCREEN}/public")
        }

        MenuButton(
            icon = Icons.Default.CollectionsBookmark,
            text = stringResource(id = R.string.your_collections),
            backgroundColor = orangeColor,
            textColor = Color.White
        ) {
            navController.navigate("${NavDestinations.COLLECTIONS_SCREEN}/own")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ActionDescriber(text = stringResource(id = R.string.create))

        MenuButton(
            icon = Icons.Default.Add,
            text = stringResource(id = R.string.create_your_nonogram),
            backgroundColor = indigoColor,
            textColor = Color.White
        ) {
            createYourGameDialogVisibility = true
        }

        Spacer(modifier = Modifier.height(32.dp))

    }

}