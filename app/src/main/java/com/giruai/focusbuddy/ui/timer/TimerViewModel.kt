package com.giruai.focusbuddy.ui.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giruai.focusbuddy.data.model.TimerSettings
import com.giruai.focusbuddy.data.repository.SettingsRepository
import com.giruai.focusbuddy.domain.model.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        // Collect settings changes
        settingsRepository.getSettings()
            .onEach { settings ->
                val currentTimerState = _uiState.value.timerState
                // Only update idle/completed timers, don't affect running/paused
                val newTimerState = when (currentTimerState) {
                    is TimerState.Idle -> TimerState.Idle(settings.focusDuration)
                    is TimerState.Completed -> TimerState.Idle(settings.focusDuration)
                    else -> currentTimerState
                }
                _uiState.update {
                    it.copy(
                        settings = settings,
                        timerState = newTimerState
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun startTimer() {
        val currentState = _uiState.value.timerState
        val settings = _uiState.value.settings
        val totalSeconds = when (currentState) {
            is TimerState.Idle -> settings.focusDuration * 60
            is TimerState.Paused -> currentState.remainingSeconds
            is TimerState.Running -> return // Already running
            is TimerState.Completed -> settings.focusDuration * 60
        }

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var remaining = totalSeconds
            while (remaining > 0) {
                _uiState.update {
                    it.copy(
                        timerState = TimerState.Running(
                            durationMinutes = settings.focusDuration,
                            remainingSeconds = remaining,
                            totalSeconds = settings.focusDuration * 60
                        ),
                        isRunning = true
                    )
                }
                delay(1000)
                remaining--
            }
            // Timer completed
            _uiState.update {
                it.copy(
                    timerState = TimerState.Completed(settings.focusDuration),
                    isRunning = false
                )
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        val current = _uiState.value.timerState as? TimerState.Running ?: return
        val settings = _uiState.value.settings
        _uiState.update {
            it.copy(
                timerState = TimerState.Paused(
                    durationMinutes = settings.focusDuration,
                    remainingSeconds = current.remainingSeconds,
                    totalSeconds = settings.focusDuration * 60
                ),
                isRunning = false
            )
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        val settings = _uiState.value.settings
        _uiState.update {
            it.copy(
                timerState = TimerState.Idle(settings.focusDuration),
                isRunning = false
            )
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        val settings = _uiState.value.settings
        _uiState.update {
            it.copy(
                timerState = TimerState.Idle(settings.focusDuration),
                isRunning = false
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    data class TimerUiState(
        val timerState: TimerState = TimerState.Idle(25),
        val settings: TimerSettings = TimerSettings(),
        val isRunning: Boolean = false
    )
}
