package com.dokari4.core.di

import com.dokari4.core.domain.usecase.AppUseCase
import com.dokari4.core.domain.usecase.AppUseCaseImpl
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
    abstract fun bindAppUseCase(appUseCaseImpl: AppUseCaseImpl): AppUseCase
}