package com.neilb.nonogram.presentation.util

import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.neilb.nonogram.R
import com.neilb.nonogram.common.GameTypes
import com.neilb.nonogram.data.model.request.GameItem
import com.neilb.nonogram.data.model.request.SolvedTableItemItem
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

fun getColorsDistribution(gameType: Int, list: ArrayList<Block>): ArrayList<Pair<Int, Color?>> {
    val colorsDistributionList = arrayListOf<Pair<Int, Color?>>()
    var colorSize = 0
    var lastBlock: Block? = null
    val listWithExtra = list + null
    listWithExtra.forEach { block ->
        if (block == null ||
            ((block.status == Block.empty || block.status == Block.notSelected) && colorSize != 0) ||
            (lastBlock != null) && lastBlock!!.status == block.status && lastBlock!!.color != block.color
        ) {
            if (colorSize != 0)
                colorsDistributionList.add(colorSize to if (gameType == MainViewModel.blackAndWhite) null else lastBlock?.color)
            colorSize = 0
        }
        if (block?.status != Block.empty && block?.status != Block.notSelected) {
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
    numberOfLives: Int = 3,
    emptyTable: Boolean = false
): Game {

    if (colors.isEmpty()) {
        val colorSize = 5

        repeat(colorSize) {
            colors.add(generateRandomColor())
        }
    }

    val possibilities = arrayListOf(if (emptyTable) Block.notSelected else Block.empty)
    if (!emptyTable) {
        possibilities.add(
            if (type == MainViewModel.blackAndWhite)
                Block.black
            else
                Block.coloured
        )
    }

    return Game(
        id,
        size,
        type,
        solvedTable = Array(size) {
            Array(size) {
                val status = possibilities.random()
                val color = if (status == Block.coloured) colors.random() else null
                Block(status, color)
            }.toList()
        }.toList(),
        numberOfLives
    )
}

fun Game.toGameItem(makerName: String? = null): GameItem {
    return GameItem(
        this.size,
        this.id,
        makerName,
        this.type,
        this.numberOfLives,
        this.solvedTable.map { blocks ->
            blocks.map {
                SolvedTableItemItem(
                    it.color?.toHex(),
                    it.status
                )
            }
        }
    )
}

fun GameItem.toGame(): Game {
    return Game(
        this.id ?: "",
        this.size ?: 0,
        this.type ?: 0,
        this.solvedTable?.map { blocks ->
            blocks?.mapNotNull {
                Block(
                    it?.status ?: 0,
                    if (it?.color != null) Color(android.graphics.Color.parseColor(it.color)) else null
                )
            } ?: listOf()
        } ?: listOf(),
        this.numberOfLives ?: 3,
    )
}

@Composable
fun LazyStaggeredGridState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) {
        mutableIntStateOf(firstVisibleItemIndex)
    }
    var previousScrollOffset by remember(this) {
        mutableIntStateOf(firstVisibleItemScrollOffset)
    }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}

fun generateColor(input: String): Color {
    val hashCode = input.hashCode()

    val red = (hashCode and 0xFF0000) shr 16
    val green = (hashCode and 0x00FF00) shr 8
    val blue = hashCode and 0x0000FF

    return Color(red, green, blue)
}
