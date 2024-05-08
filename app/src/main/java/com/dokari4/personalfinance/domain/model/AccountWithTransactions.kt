package com.dokari4.personalfinance.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class AccountWithTransactions(
    val id: Int,
    val name: String,
    val accountType: String,
    val amount: Double,
    val totalIncome: Double,
    val totalExpense: Double,
) : Parcelable
