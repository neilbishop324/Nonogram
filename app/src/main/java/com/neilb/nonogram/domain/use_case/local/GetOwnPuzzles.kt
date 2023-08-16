package com.neilb.nonogram.domain.use_case.local

import com.neilb.nonogram.domain.model.Game
import com.neilb.nonogram.domain.repository.PuzzleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOwnPuzzles @Inject constructor(
    private val puzzleRepository: PuzzleRepository
) {

    operator fun invoke(limit: Int, skip: Int): Flow<List<Game>> {
        return puzzleRepository.getOwnPuzzles(limit, skip)
    }

}