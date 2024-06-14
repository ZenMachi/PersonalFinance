package com.dokari4.personalfinance.presentation.accounts

import android.view.View
import androidx.lifecycle.ViewModel
import com.dokari4.core.domain.usecase.account.ViewAccountWithTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val viewAccountWithTransactionUseCase: ViewAccountWithTransactionUseCase
) : ViewModel() {

    val accountsWithTransactions = viewAccountWithTransactionUseCase()

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