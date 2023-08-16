package com.neilb.nonogram.domain.use_case.network

import com.neilb.nonogram.common.Resource
import com.neilb.nonogram.data.model.response.GetPuzzlesResponse
import com.neilb.nonogram.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPublicPuzzlesPublicly  @Inject constructor(
    private val apiRepository: ApiRepository
) {
    operator fun invoke(limit: Int? = null, skip: Int? = null) : Flow<Resource<GetPuzzlesResponse>> = flow {
        emit(Resource.Loading())
        val response = apiRepository.getPublicPuzzles(limit, skip)

        val resBody = response.getOrNull()

        if (response.isFailure) {
            emit(Resource.Error(response.exceptionOrNull()!!.message!!))
        } else if (resBody == null) {
            emit(Resource.Error("An error occurred!"))
        } else if (response.isSuccess) {
            emit(Resource.Success(resBody))
        }
    }
}