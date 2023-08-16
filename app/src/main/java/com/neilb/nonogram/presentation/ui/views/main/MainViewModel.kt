package com.neilb.nonogram.presentation.ui.views.main

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neilb.nonogram.domain.model.Block
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.model.ProgressInGame
import com.neilb.nonogram.domain.use_case.local.AddProgress
import com.neilb.nonogram.domain.use_case.local.AddPuzzleLocally
import com.neilb.nonogram.domain.use_case.local.GetProgressById
import com.neilb.nonogram.domain.use_case.local.GetPuzzleById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val vibratorService: Vibrator,
    private val addPuzzleLocally: AddPuzzleLocally,
    private val addProgress: AddProgress,
    private val getProgressById: GetProgressById,
    private val getPuzzleById: GetPuzzleById
) : ViewModel() {

    companion object {
        const val blackAndWhite = 0
        const val coloured = 1
    }

    private val _game = MutableLiveData<Game?>(null)
    val game: LiveData<Game?> = _game

    private fun updateGame(value: Game) {
        _game.value = value
    }

    private val _table =
        MutableLiveData<List<List<Block>>>(arrayListOf())
    val table: LiveData<List<List<Block>>> = _table

    private fun updateTable(value: List<List<Block>>) {
        _table.value = value
    }

    private fun editTable(x: Int, y: Int, value: Block) {
        if (_table.value != null) {
            _table.value =
                ArrayList(_table.value!!.mapIndexed { yValue, list ->
                    if (yValue == y)
                        ArrayList(
                            list.mapIndexed { xValue, i ->
                                if (xValue == x) value
                                else i
                            }
                        )
                    else list
                })
        }
    }

    private val _selectedBlock =
        MutableLiveData(Block(Block.empty))
    val selectedBlock: LiveData<Block> = _selectedBlock

    fun updateSelectedBlock(value: Block) {
        _selectedBlock.value = value
    }

    private val _remainingLives =
        MutableLiveData(3)
    val remainingLives: LiveData<Int> = _remainingLives

    private fun updateRemainingLives(value: Int) {
        _remainingLives.value = value
    }

    private fun resetTable() {
        updateTable(
            ArrayList(Array(_game.value?.size ?: 5) {
                ArrayList(Array(_game.value?.size ?: 5) {
                    Block(Block.notSelected)
                }.toList())
            }.toList())
        )
    }

    fun openGame(game: Game) {
        viewModelScope.launch {
            val lastGame = getPuzzleById(game.id)
            val lastGameProgress = getProgressById(game.id)
            if (lastGame != null && lastGameProgress != null) {
                updateGame(lastGame)
                updateTable(lastGameProgress.table)
                updateRemainingLives(lastGameProgress.remainingLives)
            } else {
                createGame(game)
            }
        }
    }

    fun createGame(game: Game, saveLocally: Boolean = true) {
        viewModelScope.launch {
            updateGame(game)
            resetTable()
            updateRemainingLives(game.numberOfLives)
            if (saveLocally) {
                addPuzzleLocally(game)
                addProgress(ProgressInGame(game.id, _table.value!!, game.numberOfLives))
            }
        }
    }

    fun clickCell(
        x: Int,
        y: Int,
        onFailedGame: () -> Unit,
        onFinishedGame: () -> Unit,
    ) {
        viewModelScope.launch {
            val remainingBlock = _table.value!![y][x]

            if (_table.value == null ||
                _game.value == null ||
                remainingBlock.status != Block.notSelected ||
                _remainingLives.value == 0) {
                return@launch
            }

            val selectedBlock = _selectedBlock.value

            if (selectedBlock != _game.value!!.solvedTable[y][x]) {
                _remainingLives.value = (_remainingLives.value ?: 0) - 1
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibratorService.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibratorService.vibrate(200)
                }
                if (_remainingLives.value == 0) {
                    onFailedGame()
                }
            }

            editTable(x, y, _game.value!!.solvedTable[y][x])
            val finishedTable = _table.value!!.map { blocks -> blocks.map { if (it.status == Block.notSelected) Block(Block.empty) else it } }

            if (finishedTable == _game.value!!.solvedTable) {
                onFinishedGame()
            }

            val rowFinished = _table.value!![y].map { if (it.status == Block.notSelected) Block(Block.empty) else it }
            if (rowFinished == _game.value!!.solvedTable[y].toList()) {
                _table.value!![y].forEachIndexed { index, block ->
                    if (block.status == Block.notSelected) {
                        editTable(index, y, Block(Block.empty))
                    }
                }
            }

            val columnFinished = _table.value!!.map { it[x] }.map { if (it.status == Block.notSelected) Block(Block.empty) else it }
            if (columnFinished == _game.value!!.solvedTable.map { it[x] }.toList()) {
                _table.value!!.map { it[x] }.forEachIndexed { index, block ->
                    if (block.status == Block.notSelected) {
                        editTable(x, index, Block(Block.empty))
                    }
                }
            }

            addProgress(ProgressInGame(_game.value!!.id, _table.value!!, _remainingLives.value!!))
        }
    }

    fun restartGame() {
        viewModelScope.launch {
            resetTable()
            updateRemainingLives(_game.value!!.numberOfLives)
            addProgress(ProgressInGame(_game.value!!.id, _table.value!!, _game.value!!.numberOfLives))
        }
    }

}