package com.neilb.nonogram.presentation.ui.lib.game_end_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.neilb.nonogram.R
import com.neilb.nonogram.presentation.ui.theme.indigoColor
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel
import com.neilb.nonogram.presentation.ui.views.menu.components.MenuButton
import com.neilb.nonogram.presentation.util.createGame

@Composable
fun GameEndDialog(
    visibility: Boolean,
    dismissDialog: () -> Unit,
    success: Boolean,
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    if (visibility) {
        Dialog(onDismissRequest = dismissDialog) {
            Box {
                IconButton(
                    onClick = { dismissDialog() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset((10).dp, (-10).dp)
                        .background(Color.Red, CircleShape)
                        .zIndex(1f)
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f),
                    ) {
                        Column(
                            Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = if (success) R.string.level_completed else R.string.level_failed),
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            MenuButton(
                                text = stringResource(id = R.string.new_game),
                                backgroundColor = Color.Red,
                                horizontalPadding = 20.dp,
                                textColor = Color.White,
                                icon = Icons.Default.PlayArrow
                            ) {
                                mainViewModel.createGame(createGame(mainViewModel.game.value!!.id, mainViewModel.game.value!!.type))
                                dismissDialog()
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            MenuButton(
                                text = stringResource(id = if (success) R.string.restart else R.string.retry),
                                backgroundColor = Color(0xFF3CB841),
                                horizontalPadding = 20.dp,
                                textColor = Color.White,
                                icon = Icons.Default.Replay
                            ) {
                                mainViewModel.restartGame()
                                dismissDialog()
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            MenuButton(
                                text = stringResource(id = R.string.main_menu),
                                backgroundColor = indigoColor,
                                horizontalPadding = 20.dp,
                                textColor = Color.White,
                                icon = Icons.Default.KeyboardReturn
                            ) {
                                navController.navigateUp()
                                dismissDialog()
                            }
                        }
                    }
                }
            }
        }
    }
}