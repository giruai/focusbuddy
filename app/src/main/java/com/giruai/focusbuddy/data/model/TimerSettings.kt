package com.giruai.focusbuddy.data.model

data class TimerSettings(
    val focusDuration: Int = 25,
    val breakDuration: Int = 5,
    val hapticsEnabled: Boolean = true,
    val soundEnabled: Boolean = false,
    val sessionLabel: String = "Deep Work Session"
)
