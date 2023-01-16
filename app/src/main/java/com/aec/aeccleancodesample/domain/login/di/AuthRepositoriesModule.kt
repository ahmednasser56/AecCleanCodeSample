package com.aec.aeccleancodesample.domain.login.di

import com.aec.aeccleancodesample.data.remote.AuthApi
import com.aec.aeccleancodesample.data.repository.AuthRepositoryImpl
import com.aec.aeccleancodesample.domain.login.repository.AuthRepository
import com.aec.aeccleancodesample.presentation.common.utils.NetworkUtility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoriesModule {

    @Provides
    @Singleton
    fun provideAuthRepository(networkUtility: NetworkUtility, authApi: AuthApi): AuthRepository =
        AuthRepositoryImpl(networkUtility, authApi)
}