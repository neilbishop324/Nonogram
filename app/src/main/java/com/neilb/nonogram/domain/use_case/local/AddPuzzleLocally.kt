package com.neilb.nonogram.domain.use_case.local

import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.repository.PuzzleRepository
import javax.inject.Inject

class AddPuzzleLocally @Inject constructor(
    private val puzzleRepository: PuzzleRepository
) {

    suspend operator fun invoke(game: Game) {
        puzzleRepository.insertPuzzle(game)
    }

}