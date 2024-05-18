package com.dokari4.personalfinance.data

import com.dokari4.personalfinance.data.local.LocalDataSource
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.AccountWithTransactions
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.CategoryCountTotal
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.domain.repository.IAppRepository
import com.dokari4.personalfinance.util.AppExecutors
import com.dokari4.personalfinance.util.DataMapper
import com.dokari4.personalfinance.util.OnboardingState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAppRepository {

    override fun getAccountList(): Flow<List<Account>> {
        return localDataSource.getAccountList().map {
            DataMapper.mapAccountEntitiesToDomain(it)
        }
    }

    override suspend fun insertAccount(account: Account) {
        val accountEntity = DataMapper.mapDomainToAccountEntity(account)
        localDataSource.insertAccount(accountEntity)
    }

    override suspend fun insertUser(user: User) {
        val userEntity = DataMapper.mapDomainToUserEntity(user)
        localDataSource.insertUser(userEntity)
    }

    override suspend fun insertTransaction(transaction: Transaction) {
        val entity = DataMapper.mapDomainToTransactionEntity(transaction)
        localDataSource.insertTransaction(entity)
    }

    override suspend fun insertCategory(category: Category) {
        val entity = DataMapper.mapDomainToCategoryEntity(category)
        localDataSource.insertCategory(entity)
    }

    override fun getUserName(): Flow<String> {
        return localDataSource.getUserName()
    }

    override fun getTransactionList(): Flow<List<Transaction>> {
        return localDataSource.getTransactionList().map {
            DataMapper.mapTransactionEntityToDomain(it)
        }
    }

    override fun getCategoryList(): Flow<List<Category>> {
        return localDataSource.getCategoryList().map {
            DataMapper.mapCategoryEntityToDomain(it)
        }
    }

    override fun getAccountExpenseList(accountId: Int): Flow<List<Transaction>> {
        return localDataSource.getAccountExpenseList(accountId).map {
            DataMapper.mapTransactionEntityToDomain(it)
        }
    }

    override fun getAccountIncomeList(accountId: Int): Flow<List<Transaction>> {
        return localDataSource.getAccountIncomeList(accountId).map {
            DataMapper.mapTransactionEntityToDomain(it)
        }
    }

    override fun getAccountsWithTransactions(): Flow<List<AccountWithTransactions>> {
        return localDataSource.getAccountsWithTransactions().map {
            DataMapper.mapAccountWithTransactionsEntityToDomain(it)
        }
    }

    override fun getCategoryTotalTransaction(type: String): Flow<List<CategoryCountTotal>> {
        return localDataSource.getCategoryTotalTransaction(type).map {
            DataMapper.mapCategoryWithTransactionsEntityToDomain(it)
        }
    }

    override fun checkOnboardingState(): Flow<OnboardingState> {
        return localDataSource.checkOnboardingState()
    }

    override suspend fun setOnboardingState(state: OnboardingState) {
        localDataSource.setOnboardingState(state)
    }
}