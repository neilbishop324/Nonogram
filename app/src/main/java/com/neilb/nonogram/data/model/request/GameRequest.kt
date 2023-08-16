package com.neilb.nonogram.data.model.request

import com.google.gson.annotations.SerializedName

data class GameRequest(

	@field:SerializedName("game")
	val game: GameItem? = null

)

data class GameItem(

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

    @field:SerializedName("makerName")
    val makerName: String? = null,

	@field:SerializedName("type")
	val type: Int? = null,

	@field:SerializedName("numberOfLives")
	val numberOfLives: Int? = null,

	@field:SerializedName("solvedTable")
	val solvedTable: List<List<SolvedTableItemItem?>?>? = null

)

data class SolvedTableItemItem(

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("status")
	val status: Int? = null

)
