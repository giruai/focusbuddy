package com.giruai.focusbuddy.data.repository

import com.giruai.focusbuddy.data.local.SettingsDataStore
import com.giruai.focusbuddy.data.model.TimerSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: SettingsDataStore
) {
    fun getSettings(): Flow<TimerSettings> = dataStore.settingsFlow

    suspend fun setFocusDuration(minutes: Int) = dataStore.updateFocusDuration(minutes)
    suspend fun setBreakDuration(minutes: Int) = dataStore.updateBreakDuration(minutes)
    suspend fun setHapticsEnabled(enabled: Boolean) = dataStore.updateHapticsEnabled(enabled)
    suspend fun setSoundEnabled(enabled: Boolean) = dataStore.updateSoundEnabled(enabled)
    suspend fun setSessionLabel(label: String) = dataStore.updateSessionLabel(label)
}
