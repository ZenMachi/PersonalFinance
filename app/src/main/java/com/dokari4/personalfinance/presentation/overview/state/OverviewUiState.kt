package com.dokari4.personalfinance.presentation.overview.state

import java.math.BigDecimal

data class CategoryState(
    val id: Int? = null,
    val name: String,
    val count: Int,
    val amount: BigDecimal,
)

data class OverviewUiState(
    val categories: List<CategoryState> = emptyList(),
    val isEmpty: Boolean? = null
)
