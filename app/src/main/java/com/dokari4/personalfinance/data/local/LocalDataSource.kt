package com.dokari4.personalfinance.data.local

import com.dokari4.personalfinance.data.local.entity.AccountEntity
import com.dokari4.personalfinance.data.local.room.AccountDao
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val accountDao: AccountDao) {

    fun getAccountList(): Flowable<List<AccountEntity>> = accountDao.getAccountList()

    fun insertAccount(account: AccountEntity) = accountDao.insertAccount(account)
}