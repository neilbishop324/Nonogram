package com.neilb.nonogram.presentation.ui.lib.color_selector_dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.neilb.nonogram.R
import com.neilb.nonogram.presentation.ui.theme.greenColor
import com.neilb.nonogram.presentation.util.colorIsLight
import com.neilb.nonogram.presentation.util.toHex

@Composable
fun ColorSelectorDialog(
    visibility: Boolean,
    dismissDialog: () -> Unit,
    onSubmit: (Color) -> Unit
) {
    val controller = rememberColorPickerController()
    var selectedColor by remember {
        mutableStateOf(controller.selectedColor.value)
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
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.92f),
                    ) {
                        Column(
                            Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.select_a_color),
                                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            HsvColorPicker(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(240.dp),
                                controller = controller,
                                onColorChanged = {
                                    selectedColor = it.color
                                }
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            AlphaSlider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .height(35.dp),
                                controller = controller,
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            BrightnessSlider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .height(35.dp),
                                controller = controller,
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                    Card(
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = selectedColor
                                        )
                                    ) {
                                        Text(
                                            text = "${stringResource(id = R.string.your_color)}: ${selectedColor.toHex()}",
                                            color = if (colorIsLight(selectedColor)) Color.Black else Color.White,
                                            modifier = Modifier
                                                .padding(horizontal = 20.dp, vertical = 8.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(20.dp))

                                    Button(
                                        onClick = {
                                            onSubmit(controller.selectedColor.value)
                                            dismissDialog()
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = greenColor
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = stringResource(id = R.string.add_color))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}