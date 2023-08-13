package com.neilb.nonogram.presentation.ui.views.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.presentation.ui.theme.goldColor
import com.neilb.nonogram.presentation.ui.theme.indigoColor
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel

@Composable
fun BlockSelector(
    modifier: Modifier = Modifier,
    game: Game,
    mainViewModel: MainViewModel,
    selectedBlock: Block
) {
    Row(
        modifier
    ) {
        BlockSelectorItem(
            selected = selectedBlock == Block(Block.empty),
            backgroundColor = Color.Black,
            isEmpty = true
        ) {
            mainViewModel.updateSelectedBlock(Block(Block.empty))
        }
        Spacer(modifier = Modifier.width(4.dp))
        if (game.type == MainViewModel.blackAndWhite) {
            BlockSelectorItem(
                selected = selectedBlock == Block(Block.black),
                backgroundColor = Color.Black,
                isEmpty = false
            ) {
                mainViewModel.updateSelectedBlock(Block(Block.black))
            }
        } else {
            val colors = game.solvedTable.flatten().mapNotNull { it.color }.toSet().toList()
            repeat(colors.size) {
                BlockSelectorItem(
                    selected = selectedBlock == Block(Block.coloured, colors[it]),
                    backgroundColor = colors[it],
                    isEmpty = false
                ) {
                    mainViewModel.updateSelectedBlock(Block(Block.coloured, colors[it]))
                }
                Spacer(modifier = Modifier.width(4.dp))
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
        modifier = modifier.border(2.dp, goldColor, CircleShape)
    }

    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isEmpty) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
            )
        }
    }
}