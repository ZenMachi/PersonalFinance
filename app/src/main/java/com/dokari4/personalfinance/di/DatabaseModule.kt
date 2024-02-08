package com.dokari4.personalfinance.di

import android.content.Context
import androidx.room.Room
import com.dokari4.personalfinance.data.local.room.AccountDao
import com.dokari4.personalfinance.data.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "finance.db"
        )
//            .createFromAsset("database/finance.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAccountDao(database: AppDatabase): AccountDao = database.accountDao()
}