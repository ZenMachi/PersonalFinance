package com.dokari4.personalfinance.domain.usecase

import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Transactions
import io.reactivex.Flowable

interface AppUseCase {

//    fun getHistoryTransaction(): Flowable<List<Transactions>>

    fun getAccountList(): Flowable<List<Account>>

    fun insertAccount(account: Account)
}