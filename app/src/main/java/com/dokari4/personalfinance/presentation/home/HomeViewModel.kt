package com.dokari4.personalfinance.presentation.home

import android.view.View
import androidx.lifecycle.ViewModel
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    val getTransactions = appUseCase.getTransactionList()
    val getAccountsWithTransactions = appUseCase.getAccountsWithTransactions()
    val getUserName = appUseCase.getUserName()
    val getAccounts = appUseCase.getAccountList()
    val getCategories = appUseCase.getCategoryList()

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
}