package com.dokari4.personalfinance.data

import android.util.Log
import com.dokari4.personalfinance.data.local.LocalDataSource
import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.User
import com.dokari4.personalfinance.domain.repository.IAppRepository
import com.dokari4.personalfinance.util.AppExecutors
import com.dokari4.personalfinance.util.DataMapper
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
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
        val accountEntity = DataMapper.mapDomainToAccountEntity(account)
        localDataSource.insertAccount(accountEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    Log.d("Completable", "Progress")
                }

                override fun onComplete() {
                    Log.d("Completable", "Success")
                }

                override fun onError(e: Throwable) {
                    Log.d("Completable", "Error")
                }

            })
    }

    override fun insertUser(user: User) {
        val userEntity = DataMapper.mapDomainToUserEntity(user)
        localDataSource.insertUser(userEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}