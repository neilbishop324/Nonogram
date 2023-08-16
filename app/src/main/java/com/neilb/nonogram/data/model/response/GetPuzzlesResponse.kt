package com.neilb.nonogram.data.model.response

import com.google.gson.annotations.SerializedName
import com.neilb.nonogram.data.model.request.GameItem

data class GetPuzzlesResponse(

    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("games")
    val games: List<GameItem?>? = null

)