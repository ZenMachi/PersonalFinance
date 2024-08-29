package com.dokari4.personalfinance.presentation.accounts.state

import android.os.Parcelable
import com.dokari4.core.domain.model.AccountWithTransactions
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class AccountListState(
    val id: Int,
    val balance: BigDecimal,
    val income: BigDecimal,
    val expense: BigDecimal,
    val name: String,
    val type: String,
    val data: AccountWithTransactions
): Parcelable

data class AccountsUiState(
    val accounts: List<AccountListState> = emptyList(),
    val isEmpty: Boolean? = null
)
