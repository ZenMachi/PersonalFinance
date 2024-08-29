package com.dokari4.personalfinance.presentation.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.core.domain.usecase.account.ViewAccountWithTransactionUseCase
import com.dokari4.personalfinance.presentation.accounts.state.AccountListState
import com.dokari4.personalfinance.presentation.accounts.state.AccountsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    viewAccountWithTransactionUseCase: ViewAccountWithTransactionUseCase
) : ViewModel() {

    private val accountsWithTransactions = viewAccountWithTransactionUseCase()

    private val _state = MutableStateFlow(AccountsUiState())
    val state = _state.asStateFlow()

    init {
        initValue()
    }

    private fun initValue() {
        viewModelScope.launch {
            accountsWithTransactions.collect { accounts ->
                val listAccount = accounts.map { account ->
                    val balance = account.amount + account.totalIncome - account.totalExpense
                    AccountListState(
                        id = account.id,
                        balance = balance.toBigDecimal(),
                        income = account.totalIncome.toBigDecimal(),
                        expense = account.totalExpense.toBigDecimal(),
                        name = account.name,
                        type = account.accountType,
                        data = account
                    )
                }
                _state.value = AccountsUiState(accounts = listAccount, isEmpty = accounts.isEmpty())
            }
        }
    }

}