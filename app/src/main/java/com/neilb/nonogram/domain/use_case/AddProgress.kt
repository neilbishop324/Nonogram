package com.neilb.nonogram.domain.use_case

import com.neilb.nonogram.domain.model.ProgressInGame
import com.neilb.nonogram.domain.repository.PuzzleRepository
import javax.inject.Inject

class AddProgress @Inject constructor(
    private val puzzleRepository: PuzzleRepository
) {

    suspend operator fun invoke(progressInGame: ProgressInGame) {
        return puzzleRepository.insertProgress(progressInGame)
    }

}