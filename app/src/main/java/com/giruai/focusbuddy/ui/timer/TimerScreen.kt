package com.giruai.focusbuddy.ui.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.giruai.focusbuddy.domain.model.TimerState
import com.giruai.focusbuddy.ui.components.ProgressRing
import com.giruai.focusbuddy.ui.theme.Background
import com.giruai.focusbuddy.ui.theme.Coral
import com.giruai.focusbuddy.ui.theme.CoralLight
import com.giruai.focusbuddy.ui.theme.Primary
import com.giruai.focusbuddy.ui.theme.Success
import com.giruai.focusbuddy.ui.theme.SuccessDark
import com.giruai.focusbuddy.ui.theme.Surface
import com.giruai.focusbuddy.ui.theme.SurfaceVariant
import com.giruai.focusbuddy.ui.theme.TextPrimary
import com.giruai.focusbuddy.ui.theme.TextSecondary

@Composable
fun TimerScreen(
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val timerState = uiState.timerState
    val settings = uiState.settings

    // Calculate display values
    val (minutes, seconds, progress) = when (timerState) {
        is TimerState.Idle -> Triple(
            settings.focusDuration,
            0,
            0f  // Empty at start
        )
        is TimerState.Running -> Triple(
            timerState.remainingSeconds / 60,
            timerState.remainingSeconds % 60,
            timerState.progress  // Fills up as time passes
        )
        is TimerState.Paused -> Triple(
            timerState.remainingSeconds / 60,
            timerState.remainingSeconds % 60,
            timerState.progress  // Fills up as time passes
        )
        is TimerState.Completed -> Triple(0, 0, 1f)  // Full at completion
    }

    val timeText = String.format("%02d:%02d", minutes, seconds)

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
                    text = "Focus Buddy",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            // Progress Ring with Time
            ProgressRing(
                progress = progress,
                timeText = timeText,
                label = "mins",
                modifier = Modifier.padding(top = 40.dp)
            )

            // Session Label
            Text(
                text = settings.sessionLabel,
                fontSize = 16.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Control Panel
            ControlPanel(
                timerState = timerState,
                onPlayPause = {
                    when (timerState) {
                        is TimerState.Idle -> viewModel.startTimer()
                        is TimerState.Running -> viewModel.pauseTimer()
                        is TimerState.Paused -> viewModel.startTimer()
                        is TimerState.Completed -> viewModel.resetTimer()
                    }
                },
                onStartStop = {
                    when (timerState) {
                        is TimerState.Idle -> viewModel.startTimer()
                        is TimerState.Running -> viewModel.stopTimer()
                        is TimerState.Paused -> viewModel.stopTimer()
                        is TimerState.Completed -> viewModel.resetTimer()
                    }
                },
                onSettings = onNavigateToSettings,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)
            )
        }
    }
}

@Composable
private fun ControlPanel(
    timerState: TimerState,
    onPlayPause: () -> Unit,
    onStartStop: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isRunning = timerState is TimerState.Running
    val isPaused = timerState is TimerState.Paused
    val isIdle = timerState is TimerState.Idle
    val isCompleted = timerState is TimerState.Completed

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Surface.copy(alpha = 0.9f))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Play/Pause button (shows play when paused or completed, pause when running)
        // Hidden when idle
        if (isRunning || isPaused || isCompleted) {
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
            // Spacer to maintain layout when idle
            Box(modifier = Modifier.size(48.dp))
        }

        // Center: Start/Stop button (main action)
        val buttonText = when {
            isRunning -> "Stop"
            isPaused -> "Stop"
            isCompleted -> "Reset"
            else -> "Start"
        }

        val buttonColors = when {
            isRunning -> ButtonDefaults.buttonColors(containerColor = Success)
            isPaused -> ButtonDefaults.buttonColors(containerColor = Success)
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

        // Right: Settings button
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
