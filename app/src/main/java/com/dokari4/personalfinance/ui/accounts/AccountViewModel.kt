package com.dokari4.personalfinance.ui.accounts

import android.view.View
import androidx.lifecycle.ViewModel
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {

    val accountsWithTransactions = appUseCase.getAccountsWithTransactions()

    fun isContentEmpty(): Flow<Int> {
        return flow {
            return@flow accountsWithTransactions.collect {
                if (it.isEmpty()) {
                    emit(View.VISIBLE)
                } else {
                    emit(View.GONE)
                }
            }
        }
    }

}