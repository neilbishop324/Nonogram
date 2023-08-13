package com.neilb.nonogram.domain.use_case

import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.repository.PuzzleRepository
import javax.inject.Inject

class GetPuzzleById @Inject constructor(
    private val puzzleRepository: PuzzleRepository
) {

    suspend operator fun invoke(id: String): Game? {
        return puzzleRepository.getPuzzleById(id)
    }

}