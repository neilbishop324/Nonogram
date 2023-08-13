package com.neilb.nonogram.domain.use_case

import com.neilb.nonogram.domain.model.ProgressInGame
import com.neilb.nonogram.domain.repository.PuzzleRepository
import javax.inject.Inject

class GetProgressById @Inject constructor(
    private val puzzleRepository: PuzzleRepository
) {

    suspend operator fun invoke(id: String): ProgressInGame? {
        return puzzleRepository.getProgressById(id)
    }

}