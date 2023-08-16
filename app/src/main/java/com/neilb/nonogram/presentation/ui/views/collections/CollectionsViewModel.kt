package com.neilb.nonogram.presentation.ui.views.collections

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neilb.nonogram.R
import com.neilb.nonogram.common.Resource
import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.use_case.local.AddPuzzleLocally
import com.neilb.nonogram.domain.use_case.local.DeleteAllPublicPuzzles
import com.neilb.nonogram.domain.use_case.local.DeletePuzzle
import com.neilb.nonogram.domain.use_case.local.GetOwnPuzzleSize
import com.neilb.nonogram.domain.use_case.local.GetOwnPuzzles
import com.neilb.nonogram.domain.use_case.local.GetPublicPuzzleSizeLocally
import com.neilb.nonogram.domain.use_case.local.GetPublicPuzzlesLocally
import com.neilb.nonogram.domain.use_case.network.GetPublicPuzzlesPublicly
import com.neilb.nonogram.domain.use_case.network.GetPuzzleSizePublicly
import com.neilb.nonogram.presentation.util.isNetworkConnected
import com.neilb.nonogram.presentation.util.toGame
import com.neilb.nonogram.presentation.util.toGameItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val getPublicPuzzlesLocally: GetPublicPuzzlesLocally,
    private val getPublicPuzzlesPublicly: GetPublicPuzzlesPublicly,
    private val getOwnPuzzles: GetOwnPuzzles,
    private val deleteAllPublicPuzzles: DeleteAllPublicPuzzles,
    private val addPuzzleLocally: AddPuzzleLocally,
    private val getPublicPuzzleSizeLocally: GetPublicPuzzleSizeLocally,
    private val getOwnPuzzleSize: GetOwnPuzzleSize,
    private val getPublicPuzzleSizePublicly: GetPuzzleSizePublicly,
    private val deletePuzzle: DeletePuzzle,
) : ViewModel() {

    companion object {
        const val limit = 12
    }

    private val _documentCount = mutableIntStateOf(0)
    val documentCount: State<Int> = _documentCount

    fun getDocumentSize(context: Context, isPublic: Boolean) {
        viewModelScope.launch {
            _documentCount.intValue =
                if (isPublic) {
                    if (context.isNetworkConnected()) {
                        getPublicPuzzleSizePublicly()
                    } else {
                        getPublicPuzzleSizeLocally()
                    }
                } else {
                    getOwnPuzzleSize()
                }
        }
    }

    private val _state = mutableStateOf(CollectionsState())
    val state: State<CollectionsState> = _state

    private val _skipNumberInLimit = MutableLiveData(0)
    val skipNumberInLimit: LiveData<Int> = _skipNumberInLimit

    fun updateSkipNumberInLimit(value: Int, context: Context, isPublic: Boolean) {
        _skipNumberInLimit.value = value
        getCollections(context, isPublic)
    }

    fun getCollections(context: Context, isPublic: Boolean) {
        viewModelScope.launch {
            _state.value = CollectionsState(isLoading = true)
            if (isPublic) {
                if (context.isNetworkConnected()) {
                    getPublicPuzzlesPublicly(limit, _skipNumberInLimit.value).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.value =
                                    CollectionsState(data = result.data?.games?.filterNotNull())
                                savePublicCollections(result.data?.games?.mapNotNull { it?.toGame() })
                            }

                            is Resource.Error -> {
                                _state.value = CollectionsState(error = result.message)
                            }

                            else -> {}
                        }
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.you_are_offline), Toast.LENGTH_SHORT).show()
                    getPublicPuzzlesLocally(limit, _skipNumberInLimit.value!!).collect { games ->
                        _state.value =
                            CollectionsState(data = games.map { game -> game.toGameItem() })
                    }
                }
            } else {
                getOwnPuzzles(limit, _skipNumberInLimit.value!!).collect { games ->
                    _state.value = CollectionsState(data = games.map { game -> game.toGameItem() })
                }
            }
        }
    }

    fun deleteGameLocally(game: Game, context: Context) {
        viewModelScope.launch {
            deletePuzzle(game)
            getCollections(context, false)
            getDocumentSize(context, false)
        }
    }

    private fun savePublicCollections(games: List<Game>?) {
        if (games == null) return

        viewModelScope.launch(Dispatchers.IO) {
            deleteAllPublicPuzzles()
            games.forEach {
                addPuzzleLocally(it)
            }
        }

    }

}