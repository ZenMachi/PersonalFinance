package com.dokari4.personalfinance.data.local

import com.dokari4.personalfinance.data.local.datastore.DataStoreManager
import com.dokari4.personalfinance.data.local.entity.AccountEntity
import com.dokari4.personalfinance.data.local.entity.CategoryEntity
import com.dokari4.personalfinance.data.local.entity.TransactionEntity
import com.dokari4.personalfinance.data.local.entity.UserEntity
import com.dokari4.personalfinance.data.local.model.AccountWithTransactions
import com.dokari4.personalfinance.data.local.model.CategoryCountTotal
import com.dokari4.personalfinance.data.local.room.AppDao
import com.dokari4.personalfinance.util.enums.OnboardingState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val appDao: AppDao,
    private val manager: DataStoreManager
) {

    fun getUserName(): Flow<String> = appDao.getUserName()

    fun getAccountList(): Flow<List<AccountEntity>> = appDao.getAccountList()

    fun getTransactionList(): Flow<List<TransactionEntity>> = appDao.getTransactionList()

    fun getCategoryList(): Flow<List<CategoryEntity>> = appDao.getCategoryList()

    fun getAccountExpenseList(accountId: Int): Flow<List<TransactionEntity>> = appDao.getTransactionListByAccountIdAndType(accountId, "Expense")

    fun getAccountIncomeList(accountId: Int): Flow<List<TransactionEntity>> = appDao.getTransactionListByAccountIdAndType(accountId, "Income")

   fun getAccountsWithTransactions(): Flow<List<AccountWithTransactions>> = appDao.getAccountsWithTransactions()

    fun getCategoryTotalTransaction(type: String): Flow<List<CategoryCountTotal>> = appDao.getCategoryTotalTransaction(type)

    suspend fun insertAccount(account: AccountEntity) = appDao.insertAccount(account)

    suspend fun insertUser(user: UserEntity) = appDao.insertUser(user)

    suspend fun insertCategory(category: CategoryEntity) = appDao.insertCategory(category)

    suspend fun insertTransaction(transaction: TransactionEntity) = appDao.insertTransaction(transaction)

    fun checkOnboardingState() = manager.checkOnboardingState

    suspend fun setOnboardingState(state: OnboardingState) = manager.setOnboardingState(state)

    suspend fun updateAccount(account: AccountEntity) = appDao.updateAccount(account)

    suspend fun deleteAccount(account: AccountEntity) = appDao.deleteAccount(account)

    suspend fun updateTransaction(transaction: TransactionEntity) = appDao.updateTransaction(transaction)

    suspend fun deleteTransaction(transaction: TransactionEntity) = appDao.deleteTransaction(transaction)


}