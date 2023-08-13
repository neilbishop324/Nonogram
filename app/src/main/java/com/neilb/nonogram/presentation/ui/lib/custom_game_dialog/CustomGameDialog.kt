package com.neilb.nonogram.presentation.ui.lib.custom_game_dialog

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.neilb.nonogram.R
import com.neilb.nonogram.common.GameTypes
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.presentation.ui.theme.indigoColor
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel
import com.neilb.nonogram.presentation.ui.views.menu.components.MenuButton
import com.neilb.nonogram.presentation.ui.widgets.DropdownSelectorInTextField
import com.neilb.nonogram.presentation.util.createGame

@Composable
fun CustomGameDialog(
    visibility: Boolean,
    dismissDialog: () -> Unit,
    title: String,
    context: Context,
    onSubmit: (Game) -> Unit,
) {
    if (visibility) {
        Dialog(
            onDismissRequest = dismissDialog
        ) {
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
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f),
                    ) {
                        Column(
                            Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = title,
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(32.dp))

                            val (sizeDropdownVisible, sizeDropdownSetVisibility) = remember {
                                mutableStateOf(
                                    false
                                )
                            }

                            val (sizeSelectedOption, setSizeSelectedOption) = remember {
                                mutableStateOf(
                                    "5x5"
                                )
                            }

                            DropdownSelectorInTextField(
                                visibility = sizeDropdownVisible,
                                setVisibility = sizeDropdownSetVisibility,
                                label = stringResource(
                                    id = R.string.size
                                ),
                                options = (5..15).map { "${it}x${it}" },
                                selectedOptionText = sizeSelectedOption,
                                setSelectedOptionText = setSizeSelectedOption
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            val (typeDropdownVisible, typeDropdownSetVisibility) = remember {
                                mutableStateOf(
                                    false
                                )
                            }

                            val typeOptions = listOf(
                                stringResource(id = R.string.black_and_white),
                                stringResource(id = R.string.coloured)
                            )

                            val (typeSelectedOption, setTypeSelectedOption) = remember {
                                mutableStateOf(
                                    typeOptions[0]
                                )
                            }

                            DropdownSelectorInTextField(
                                visibility = typeDropdownVisible,
                                setVisibility = typeDropdownSetVisibility,
                                label = stringResource(
                                    id = R.string.type
                                ),
                                options = typeOptions,
                                selectedOptionText = typeSelectedOption,
                                setSelectedOptionText = setTypeSelectedOption
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            val (numberOfLives, setNumberOfLives) = remember {
                                mutableStateOf("3")
                            }

                            TextField(
                                value = numberOfLives,
                                onValueChange = setNumberOfLives,
                                label = { Text(text = stringResource(id = R.string.number_of_lives)) }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            MenuButton(
                                text = stringResource(id = R.string.create),
                                icon = Icons.Default.Add,
                                textColor = Color.White,
                                backgroundColor = indigoColor,
                                horizontalPadding = 20.dp
                            ) {
                                if (!numberOfLives.all { it.isDigit() } || numberOfLives.toInt() < 1) {
                                    Toast.makeText(context, context.getString(R.string.invalid_number_of_lives), Toast.LENGTH_SHORT).show()
                                } else {
                                    onSubmit(createGame(
                                        id = if (typeSelectedOption == context.getString(R.string.black_and_white)) GameTypes.blackAndWhite else GameTypes.coloured,
                                        type = if (typeSelectedOption == context.getString(R.string.black_and_white)) MainViewModel.blackAndWhite else MainViewModel.coloured,
                                        size = sizeSelectedOption.split("x")[0].toInt(),
                                        numberOfLives = numberOfLives.toInt()
                                    ))
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}