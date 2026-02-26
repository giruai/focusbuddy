package com.giruai.focusbuddy.ui.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giruai.focusbuddy.domain.model.TimerState
import com.giruai.focusbuddy.ui.components.ProgressRing
import com.giruai.focusbuddy.ui.theme.Background
import com.giruai.focusbuddy.ui.theme.Coral
import com.giruai.focusbuddy.ui.theme.Primary
import com.giruai.focusbuddy.ui.theme.Success
import com.giruai.focusbuddy.ui.theme.Surface
import com.giruai.focusbuddy.ui.theme.SurfaceVariant
import com.giruai.focusbuddy.ui.theme.TextPrimary
import com.giruai.focusbuddy.ui.theme.TextSecondary

data class TimerDisplay(
    val minutes: Int,
    val seconds: Int,
    val progress: Float,
    val label: String,
    val isCompleted: Boolean,
    val isBreak: Boolean
)

@Composable
fun TimerScreen(
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val timerState = uiState.timerState
    val settings = uiState.settings
    val isBreak = uiState.isBreak

    // Calculate display values based on state
    val display = when (timerState) {
        is TimerState.Idle -> TimerDisplay(
            settings.focusDuration, 0, 0f,
            "Time to focus!", false, false
        )
        is TimerState.Running -> TimerDisplay(
            timerState.remainingSeconds / 60,
            timerState.remainingSeconds % 60,
            timerState.progress,
            settings.sessionLabel, false, false
        )
        is TimerState.Paused -> TimerDisplay(
            timerState.remainingSeconds / 60,
            timerState.remainingSeconds % 60,
            timerState.progress,
            settings.sessionLabel, false, false
        )
        is TimerState.Completed -> TimerDisplay(
            0, 0, 1f, "Focus Complete!", true, false
        )
        is TimerState.BreakRunning -> TimerDisplay(
            timerState.remainingSeconds / 60,
            timerState.remainingSeconds % 60,
            timerState.progress,
            "Break Time", false, true
        )
        is TimerState.BreakPaused -> TimerDisplay(
            timerState.remainingSeconds / 60,
            timerState.remainingSeconds % 60,
            timerState.progress,
            "Break Paused", false, true
        )
        is TimerState.BreakCompleted -> TimerDisplay(
            0, 0, 1f, "Break Complete!", true, true
        )
    }

    val timeText = String.format("%02d:%02d", display.minutes, display.seconds)
    val displayLabel = if (display.isBreak) display.label else settings.sessionLabel

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 48.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (display.isBreak) "Break Time" else "Focus Buddy",
                    style = MaterialTheme.typography.titleLarge,
                    color = if (display.isBreak) Success else TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            // Progress Ring with Time
            ProgressRing(
                progress = display.progress,
                timeText = timeText,
                label = "mins",
                modifier = Modifier.padding(top = 40.dp),
                color = if (display.isBreak) Success else Primary
            )

            // Session Label / Status
            Text(
                text = displayLabel,
                fontSize = 16.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Control Panel
            when {
                display.isCompleted -> CompletionControls(
                    isBreak = display.isBreak,
                    onStartBreak = { viewModel.startBreak() },
                    onSkipBreak = { viewModel.skipBreak() },
                    onStartFocus = { viewModel.startTimer() },
                    onSettings = onNavigateToSettings,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)
                )
                else -> ControlPanel(
                    timerState = timerState,
                    isBreak = display.isBreak,
                    onPlayPause = {
                        when (timerState) {
                            is TimerState.Idle -> viewModel.startTimer()
                            is TimerState.Running -> viewModel.pauseTimer()
                            is TimerState.Paused -> viewModel.startTimer()
                            is TimerState.BreakRunning -> viewModel.pauseTimer()
                            is TimerState.BreakPaused -> viewModel.startTimer()
                            else -> {}
                        }
                    },
                    onStartStop = {
                        when (timerState) {
                            is TimerState.Idle -> viewModel.startTimer()
                            is TimerState.Running -> viewModel.stopTimer()
                            is TimerState.Paused -> viewModel.stopTimer()
                            is TimerState.BreakRunning -> viewModel.stopTimer()
                            is TimerState.BreakPaused -> viewModel.stopTimer()
                            else -> {}
                        }
                    },
                    onSettings = onNavigateToSettings,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)
                )
            }
        }
    }
}

@Composable
private fun CompletionControls(
    isBreak: Boolean,
    onStartBreak: () -> Unit,
    onSkipBreak: () -> Unit,
    onStartFocus: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Surface.copy(alpha = 0.9f))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isBreak) {
            Text(
                text = "Ready to focus?",
                fontSize = 18.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = onStartFocus,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Coral),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Start Focus",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        } else {
            Text(
                text = "Take a break?",
                fontSize = 18.sp,
                color = TextPrimary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = onStartBreak,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Success),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Start Break",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onSkipBreak,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Skip Break",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        IconButton(
            onClick = onSettings,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(SurfaceVariant)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = TextPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun ControlPanel(
    timerState: TimerState,
    isBreak: Boolean,
    onPlayPause: () -> Unit,
    onStartStop: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isRunning = timerState is TimerState.Running || timerState is TimerState.BreakRunning
    val isPaused = timerState is TimerState.Paused || timerState is TimerState.BreakPaused

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Surface.copy(alpha = 0.9f))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isRunning || isPaused) {
            IconButton(
                onClick = onPlayPause,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(SurfaceVariant)
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isRunning) "Pause" else "Play",
                    tint = TextPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        } else {
            Box(modifier = Modifier.size(48.dp))
        }

        val buttonText = when {
            isRunning -> "Stop"
            isPaused -> "Stop"
            else -> "Start"
        }

        val buttonColors = when {
            isBreak -> ButtonDefaults.buttonColors(containerColor = Success)
            isRunning -> ButtonDefaults.buttonColors(containerColor = Success)
            else -> ButtonDefaults.buttonColors(containerColor = Coral)
        }

        Button(
            onClick = onStartStop,
            shape = RoundedCornerShape(24.dp),
            colors = buttonColors,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = buttonText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 12.dp)
            )
        }

        IconButton(
            onClick = onSettings,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(SurfaceVariant)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = TextPrimary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
