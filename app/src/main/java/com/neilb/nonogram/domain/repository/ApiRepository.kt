package com.neilb.nonogram.domain.repository

import com.neilb.nonogram.data.model.request.GameRequest
import com.neilb.nonogram.data.model.response.GameResponse
import com.neilb.nonogram.data.model.response.GetPuzzlesResponse
import com.neilb.nonogram.data.model.response.PuzzleSizeResponse

interface ApiRepository {

    suspend fun getPublicPuzzles(limit: Int? = null, skip: Int? = null) : Result<GetPuzzlesResponse>

    suspend fun addPublicPuzzle(request: GameRequest) : Result<GameResponse>

    suspend fun getPuzzleSize() : Result<PuzzleSizeResponse>

}