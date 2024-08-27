package com.dokari4.personalfinance.presentation.home.state

import android.os.Parcelable
import com.dokari4.core.domain.model.Transaction
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionListState(
    val id: Int? = null,
    val accountType: String,
    val categoryType: String,
    val name: String,
    val description: String,
    val date: String,
    val time: String,
    val type: String,
    val amount: Double,
    val data: Transaction
): Parcelable

data class HomeUiState(
    val username: String? = null,
    val balanceMoney: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val transactions: List<TransactionListState> = emptyList(),
    val isEmpty: Boolean? = null
)
