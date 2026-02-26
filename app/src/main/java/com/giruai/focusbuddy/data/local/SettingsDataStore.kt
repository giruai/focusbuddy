package com.giruai.focusbuddy.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.giruai.focusbuddy.data.model.TimerSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "focusbuddy_settings")

@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val FOCUS_DURATION = intPreferencesKey("focus_duration")
        val BREAK_DURATION = intPreferencesKey("break_duration")
        val HAPTICS_ENABLED = booleanPreferencesKey("haptics_enabled")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val SESSION_LABEL = stringPreferencesKey("session_label")

        const val DEFAULT_FOCUS_DURATION = 25
        const val DEFAULT_BREAK_DURATION = 5
        const val DEFAULT_HAPTICS_ENABLED = true
        const val DEFAULT_SOUND_ENABLED = false
        const val DEFAULT_SESSION_LABEL = "Deep Work Session"
    }

    val settingsFlow: Flow<TimerSettings> = context.dataStore.data.map { prefs ->
        TimerSettings(
            focusDuration = prefs[FOCUS_DURATION] ?: DEFAULT_FOCUS_DURATION,
            breakDuration = prefs[BREAK_DURATION] ?: DEFAULT_BREAK_DURATION,
            hapticsEnabled = prefs[HAPTICS_ENABLED] ?: DEFAULT_HAPTICS_ENABLED,
            soundEnabled = prefs[SOUND_ENABLED] ?: DEFAULT_SOUND_ENABLED,
            sessionLabel = prefs[SESSION_LABEL] ?: DEFAULT_SESSION_LABEL
        )
    }

    suspend fun updateFocusDuration(minutes: Int) {
        context.dataStore.edit { it[FOCUS_DURATION] = minutes }
    }

    suspend fun updateBreakDuration(minutes: Int) {
        context.dataStore.edit { it[BREAK_DURATION] = minutes }
    }

    suspend fun updateHapticsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[HAPTICS_ENABLED] = enabled }
    }

    suspend fun updateSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { it[SOUND_ENABLED] = enabled }
    }

    suspend fun updateSessionLabel(label: String) {
        context.dataStore.edit { it[SESSION_LABEL] = label }
    }
}
