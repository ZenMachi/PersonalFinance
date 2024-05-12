package com.dokari4.personalfinance.data

import android.util.Log
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
import io.reactivex.CompletableObserver
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
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
                    Log.d("Error", e.message!!)
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

    override fun insertTransaction(transaction: Transaction) {
        val entity = DataMapper.mapDomainToTransactionEntity(transaction)
        localDataSource.insertTransaction(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun insertCategory(category: Category) {
        val entity = DataMapper.mapDomainToCategoryEntity(category)
        localDataSource.insertCategory(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    override fun getTransactionList(): Flowable<List<Transaction>> {
        return localDataSource.getTransactionList().map {
            DataMapper.mapTransactionEntityToDomain(it)
        }
    }

    override fun getCategoryList(): Flowable<List<Category>> {
        return localDataSource.getCategoryList().map {
            DataMapper.mapCategoryEntityToDomain(it)
        }
    }

    override fun getAccountExpenseList(accountId: Int): Flowable<List<Transaction>> {
        return localDataSource.getAccountExpenseList(accountId).map {
            DataMapper.mapTransactionEntityToDomain(it)
        }
    }
    override fun getAccountIncomeList(accountId: Int): Flowable<List<Transaction>> {
        return localDataSource.getAccountIncomeList(accountId).map {
            DataMapper.mapTransactionEntityToDomain(it)
        }
    }

    override fun getAccountsWithTransactions(): Flowable<List<AccountWithTransactions>> {
        return localDataSource.getAccountsWithTransactions().map {
            DataMapper.mapAccountWithTransactionsEntityToDomain(it)
        }
    }

    override fun getCategoryTotalTransaction(): Flowable<List<CategoryCountTotal>> {
        return localDataSource.getCategoryTotalTransaction().map {
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