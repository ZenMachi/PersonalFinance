package com.dokari4.core.di

import android.content.Context
import androidx.room.Room
import com.dokari4.core.data.local.room.AppDao
import com.dokari4.core.data.local.room.AppDatabase
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
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAppDao(database: AppDatabase): AppDao = database.appDao()
}