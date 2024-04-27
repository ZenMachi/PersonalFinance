package com.dokari4.personalfinance.data.local

import com.dokari4.personalfinance.data.local.entity.AccountEntity
import com.dokari4.personalfinance.data.local.entity.UserEntity
import com.dokari4.personalfinance.data.local.room.AppDao
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val appDao: AppDao) {

    fun getAccountList(): Flowable<List<AccountEntity>> = appDao.getAccountList()

    fun insertAccount(account: AccountEntity) = appDao.insertAccount(account)

    fun insertUser(user: UserEntity) = appDao.insertUser(user)
}