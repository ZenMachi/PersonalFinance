package com.dokari4.personalfinance.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dokari4.personalfinance.data.local.entity.AccountEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface AccountDao {

    @Query("select * from account")
    fun getAccountList(): Flowable<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(account: AccountEntity): Completable
}