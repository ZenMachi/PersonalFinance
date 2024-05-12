package com.dokari4.personalfinance.domain.usecase

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

//    fun getHistoryTransaction(): Flowable<List<Transactions>>

    fun getAccountList(): Flowable<List<Account>>
    fun insertAccount(account: Account)
    fun insertUser(user: User)
    fun insertTransaction(transaction: Transaction)
    fun insertCategory(category: Category)
    fun getTransactionList(): Flowable<List<Transaction>>
    fun getCategoryList(): Flowable<List<Category>>
    fun getAccountExpenseList(accountId: Int): Flowable<List<Transaction>>
    fun getAccountIncomeList(accountId: Int): Flowable<List<Transaction>>
    fun getAccountsWithTransactions(): Flowable<List<AccountWithTransactions>>
    fun getCategoryTotalTransaction(): Flowable<List<CategoryCountTotal>>
    fun checkOnboardingState(): Flow<OnboardingState>
    suspend fun setOnboardingState(state: OnboardingState)
}