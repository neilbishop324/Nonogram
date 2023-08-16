package com.neilb.nonogram.domain.use_case.local

import com.neilb.nonogram.domain.repository.PuzzleRepository
import javax.inject.Inject

class GetPublicPuzzleSizeLocally @Inject constructor(
    private val puzzleRepository: PuzzleRepository
) {
    suspend operator fun invoke(): Int {
        return puzzleRepository.getPublicPuzzleSize() ?: 0
    }
}