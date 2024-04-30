package com.dokari4.personalfinance.ui.add_transaction

import androidx.lifecycle.ViewModel
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(private val appUseCase: AppUseCase) :
    ViewModel() {
    fun insertTransaction(transaction: Transaction) = appUseCase.insertTransaction(transaction)
}