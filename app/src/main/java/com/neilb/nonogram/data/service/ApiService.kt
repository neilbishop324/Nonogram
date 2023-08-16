package com.neilb.nonogram.data.service

import com.neilb.nonogram.data.model.request.GameRequest
import com.neilb.nonogram.data.model.response.GameResponse
import com.neilb.nonogram.data.model.response.GetPuzzlesResponse
import com.neilb.nonogram.data.model.response.PuzzleSizeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("puzzles")
    suspend fun getPublicPuzzles(@Query("limit") limit: Int? = null, @Query("skip") skip: Int? = null) : Response<GetPuzzlesResponse>

    @POST("addPuzzle")
    suspend fun addPublicPuzzle(@Body request: GameRequest) : Response<GameResponse>

    @GET("puzzleSize")
    suspend fun getPuzzleSize() : Response<PuzzleSizeResponse>

}