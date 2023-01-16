package com.aec.aeccleancodesample.data.remote.di

import com.aec.aeccleancodesample.BuildConfig.BASE_URL
import com.aec.aeccleancodesample.presentation.common.utils.NetworkUtility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(networkUtility: NetworkUtility): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(networkUtility.getUnsafeOkHttpClient())
            .build()
}