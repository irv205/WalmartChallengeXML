package com.irv205.walmartchallengexml.core.di

import com.irv205.walmartchallengexml.data.repository.RepositoryImpl
import com.irv205.walmartchallengexml.domain.repository.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): IRepository
}