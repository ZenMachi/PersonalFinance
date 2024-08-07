package com.dokari4.core.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.dokari4.core.data.local.entity.AccountEntity
import com.dokari4.core.data.local.entity.CategoryEntity
import com.dokari4.core.data.local.entity.TransactionEntity
import com.dokari4.core.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    @Query("SELECT name FROM user_table WHERE id = 1")
    fun getUserName(): Flow<String>

    @Query("SELECT * FROM account_table")
    fun getAccountList(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM transaction_table")
    fun getTransactionList(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM category_table")
    fun getCategoryList(): Flow<List<CategoryEntity>>

    @Query(
        "select" +
                " a.id, a.name, a.account_type, a.amount," +
                " sum(case when t.type = 'Income' then t.amount else 0 end) as total_income," +
                " sum(case when t.type = 'Expense' then t.amount else 0 end) as total_expense" +
                " from account_table as a" +
                " left join transaction_table as t on a.id = t.account_id" +
                " group by a.id"
    )
    fun getAccountsWithTransactions(): Flow<List<com.dokari4.core.data.local.model.AccountWithTransactions>>

    @Query(
        "select" +
                " c.id, c.name," +
                " count(case when t.type = :type then t.category_id else null end) as total_transaction," +
                " sum(case when t.type = :type then t.amount else 0 end) as amount" +
                " from category_table as c" +
                " left join transaction_table as t" +
                " on c.id = t.category_id" +
                " group by c.id"
    )
    fun getCategoryTotalTransaction(type: String): Flow<List<com.dokari4.core.data.local.model.CategoryCountTotal>>

    @Query("SELECT * FROM transaction_table WHERE account_id = :accountId AND type LIKE :type")
    fun getTransactionListByAccountIdAndType(
        accountId: Int,
        type: String
    ): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transaction_table WHERE type LIKE :type")
    fun getTransactionListByTransactionType(type: String): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Transaction
    suspend fun transferTransaction(from: TransactionEntity, to: TransactionEntity) {
        insertTransaction(from)
        insertTransaction(to)
    }

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteAccount(account: AccountEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)
}