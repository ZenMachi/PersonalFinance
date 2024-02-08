package com.dokari4.personalfinance.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dokari4.personalfinance.data.local.entity.AccountEntity

@Database(entities = [AccountEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

}