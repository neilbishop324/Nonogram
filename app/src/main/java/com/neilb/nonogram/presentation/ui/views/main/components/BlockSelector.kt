package com.neilb.nonogram.presentation.ui.views.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.neilb.nonogram.R
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.presentation.ui.lib.color_selector_dialog.ColorSelectorDialog
import com.neilb.nonogram.presentation.ui.theme.goldColor
import com.neilb.nonogram.presentation.ui.theme.greenColor
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel

@Composable
fun BlockSelector(
    modifier: Modifier = Modifier,
    game: Game,
    selectedBlock: Block,
    updateSelectedBlock: (Block) -> Unit,
    isDesigner: Boolean = false
) {

    val designerColors = remember { mutableStateListOf<Color>() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row {
            BlockSelectorItem(
                selected = selectedBlock == Block(Block.empty),
                backgroundColor = Color.Black,
                isEmpty = true
            ) {
                updateSelectedBlock(Block(Block.empty))
            }
            Spacer(modifier = Modifier.width(4.dp))
            if (game.type == MainViewModel.blackAndWhite) {
                BlockSelectorItem(
                    selected = selectedBlock == Block(Block.black),
                    backgroundColor = Color.Black,
                    isEmpty = false
                ) {
                    updateSelectedBlock(Block(Block.black))
                }
            } else {
                val colors =
                    designerColors.ifEmpty { game.solvedTable.flatten().mapNotNull { it.color }.toSet().toList() }
                repeat(colors.size) {
                    BlockSelectorItem(
                        selected = selectedBlock == Block(Block.coloured, colors[it]),
                        backgroundColor = colors[it],
                        isEmpty = false
                    ) {
                        updateSelectedBlock(Block(Block.coloured, colors[it]))
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }

        if (game.type == MainViewModel.coloured && isDesigner) {

            var colorDialogVisibility by remember { mutableStateOf(false) }

            ColorSelectorDialog(
                visibility = colorDialogVisibility,
                dismissDialog = { colorDialogVisibility = false },
                onSubmit = {
                    if (!designerColors.contains(it))
                        designerColors.add(it)
                }
            )

            Spacer(modifier = Modifier.height(4.dp))
            Button(onClick = { colorDialogVisibility = true }, colors = ButtonDefaults.buttonColors(
                containerColor = greenColor
            )) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = stringResource(id = R.string.add_color))
            }
        }
    }
}

@Composable
fun BlockSelectorItem(
    selected: Boolean,
    backgroundColor: Color,
    isEmpty: Boolean,
    onClick: () -> Unit
) {
    var modifier: Modifier = Modifier
        .padding(8.dp)
        .size(36.dp)
        .background(backgroundColor, CircleShape)
        .clip(CircleShape)

    if (selected) {
        modifier = modifier.border(3.dp, goldColor, CircleShape)
    }

    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isEmpty) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}