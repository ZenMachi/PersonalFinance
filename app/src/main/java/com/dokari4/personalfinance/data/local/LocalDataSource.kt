package com.dokari4.personalfinance.data.local

import com.dokari4.personalfinance.data.local.entity.AccountEntity
import com.dokari4.personalfinance.data.local.entity.CategoryEntity
import com.dokari4.personalfinance.data.local.entity.TransactionEntity
import com.dokari4.personalfinance.data.local.entity.UserEntity
import com.dokari4.personalfinance.data.local.room.AppDao
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val appDao: AppDao) {

    fun getAccountList(): Flowable<List<AccountEntity>> = appDao.getAccountList()
    fun getTransactionList(): Flowable<List<TransactionEntity>> = appDao.getTransactionList()

    fun insertAccount(account: AccountEntity) = appDao.insertAccount(account)

    fun insertUser(user: UserEntity) = appDao.insertUser(user)

    fun insertCategory(category: CategoryEntity) = appDao.insertCategory(category)

    fun insertTransaction(transaction: TransactionEntity) = appDao.insertTransaction(transaction)
}