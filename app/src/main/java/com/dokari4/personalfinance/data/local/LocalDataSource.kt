package com.dokari4.personalfinance.data.local

import com.dokari4.personalfinance.data.local.datastore.DataStoreManager
import com.dokari4.personalfinance.data.local.entity.AccountEntity
import com.dokari4.personalfinance.data.local.entity.CategoryEntity
import com.dokari4.personalfinance.data.local.entity.TransactionEntity
import com.dokari4.personalfinance.data.local.entity.UserEntity
import com.dokari4.personalfinance.data.local.model.AccountWithTransactions
import com.dokari4.personalfinance.data.local.model.CategoryCountTotal
import com.dokari4.personalfinance.data.local.room.AppDao
import com.dokari4.personalfinance.util.OnboardingState
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val appDao: AppDao,
    private val manager: DataStoreManager
) {

    fun getUserName(): Flowable<String> = appDao.getUserName()

    fun getAccountList(): Flowable<List<AccountEntity>> = appDao.getAccountList()

    fun getTransactionList(): Flowable<List<TransactionEntity>> = appDao.getTransactionList()

    fun getCategoryList(): Flowable<List<CategoryEntity>> = appDao.getCategoryList()

    fun getAccountExpenseList(accountId: Int): Flowable<List<TransactionEntity>> = appDao.getTransactionListByAccountIdAndType(accountId, "Expense")

    fun getAccountIncomeList(accountId: Int): Flowable<List<TransactionEntity>> = appDao.getTransactionListByAccountIdAndType(accountId, "Income")

   fun getAccountsWithTransactions(): Flowable<List<AccountWithTransactions>> = appDao.getAccountsWithTransactions()

    fun getCategoryTotalTransaction(type: String): Flowable<List<CategoryCountTotal>> = appDao.getCategoryTotalTransaction(type)

    fun insertAccount(account: AccountEntity) = appDao.insertAccount(account)

    fun insertUser(user: UserEntity) = appDao.insertUser(user)

    fun insertCategory(category: CategoryEntity) = appDao.insertCategory(category)

    fun insertTransaction(transaction: TransactionEntity) = appDao.insertTransaction(transaction)

    fun checkOnboardingState() = manager.checkOnboardingState

    suspend fun setOnboardingState(state: OnboardingState) = manager.setOnboardingState(state)
}