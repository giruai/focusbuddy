package com.giruai.focusbuddy.ui.timer

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giruai.focusbuddy.data.model.TimerSettings
import com.giruai.focusbuddy.data.repository.SettingsRepository
import com.giruai.focusbuddy.domain.model.TimerState
import com.giruai.focusbuddy.util.HapticsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val hapticsHelper: HapticsHelper,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var mediaPlayer: MediaPlayer? = null

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
        hapticsHelper.tick()

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
            onTimerComplete()
        }
    }

    fun pauseTimer() {
        hapticsHelper.tick()
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
        hapticsHelper.tick()
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
        hapticsHelper.tick()
        timerJob?.cancel()
        val settings = _uiState.value.settings
        _uiState.update {
            it.copy(
                timerState = TimerState.Idle(settings.focusDuration),
                isRunning = false
            )
        }
    }

    private fun onTimerComplete() {
        hapticsHelper.complete()

        // Play sound if enabled
        val settings = _uiState.value.settings
        if (settings.soundEnabled) {
            playCompletionSound()
        }
    }

    private fun playCompletionSound() {
        // TODO: Add actual sound file to res/raw and play it
        // For now, using system notification sound
        try {
            // This would be: MediaPlayer.create(context, R.raw.timer_complete)
            // For MVP without sound file, we skip actual playback
        } catch (e: Exception) {
            // Sound not available
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    data class TimerUiState(
        val timerState: TimerState = TimerState.Idle(25),
        val settings: TimerSettings = TimerSettings(),
        val isRunning: Boolean = false
    )
}
