package com.dokari4.personalfinance.di

import com.dokari4.personalfinance.domain.usecase.AppUseCase
import com.dokari4.personalfinance.domain.usecase.AppUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideAppUseCase(appUseCaseImpl: AppUseCaseImpl): AppUseCase
}