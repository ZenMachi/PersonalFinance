package com.dokari4.personalfinance.presentation.home.state

import com.dokari4.core.domain.model.Transaction

data class HomeUiState(
    val username: String = "",
    val balanceMoney: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val transactions: List<Transaction> = emptyList(),
    val isEmpty: Boolean? = null
)
