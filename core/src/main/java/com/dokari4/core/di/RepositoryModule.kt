package com.dokari4.core.di

import com.dokari4.core.data.AppRepositoryImpl
import com.dokari4.core.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class, DataStoreModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository
}