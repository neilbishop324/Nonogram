package com.neilb.nonogram.domain.use_case

import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.repository.PuzzleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePuzzle @Inject constructor(
    private val puzzleRepository: PuzzleRepository
) {

    suspend operator fun invoke(game: Game) {
        return puzzleRepository.deletePuzzle(game)
    }

}