package com.giruai.focusbuddy.ui.timer

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
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
        settingsRepository.getSettings()
            .onEach { settings ->
                val currentTimerState = _uiState.value.timerState
                val newTimerState = when (currentTimerState) {
                    is TimerState.Idle -> TimerState.Idle(settings.focusDuration)
                    is TimerState.Completed -> TimerState.Idle(settings.focusDuration)
                    is TimerState.BreakCompleted -> TimerState.Idle(settings.focusDuration)
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
        
        when (currentState) {
            is TimerState.Idle -> startFocusTimer(settings.focusDuration * 60, settings)
            is TimerState.Paused -> startFocusTimer(currentState.remainingSeconds, settings)
            is TimerState.Running -> return
            is TimerState.Completed -> startFocusTimer(settings.focusDuration * 60, settings)
            is TimerState.BreakCompleted -> startFocusTimer(settings.focusDuration * 60, settings)
            else -> {} // Break states handled separately
        }
    }

    fun startBreak() {
        hapticsHelper.tick()
        val settings = _uiState.value.settings
        startBreakTimer(settings.breakDuration * 60, settings)
    }

    fun skipBreak() {
        hapticsHelper.tick()
        val settings = _uiState.value.settings
        _uiState.update {
            it.copy(
                timerState = TimerState.Idle(settings.focusDuration),
                isRunning = false,
                isBreak = false
            )
        }
    }

    private fun startFocusTimer(totalSeconds: Int, settings: TimerSettings) {
        timerJob?.cancel()
        _uiState.update { it.copy(isBreak = false) }
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
            onFocusComplete()
        }
    }

    private fun startBreakTimer(totalSeconds: Int, settings: TimerSettings) {
        timerJob?.cancel()
        _uiState.update { it.copy(isBreak = true) }
        timerJob = viewModelScope.launch {
            var remaining = totalSeconds
            while (remaining > 0) {
                _uiState.update {
                    it.copy(
                        timerState = TimerState.BreakRunning(
                            durationMinutes = settings.breakDuration,
                            remainingSeconds = remaining,
                            totalSeconds = settings.breakDuration * 60
                        ),
                        isRunning = true
                    )
                }
                delay(1000)
                remaining--
            }
            onBreakComplete()
        }
    }

    fun pauseTimer() {
        hapticsHelper.tick()
        timerJob?.cancel()
        val current = _uiState.value.timerState
        val settings = _uiState.value.settings
        
        when (current) {
            is TimerState.Running -> {
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
            is TimerState.BreakRunning -> {
                _uiState.update {
                    it.copy(
                        timerState = TimerState.BreakPaused(
                            durationMinutes = settings.breakDuration,
                            remainingSeconds = current.remainingSeconds,
                            totalSeconds = settings.breakDuration * 60
                        ),
                        isRunning = false
                    )
                }
            }
            else -> {}
        }
    }

    fun stopTimer() {
        hapticsHelper.tick()
        timerJob?.cancel()
        val settings = _uiState.value.settings
        _uiState.update {
            it.copy(
                timerState = TimerState.Idle(settings.focusDuration),
                isRunning = false,
                isBreak = false
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
                isRunning = false,
                isBreak = false
            )
        }
    }

    private fun onFocusComplete() {
        hapticsHelper.complete()
        val settings = _uiState.value.settings
        
        _uiState.update {
            it.copy(
                timerState = TimerState.Completed(settings.focusDuration),
                isRunning = false
            )
        }
        
        if (settings.soundEnabled) {
            playCompletionSound()
        }
    }

    private fun onBreakComplete() {
        hapticsHelper.complete()
        val settings = _uiState.value.settings
        
        _uiState.update {
            it.copy(
                timerState = TimerState.BreakCompleted(settings.breakDuration),
                isRunning = false,
                isBreak = false
            )
        }
        
        if (settings.soundEnabled) {
            playCompletionSound()
        }
    }

    private fun playCompletionSound() {
        try {
            // Use system notification sound
            val notificationUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, notificationUri)
            mediaPlayer?.apply {
                setOnCompletionListener { it.release() }
                start()
            }
        } catch (e: Exception) {
            // Sound not available, continue silently
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
        val isRunning: Boolean = false,
        val isBreak: Boolean = false
    )
}
