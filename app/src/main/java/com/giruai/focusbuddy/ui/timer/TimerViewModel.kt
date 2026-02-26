package com.giruai.focusbuddy.ui.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giruai.focusbuddy.domain.model.TimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun startTimer() {
        val currentState = _uiState.value.timerState
        val totalSeconds = when (currentState) {
            is TimerState.Idle -> currentState.durationMinutes * 60
            is TimerState.Paused -> currentState.remainingSeconds
            is TimerState.Running -> return // Already running
            is TimerState.Completed -> currentState.durationMinutes * 60
        }
        val durationMinutes = currentState.durationMinutes

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var remaining = totalSeconds
            while (remaining > 0) {
                _uiState.update {
                    it.copy(
                        timerState = TimerState.Running(
                            durationMinutes = durationMinutes,
                            remainingSeconds = remaining,
                            totalSeconds = durationMinutes * 60
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
                    timerState = TimerState.Completed(durationMinutes),
                    isRunning = false
                )
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        val current = _uiState.value.timerState as? TimerState.Running ?: return
        _uiState.update {
            it.copy(
                timerState = TimerState.Paused(
                    durationMinutes = current.durationMinutes,
                    remainingSeconds = current.remainingSeconds,
                    totalSeconds = current.totalSeconds
                ),
                isRunning = false
            )
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        val duration = (_uiState.value.timerState as? TimerState.Running)?.durationMinutes
            ?: (_uiState.value.timerState as? TimerState.Paused)?.durationMinutes
            ?: 25
        _uiState.update {
            it.copy(
                timerState = TimerState.Idle(duration),
                isRunning = false
            )
        }
    }

    fun resetTimer() {
        timerJob?.cancel()
        val duration = _uiState.value.timerState.durationMinutes
        _uiState.update {
            it.copy(
                timerState = TimerState.Idle(duration),
                isRunning = false
            )
        }
    }

    fun setDuration(minutes: Int) {
        _uiState.update {
            it.copy(
                timerState = TimerState.Idle(minutes)
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    data class TimerUiState(
        val timerState: TimerState = TimerState.Idle(25),
        val isRunning: Boolean = false
    )
}
