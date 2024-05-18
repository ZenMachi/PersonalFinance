package com.dokari4.personalfinance.domain.usecase

import com.dokari4.personalfinance.data.State
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.AccountWithTransactions
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.util.OnboardingState
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

interface AppUseCase {

    fun getAccountList(): Flow<List<Account>>
    suspend fun insertAccount(account: Account)
    suspend fun insertUser(user: User)
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun insertCategory(category: Category)
    fun getUserName(): Flow<String>
    fun getTransactionList(): Flow<List<Transaction>>
    fun getCategoryList(): Flow<List<Category>>
    fun getAccountExpenseList(accountId: Int): Flow<List<Transaction>>
    fun getAccountIncomeList(accountId: Int): Flow<List<Transaction>>
    fun getAccountsWithTransactions(): Flow<List<AccountWithTransactions>>
    fun getCategoryTotalTransaction(type: String): Flow<List<CategoryCountTotal>>
    fun checkOnboardingState(): Flow<OnboardingState>
    suspend fun setOnboardingState(state: OnboardingState)
}