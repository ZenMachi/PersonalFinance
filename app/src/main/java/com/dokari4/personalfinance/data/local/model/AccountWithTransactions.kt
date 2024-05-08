package com.dokari4.personalfinance.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class AccountWithTransactions(
    val id: Int,
    val name: String,
    @ColumnInfo(name = "account_type")
    val accountType: String,
    val amount: Double,
    @ColumnInfo(name = "total_income")
    val totalIncome: Double,
    @ColumnInfo(name = "total_expense")
    val totalExpense: Double,
) : Parcelable
