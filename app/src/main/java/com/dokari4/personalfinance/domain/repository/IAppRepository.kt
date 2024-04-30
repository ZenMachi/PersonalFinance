package com.dokari4.personalfinance.domain.repository

import com.dokari4.personalfinance.domain.model.Account
import com.dokari4.personalfinance.domain.model.Category
import com.dokari4.personalfinance.domain.model.Transaction
import com.dokari4.personalfinance.domain.model.User
import io.reactivex.Flowable

interface IAppRepository {

//    fun getHistoryTransaction(): Flowable<List<Transactions>>

    fun getAccountList(): Flowable<List<Account>>
    fun insertAccount(account: Account)
    fun insertUser(user: User)
    fun insertTransaction(transaction: Transaction)
    fun insertCategory(category: Category)
    fun getTransactionList(): Flowable<List<Transaction>>
}