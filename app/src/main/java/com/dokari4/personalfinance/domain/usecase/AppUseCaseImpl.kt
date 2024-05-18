package com.dokari4.personalfinance.domain.usecase

import com.dokari4.personalfinance.data.State
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.AccountWithTransactions
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.domain.repository.IAppRepository
import com.dokari4.personalfinance.util.OnboardingState
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppUseCaseImpl @Inject constructor(private val appRepository: IAppRepository) : AppUseCase {

    override fun getAccountList(): Flow<List<Account>> = appRepository.getAccountList()

    override suspend fun insertAccount(account: Account) = appRepository.insertAccount(account)
    override suspend fun insertUser(user: User) = appRepository.insertUser(user)
    override suspend fun insertTransaction(transaction: Transaction) = appRepository.insertTransaction(transaction)
    override suspend fun insertCategory(category: Category) = appRepository.insertCategory(category)
    override fun getUserName(): Flow<String> = appRepository.getUserName()
    override fun getTransactionList(): Flow<List<Transaction>> = appRepository.getTransactionList()
    override fun getCategoryList(): Flow<List<Category>> = appRepository.getCategoryList()
    override fun getAccountExpenseList(accountId: Int): Flow<List<Transaction>> = appRepository.getAccountExpenseList(accountId)
    override fun getAccountIncomeList(accountId: Int): Flow<List<Transaction>>  = appRepository.getAccountIncomeList(accountId)
    override fun getAccountsWithTransactions(): Flow<List<AccountWithTransactions>> = appRepository.getAccountsWithTransactions()
    override fun getCategoryTotalTransaction(type: String): Flow<List<CategoryCountTotal>> = appRepository.getCategoryTotalTransaction(type)
    override fun checkOnboardingState(): Flow<OnboardingState> = appRepository.checkOnboardingState()

    override suspend fun setOnboardingState(state: OnboardingState) = appRepository.setOnboardingState(state)

}