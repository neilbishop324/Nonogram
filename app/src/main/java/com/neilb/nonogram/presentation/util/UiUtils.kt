package com.neilb.nonogram.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.neilb.nonogram.R
import com.neilb.nonogram.common.GameTypes
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.presentation.ui.views.main.MainViewModel

@Composable
fun getGameTitle(gameType: Int): String {
    return if (gameType == MainViewModel.blackAndWhite) {
        stringResource(id = R.string.blackAndWhite)
    } else {
        stringResource(id = R.string.coloured)
    }
}

fun getColorsDistribution(gameType: Int, list: ArrayList<Block>) : ArrayList<Pair<Int, Color?>> {
    val colorsDistributionList = arrayListOf<Pair<Int, Color?>>()
    var colorSize = 0
    var lastBlock: Block? = null
    val listWithExtra = list + null
    listWithExtra.forEach { block ->
        if (block == null ||
            (block.status == Block.empty && colorSize != 0) ||
            (lastBlock != null) && lastBlock!!.status == block.status && lastBlock!!.color != block.color) {
            if (colorSize != 0)
                colorsDistributionList.add(colorSize to if (gameType == MainViewModel.blackAndWhite) null else lastBlock?.color)
            colorSize = 0
        }
        if (block?.status != Block.empty) {
            colorSize++
        }
        lastBlock = block
    }
    return colorsDistributionList
}

fun createGame(
    id: String = GameTypes.blackAndWhite,
    type: Int = MainViewModel.blackAndWhite,
    colors: ArrayList<Color> = arrayListOf(),
    size: Int = (5..15).random(),
    numberOfLives: Int = 3
): Game {

    if (colors.isEmpty()) {
        val colorSize = 5

        repeat(colorSize) {
            colors.add(generateRandomColor())
        }
    }

    return Game(
        id,
        size,
        type,
        solvedTable = ArrayList(Array(size) {
            ArrayList(Array(size) {
                val status = listOf(
                    Block.empty,
                    if (type == MainViewModel.blackAndWhite)
                        Block.black
                    else
                        Block.coloured
                ).random()
                val color = if (status == Block.coloured) colors.random() else null
                Block(status, color)
            }.toList())
        }.toList()),
        numberOfLives
    )
}