package com.dokari4.personalfinance.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.dokari4.personalfinance.domain.model.AccountWithTransactions
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import com.dokari4.personalfinance.util.CurrencyConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    val getTransactions = appUseCase.getTransactionList().toLiveData()
    val getAccountsWithTransactions = appUseCase.getAccountsWithTransactions().toLiveData()
    val getUserName = appUseCase.getUserName().toLiveData()
}