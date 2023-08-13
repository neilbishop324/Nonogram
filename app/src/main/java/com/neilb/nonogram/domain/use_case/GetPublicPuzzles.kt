package com.neilb.nonogram.domain.use_case

import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.repository.PuzzleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPublicPuzzles @Inject constructor(
    private val puzzleRepository: PuzzleRepository
) {

    operator fun invoke(): Flow<List<Game>> {
        return puzzleRepository.getPublicPuzzles()
    }

}