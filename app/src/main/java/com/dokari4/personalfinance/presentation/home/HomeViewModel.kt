package com.dokari4.personalfinance.presentation.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.dokari4.core.domain.usecase.account.ViewAccountUseCase
import com.dokari4.core.domain.usecase.account.ViewAccountWithTransactionUseCase
import com.dokari4.core.domain.usecase.category.ViewCategoryUseCase
import com.dokari4.core.domain.usecase.home.ViewUsernameUseCase
import com.dokari4.core.domain.usecase.transaction.ViewTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val viewTransactionUseCase: ViewTransactionUseCase,
    private val viewAccountWithTransactionUseCase: ViewAccountWithTransactionUseCase,
    private val viewUsernameUseCase: ViewUsernameUseCase,
    private val viewAccountUseCase: ViewAccountUseCase,
    private val viewCategoryUseCase: ViewCategoryUseCase
) : ViewModel() {
    val getTransactions = viewTransactionUseCase()
    val getAccountsWithTransactions = viewAccountWithTransactionUseCase()
    val getUserName = viewUsernameUseCase()
    val getAccounts = viewAccountUseCase()
    val getCategories = viewCategoryUseCase()

    private val _balanceMoney = MutableStateFlow(0.0)
    val balanceMoney = _balanceMoney.asStateFlow()


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