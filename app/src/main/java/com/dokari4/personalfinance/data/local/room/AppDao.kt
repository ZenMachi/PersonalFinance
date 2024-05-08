package com.dokari4.personalfinance.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dokari4.personalfinance.data.local.entity.AccountEntity
import com.dokari4.personalfinance.data.local.entity.CategoryEntity
import com.dokari4.personalfinance.data.local.entity.TransactionEntity
import com.dokari4.personalfinance.data.local.entity.UserEntity
import com.dokari4.personalfinance.data.local.model.AccountWithTransactions
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface AppDao {

    @Query("SELECT * FROM account_table")
    fun getAccountList(): Flowable<List<AccountEntity>>

    @Query("SELECT * FROM transaction_table")
    fun getTransactionList(): Flowable<List<TransactionEntity>>

    @Query("SELECT * FROM category_table")
    fun getCategoryList(): Flowable<List<CategoryEntity>>

    @Query(
        "select" +
                " a.id, a.name, a.account_type, a.amount," +
                " sum(case when t.type = 'Income' then t.amount else 0 end) as total_income," +
                " sum(case when t.type = 'Expense' then t.amount else 0 end) as total_expense" +
                " from account_table as a" +
                " left join transaction_table as t on a.id = t.account_id" +
                " group by a.id"
    )
    fun getAccountsWithTransactions(): Flowable<List<AccountWithTransactions>>

    @Query("SELECT * FROM transaction_table WHERE account_id = :accountId AND type LIKE :type")
    fun getTransactionListByAccountIdAndType(accountId: Int, type: String): Flowable<List<TransactionEntity>>

    @Query("SELECT * FROM transaction_table WHERE type LIKE :type")
    fun getTransactionListByTransactionType(type: String): Flowable<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(account: AccountEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: CategoryEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: TransactionEntity): Completable
}