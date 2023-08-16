package com.neilb.nonogram.data.model.response

import com.google.gson.annotations.SerializedName

data class PuzzleSizeResponse(

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
