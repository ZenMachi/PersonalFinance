package com.dokari4.personalfinance.di

import com.dokari4.personalfinance.data.AppRepository
import com.dokari4.personalfinance.domain.repository.IAppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(appRepository: AppRepository): IAppRepository
}