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
import androidx.compose.material.icons.filled.Stop
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
import androidx.compose.ui.graphics.Color
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

    // Calculate display values
    val (minutes, seconds, progress) = when (timerState) {
        is TimerState.Idle -> Triple(
            timerState.durationMinutes,
            0,
            1f
        )
        is TimerState.Running -> Triple(
            timerState.remainingSeconds / 60,
            timerState.remainingSeconds % 60,
            timerState.progress
        )
        is TimerState.Paused -> Triple(
            timerState.remainingSeconds / 60,
            timerState.remainingSeconds % 60,
            timerState.progress
        )
        is TimerState.Completed -> Triple(0, 0, 0f)
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
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
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
                text = "Deep Work Session",
                fontSize = 16.sp,
                color = TextSecondary,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Control Panel
            ControlPanel(
                isRunning = uiState.isRunning,
                isPaused = timerState is TimerState.Paused,
                onPlayPause = {
                    when (timerState) {
                        is TimerState.Idle -> viewModel.startTimer()
                        is TimerState.Running -> viewModel.pauseTimer()
                        is TimerState.Paused -> viewModel.startTimer()
                        is TimerState.Completed -> viewModel.resetTimer()
                    }
                },
                onStop = {
                    if (uiState.isRunning || timerState is TimerState.Paused) {
                        viewModel.stopTimer()
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
    isRunning: Boolean,
    isPaused: Boolean,
    onPlayPause: () -> Unit,
    onStop: () -> Unit,
    onSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Surface.copy(alpha = 0.9f))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Play/Pause button
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

        // Start/Stop button
        val isActive = isRunning || isPaused
        Button(
            onClick = onStop,
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isActive) Success else Coral
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = if (isActive) "Stop" else "Start",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        // Settings button
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
