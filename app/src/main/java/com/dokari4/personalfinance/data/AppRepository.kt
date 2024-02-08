package com.dokari4.personalfinance.data

import com.dokari4.personalfinance.data.local.LocalDataSource
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Transactions
import com.dokari4.personalfinance.domain.repository.IAppRepository
import com.dokari4.personalfinance.util.AppExecutors
import com.dokari4.personalfinance.util.DataMapper
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IAppRepository {

    override fun getAccountList(): Flowable<List<Account>> {
        return localDataSource.getAccountList().map {
            DataMapper.mapAccountEntitiesToDomain(it)
        }
    }

    override fun insertAccount(account: Account) {
        val accountEntity = DataMapper.mapDomainToEntity(account)
        localDataSource.insertAccount(accountEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}