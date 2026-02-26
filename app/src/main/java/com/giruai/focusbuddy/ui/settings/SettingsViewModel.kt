package com.giruai.focusbuddy.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giruai.focusbuddy.data.model.TimerSettings
import com.giruai.focusbuddy.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        settingsRepository.getSettings()
            .onEach { settings ->
                _uiState.update { it.copy(settings = settings) }
            }
            .launchIn(viewModelScope)
    }

    fun incrementFocusDuration() {
        val current = _uiState.value.settings.focusDuration
        if (current < 60) {
            viewModelScope.launch {
                settingsRepository.setFocusDuration(current + 5)
            }
        }
    }

    fun decrementFocusDuration() {
        val current = _uiState.value.settings.focusDuration
        if (current > 5) {
            viewModelScope.launch {
                settingsRepository.setFocusDuration(current - 5)
            }
        }
    }

    fun incrementBreakDuration() {
        val current = _uiState.value.settings.breakDuration
        if (current < 30) {
            viewModelScope.launch {
                settingsRepository.setBreakDuration(current + 5)
            }
        }
    }

    fun decrementBreakDuration() {
        val current = _uiState.value.settings.breakDuration
        if (current > 1) {
            viewModelScope.launch {
                settingsRepository.setBreakDuration(current - 1)
            }
        }
    }

    fun setSessionLabel(label: String) {
        viewModelScope.launch {
            settingsRepository.setSessionLabel(label)
        }
    }

    data class SettingsUiState(
        val settings: TimerSettings = TimerSettings()
    )
}
