package com.dokari4.personalfinance.presentation.home

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.core.domain.usecase.account.ViewAccountUseCase
import com.dokari4.core.domain.usecase.account.ViewAccountWithTransactionUseCase
import com.dokari4.core.domain.usecase.category.ViewCategoryUseCase
import com.dokari4.core.domain.usecase.home.ViewUsernameUseCase
import com.dokari4.core.domain.usecase.transaction.ViewTransactionUseCase
import com.dokari4.personalfinance.presentation.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    viewTransactionUseCase: ViewTransactionUseCase,
    viewAccountWithTransactionUseCase: ViewAccountWithTransactionUseCase,
    viewUsernameUseCase: ViewUsernameUseCase,
    viewAccountUseCase: ViewAccountUseCase,
    viewCategoryUseCase: ViewCategoryUseCase
) : ViewModel() {
    val getTransactions = viewTransactionUseCase()
    val getAccountsWithTransactions = viewAccountWithTransactionUseCase()
    val getUserName = viewUsernameUseCase()
    val getAccounts = viewAccountUseCase()
    val getCategories = viewCategoryUseCase()
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _balanceMoney = MutableStateFlow(0.0)
    val balanceMoney = _balanceMoney.asStateFlow()

    init {
        initValue()
    }

    private fun initValue() {
        viewModelScope.launch {
            combine(
                getUserName,
                getAccountsWithTransactions,
                getTransactions
            ) { username, accounts, transactions ->

                val totalIncome = accounts.sumOf { account ->
                    account.totalIncome
                }
                val totalExpense = accounts.sumOf { account ->
                    account.totalExpense
                }
                val amount = accounts.sumOf { account ->
                    account.amount
                }
                val balance = amount + totalIncome - totalExpense

                HomeUiState(
                    username = username,
                    balanceMoney = balance,
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    transactions = transactions,
                    isEmpty = transactions.isEmpty()
                )
            }.collect {
                _uiState.value = it
                setBalanceMoney(it.balanceMoney)
            }
        }
    }


    fun isContentEmpty(): Flow<Int> {
        return flow {
            return@flow getTransactions.collect {
                if (it.isEmpty()) {
                    emit(View.VISIBLE)
                } else {
                    emit(View.GONE)
                }
            }
        }
    }

    fun setBalanceMoney(balance: Double) {
        _balanceMoney.value = balance
    }
}