package com.aec.aeccleancodesample.domain.login.di

import com.aec.aeccleancodesample.domain.login.repository.AuthRepository
import com.aec.aeccleancodesample.domain.login.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCasesModule {

    @Singleton
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository) =
        LoginUseCase(authRepository)

}