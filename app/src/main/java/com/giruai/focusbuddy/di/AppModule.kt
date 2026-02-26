package com.giruai.focusbuddy.di

import android.content.Context
import com.giruai.focusbuddy.data.local.SettingsDataStore
import com.giruai.focusbuddy.data.repository.SettingsRepository
import com.giruai.focusbuddy.util.HapticsHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): SettingsDataStore = SettingsDataStore(context)

    // SettingsRepository is created by Hilt via @Inject constructor

    @Provides
    @Singleton
    fun provideHapticsHelper(
        @ApplicationContext context: Context,
        settingsRepository: SettingsRepository
    ): HapticsHelper = HapticsHelper(context, settingsRepository)
}
