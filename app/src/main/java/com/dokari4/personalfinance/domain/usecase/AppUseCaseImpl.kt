package com.dokari4.personalfinance.domain.usecase

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

    override fun getAccountList(): Flowable<List<Account>> = appRepository.getAccountList()

    override fun insertAccount(account: Account) = appRepository.insertAccount(account)
    override fun insertUser(user: User) = appRepository.insertUser(user)
    override fun insertTransaction(transaction: Transaction) = appRepository.insertTransaction(transaction)
    override fun insertCategory(category: Category) = appRepository.insertCategory(category)
    override fun getUserName(): Flowable<String> = appRepository.getUserName()
    override fun getTransactionList(): Flowable<List<Transaction>> = appRepository.getTransactionList()
    override fun getCategoryList(): Flowable<List<Category>> = appRepository.getCategoryList()
    override fun getAccountExpenseList(accountId: Int): Flowable<List<Transaction>> = appRepository.getAccountExpenseList(accountId)
    override fun getAccountIncomeList(accountId: Int): Flowable<List<Transaction>>  = appRepository.getAccountIncomeList(accountId)
    override fun getAccountsWithTransactions(): Flowable<List<AccountWithTransactions>> = appRepository.getAccountsWithTransactions()
    override fun getCategoryTotalTransaction(type: String): Flowable<List<CategoryCountTotal>> = appRepository.getCategoryTotalTransaction(type)
    override fun checkOnboardingState(): Flow<OnboardingState> = appRepository.checkOnboardingState()

    override suspend fun setOnboardingState(state: OnboardingState) = appRepository.setOnboardingState(state)

}