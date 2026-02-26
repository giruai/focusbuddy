package com.giruai.focusbuddy.domain.model

sealed class TimerState {
    abstract val durationMinutes: Int
    
    data class Idle(override val durationMinutes: Int = 25) : TimerState()
    
    data class Running(
        override val durationMinutes: Int,
        val remainingSeconds: Int,
        val totalSeconds: Int = durationMinutes * 60
    ) : TimerState() {
        val progress: Float = remainingSeconds.toFloat() / totalSeconds.toFloat()
    }
    
    data class Paused(
        override val durationMinutes: Int,
        val remainingSeconds: Int,
        val totalSeconds: Int = durationMinutes * 60
    ) : TimerState() {
        val progress: Float = remainingSeconds.toFloat() / totalSeconds.toFloat()
    }
    
    data class Completed(override val durationMinutes: Int = 25) : TimerState()
}

enum class TimerMode {
    FOCUS, BREAK
}
