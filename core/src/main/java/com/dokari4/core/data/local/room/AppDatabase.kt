package com.dokari4.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dokari4.core.data.local.entity.AccountEntity
import com.dokari4.core.data.local.entity.CategoryEntity
import com.dokari4.core.data.local.entity.TransactionEntity
import com.dokari4.core.data.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, AccountEntity::class, CategoryEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

}