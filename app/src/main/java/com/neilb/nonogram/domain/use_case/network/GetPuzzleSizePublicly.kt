package com.neilb.nonogram.domain.use_case.network

import com.neilb.nonogram.data.service.ApiService
import javax.inject.Inject

class GetPuzzleSizePublicly @Inject constructor(
    private val apiService: ApiService
) {
    suspend operator fun invoke(): Int {
        val response = apiService.getPuzzleSize()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.size ?: 0
        }
        return 0
    }
}