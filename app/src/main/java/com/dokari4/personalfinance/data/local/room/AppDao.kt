package com.dokari4.personalfinance.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dokari4.personalfinance.data.local.entity.AccountEntity
import com.dokari4.personalfinance.data.local.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface AppDao {

    @Query("select * from account")
    fun getAccountList(): Flowable<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(account: AccountEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity): Completable
}