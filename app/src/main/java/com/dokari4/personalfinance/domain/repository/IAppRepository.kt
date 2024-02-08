package com.dokari4.personalfinance.domain.repository

import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Transactions
import io.reactivex.Flowable

interface IAppRepository {

//    fun getHistoryTransaction(): Flowable<List<Transactions>>

    fun getAccountList(): Flowable<List<Account>>

    fun insertAccount(account: Account)
}