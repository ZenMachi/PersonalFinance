package com.dokari4.personalfinance.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dokari4.core.domain.usecase.account.ViewAccountWithTransactionUseCase
import com.dokari4.core.domain.usecase.category.ViewCategoryUseCase
import com.dokari4.core.domain.usecase.home.ViewUsernameUseCase
import com.dokari4.core.domain.usecase.transaction.ViewTransactionUseCase
import com.dokari4.core.util.DateConverter
import com.dokari4.personalfinance.presentation.home.state.HomeUiState
import com.dokari4.personalfinance.presentation.home.state.TransactionListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    viewTransactionUseCase: ViewTransactionUseCase,
    viewAccountWithTransactionUseCase: ViewAccountWithTransactionUseCase,
    viewUsernameUseCase: ViewUsernameUseCase,
    viewCategoryUseCase: ViewCategoryUseCase
) : ViewModel() {
    private val getTransactions = viewTransactionUseCase()
    private val getAccountsWithTransactions = viewAccountWithTransactionUseCase()
    private val getUserName = viewUsernameUseCase()
    private val getCategories = viewCategoryUseCase()
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
                getTransactions,
                getCategories
            ) { username, accounts, transactions, categories ->

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

                val listTransaction = transactions.map { transaction ->
                    val date = DateConverter.setTimeToDate(transaction.dateTime)
                    val time = DateConverter.setTimeToHourAndMinutes(transaction.dateTime)
                    val account = accounts.find { it.id == transaction.accountId }
                    val accountType = account?.accountType ?: "Not Defined"
                    val category = categories.find { it.id == transaction.categoryId }
                    val categoryType = category?.name ?: "Not Defined"

                    TransactionListState(
                        id = transaction.id,
                        accountType = accountType,
                        categoryType = categoryType,
                        name = transaction.name,
                        description = transaction.description,
                        date = date,
                        time = time,
                        type = transaction.type,
                        amount = transaction.amount,
                        data = transaction
                    )
                }


                HomeUiState(
                    username = username,
                    balanceMoney = balance,
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    transactions = listTransaction,
                    isEmpty = transactions.isEmpty()
                )
            }.collect {
                _uiState.value = it
                setBalanceMoney(it.balanceMoney)
            }
        }
    }

    private fun setBalanceMoney(balance: Double) {
        _balanceMoney.value = balance
    }
}