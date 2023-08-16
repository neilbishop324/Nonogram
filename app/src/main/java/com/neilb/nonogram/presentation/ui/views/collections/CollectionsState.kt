package com.neilb.nonogram.presentation.ui.views.collections

import com.neilb.nonogram.data.model.request.GameItem

data class CollectionsState(
    val isLoading: Boolean = false,
    val data: List<GameItem>? = null,
    val error: String? = null
)