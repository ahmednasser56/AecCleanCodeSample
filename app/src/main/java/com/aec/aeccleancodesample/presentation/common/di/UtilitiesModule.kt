package com.aec.aeccleancodesample.presentation.common.di

import android.content.Context
import com.aec.aeccleancodesample.data.local.prefs.PreferencesUtility
import com.aec.aeccleancodesample.presentation.common.utils.NetworkUtility
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UtilitiesModule {

    @Singleton
    @Provides
    fun provideNetworkUtility(
        @ApplicationContext applicationContext: Context,
        preferencesUtility: PreferencesUtility
    ) =
        NetworkUtility(applicationContext, preferencesUtility)
}