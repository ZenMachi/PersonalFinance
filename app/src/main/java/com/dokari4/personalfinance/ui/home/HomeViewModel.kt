package com.dokari4.personalfinance.ui.home

import androidx.lifecycle.ViewModel
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    val getTransactions = appUseCase.getTransactionList()
    val getAccountsWithTransactions = appUseCase.getAccountsWithTransactions()
    val getUserName = appUseCase.getUserName()
}