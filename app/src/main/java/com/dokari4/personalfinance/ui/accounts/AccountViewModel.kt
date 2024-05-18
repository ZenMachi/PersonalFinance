package com.dokari4.personalfinance.ui.accounts

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.toLiveData
import androidx.lifecycle.viewModelScope
import com.dokari4.personalfinance.data.State
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.domain.usecase.AppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {

    val getAccounts = appUseCase.getAccountList()

    val accountsWithTransactions = appUseCase.getAccountsWithTransactions()
    fun getExpense(accountId: Int) = appUseCase.getAccountExpenseList(accountId)
    fun getIncome(accountId: Int) = appUseCase.getAccountIncomeList(accountId)

    fun isValid(): Flow<Int> {
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

    suspend fun insertAccount(account: Account) = viewModelScope.async {
        appUseCase.insertAccount(account)
    }

    suspend fun insertUser(user: User) = viewModelScope.async {
        appUseCase.insertUser(user)
    }

    suspend fun insertCategory(category: Category) = viewModelScope.async {
        appUseCase.insertCategory(category)
    }
}