package com.neilb.nonogram.presentation.ui.views.create_nonogram

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neilb.nonogram.R
import com.neilb.nonogram.common.Resource
import com.neilb.nonogram.data.model.request.GameItem
import com.neilb.nonogram.data.model.request.GameRequest
import com.neilb.nonogram.data.model.request.SolvedTableItemItem
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.use_case.local.AddPuzzleLocally
import com.neilb.nonogram.domain.use_case.network.AddPuzzlePublicly
import com.neilb.nonogram.presentation.util.generateRandomString
import com.neilb.nonogram.presentation.util.isNetworkConnected
import com.neilb.nonogram.presentation.util.toHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNonogramViewModel @Inject constructor(
    private val addPuzzleLocally: AddPuzzleLocally,
    private val addPuzzlePublicly: AddPuzzlePublicly
) : ViewModel() {

    private val _game = MutableLiveData<Game?>(null)
    val game: LiveData<Game?> = _game

    fun updateGame(value: Game) {
        _game.value = value
    }

    private val _selectedBlock =
        MutableLiveData(Block(Block.empty))
    val selectedBlock: LiveData<Block> = _selectedBlock

    fun updateSelectedBlock(value: Block) {
        _selectedBlock.value = value
    }

    fun clickToCell(xValue: Int, yValue: Int, value: Block? = null) {
        val solvedTable = _game.value!!.solvedTable
        updateGame(_game.value!!.copy(solvedTable =
            solvedTable.mapIndexed { y, blocks ->
                blocks.mapIndexed { x, block ->
                    if (xValue == x && yValue == y) (value ?: _selectedBlock.value!!) else block
                }
            }
        ))
    }

    fun resetDesign(initialNumberOfLives: Int) {
        val initialTable = _game.value!!.solvedTable.map { it.map { Block(Block.notSelected) } }
        updateGame(_game.value!!.copy(numberOfLives = initialNumberOfLives, solvedTable = initialTable))
    }

    fun submitNonogram(context: Context, makerName: String? = null, dismissPage: () -> Unit) {
        viewModelScope.launch {
            val gameValue = _game.value!!
            if (makerName != null) {
                if (!context.isNetworkConnected()) {
                    Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show()
                    return@launch
                } else {
                    val gameRequest = GameRequest(
                        game = GameItem(
                            id = "public${generateRandomString(onlyDigits = true)}",
                            size = gameValue.size,
                            type = gameValue.type,
                            numberOfLives = gameValue.numberOfLives,
                            solvedTable = gameValue.solvedTable.map { blocks -> blocks.map { SolvedTableItemItem(status = if (it.status == Block.notSelected) Block.empty else it.status, color = it.color?.toHex()) } },
                            makerName = makerName
                        )
                    )
                    addPuzzlePublicly(gameRequest).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                Toast.makeText(context, context.getString(R.string.shared_publicly), Toast.LENGTH_SHORT).show()
                                dismissPage()
                            }
                            is Resource.Error -> {
                                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                            }
                            else -> {}
                        }
                    }
                }
            }
            addPuzzleLocally(gameValue.copy(
                id = "own${generateRandomString(onlyDigits = true)}",
                solvedTable = gameValue.solvedTable.map { blocks -> blocks.map { Block(status = if (it.status == Block.notSelected) Block.empty else it.status, color = it.color) } }
            ))
            if (makerName == null) {
                Toast.makeText(context, context.getString(R.string.saved_in_your_collection), Toast.LENGTH_SHORT).show()
                dismissPage()
            }
        }
    }

}