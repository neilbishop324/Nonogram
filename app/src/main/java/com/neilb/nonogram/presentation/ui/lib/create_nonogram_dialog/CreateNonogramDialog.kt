package com.neilb.nonogram.presentation.ui.lib.create_nonogram_dialog

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
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.neilb.nonogram.R
import com.neilb.nonogram.presentation.ui.theme.greenColor
import com.neilb.nonogram.presentation.ui.theme.orangeColor
import com.neilb.nonogram.presentation.ui.views.menu.components.MenuButton

@Composable
fun CreateNonogramDialog(
    visibility: Boolean,
    dismissDialog: () -> Unit,
    onSharePublicly: (String) -> Unit,
    onSaveToCollection: () -> Unit
) {

    val context = LocalContext.current

    var makerName by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }

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
                            Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.share_your_nonogram),
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(id = R.string.share_your_nonogram_question),
                                style = TextStyle(fontSize = 18.sp)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            TextField(
                                value = makerName,
                                onValueChange = { makerName = it },
                                label = {
                                    Text(text = stringResource(id = R.string.maker_name))
                                },
                                isError = errorMessage != null,
                                supportingText = {
                                    Text(text = errorMessage ?: stringResource(id = R.string.fill_it_if_you_want_to_share))
                                }
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            MenuButton(
                                text = stringResource(id = R.string.share_publicly),
                                backgroundColor = greenColor,
                                horizontalPadding = 20.dp,
                                textColor = Color.White,
                                icon = Icons.Default.Public
                            ) {
                                if (makerName.isEmpty()) {
                                    errorMessage = context.getString(R.string.maker_name_cant_be_empty)
                                } else {
                                    errorMessage = null
                                    onSharePublicly(makerName)
                                    dismissDialog()
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            MenuButton(
                                text = stringResource(id = R.string.add_to_your_collection),
                                backgroundColor = orangeColor,
                                horizontalPadding = 20.dp,
                                textColor = Color.White,
                                icon = Icons.Default.CollectionsBookmark
                            ) {
                                onSaveToCollection()
                                dismissDialog()
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            MenuButton(
                                text = stringResource(id = R.string.cancel),
                                backgroundColor = Color.Red,
                                horizontalPadding = 20.dp,
                                textColor = Color.White,
                                icon = Icons.Default.Close
                            ) {
                                dismissDialog()
                            }
                        }
                    }
                }
            }
        }
    }
}