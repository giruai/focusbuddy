package com.giruai.focusbuddy.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.giruai.focusbuddy.ui.theme.Primary
import com.giruai.focusbuddy.ui.theme.PrimaryGradientEnd
import com.giruai.focusbuddy.ui.theme.SurfaceVariant
import com.giruai.focusbuddy.ui.theme.TextPrimary
import com.giruai.focusbuddy.ui.theme.TextSecondary
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ProgressRing(
    progress: Float,
    timeText: String,
    label: String,
    modifier: Modifier = Modifier,
    ringSize: Dp = 280.dp,
    strokeWidth: Dp = 12.dp,
    trackColor: Color = SurfaceVariant,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 1000),
        label = "progress"
    )

    Box(
        modifier = modifier.size(ringSize),
        contentAlignment = Alignment.Center
    ) {
        // Progress ring canvas
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(strokeWidth / 2)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val diameter = minOf(canvasWidth, canvasHeight) - strokeWidth.toPx()
            val radius = diameter / 2
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            // Draw track (background ring)
            drawCircle(
                color = trackColor,
                radius = radius,
                center = Offset(centerX, centerY),
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            // Draw tick marks every 5 minutes (12 marks for 60 min, but we show based on duration)
            val tickCount = 12
            for (i in 0 until tickCount) {
                val angle = (i * 30 - 90) * (Math.PI / 180f)
                val tickStartRadius = radius - strokeWidth.toPx() * 1.5f
                val tickEndRadius = radius - strokeWidth.toPx() * 2.5f

                val startX = centerX + cos(angle).toFloat() * tickStartRadius
                val startY = centerY + sin(angle).toFloat() * tickStartRadius
                val endX = centerX + cos(angle).toFloat() * tickEndRadius
                val endY = centerY + sin(angle).toFloat() * tickEndRadius

                drawLine(
                    color = trackColor.copy(alpha = 0.5f),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 2f
                )
            }

            // Draw progress arc with gradient
            val sweepAngle = animatedProgress * 360f

            // Create gradient brush
            val gradientBrush = Brush.sweepGradient(
                colors = listOf(
                    PrimaryGradientEnd,
                    Primary,
                    PrimaryGradientEnd
                ),
                center = Offset(centerX, centerY)
            )

            // Rotate to start from top (-90 degrees)
            rotate(degrees = -90f, pivot = Offset(centerX, centerY)) {
                // Draw glow effect behind the progress
                drawArc(
                    brush = gradientBrush,
                    startAngle = 0f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(centerX - radius, centerY - radius),
                    size = Size(diameter, diameter),
                    style = Stroke(width = strokeWidth.toPx() * 1.5f, cap = StrokeCap.Round),
                    alpha = 0.3f
                )

                // Draw main progress arc
                drawArc(
                    brush = gradientBrush,
                    startAngle = 0f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(centerX - radius, centerY - radius),
                    size = Size(diameter, diameter),
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )
            }

            // Draw glow dot at the end of progress
            if (sweepAngle > 0) {
                val endAngleRad = (sweepAngle - 90) * (Math.PI / 180f)
                val dotX = centerX + cos(endAngleRad).toFloat() * radius
                val dotY = centerY + sin(endAngleRad).toFloat() * radius

                // Outer glow
                drawCircle(
                    color = Primary.copy(alpha = 0.4f),
                    radius = strokeWidth.toPx() * 1.5f,
                    center = Offset(dotX, dotY)
                )

                // Inner dot
                drawCircle(
                    color = Primary,
                    radius = strokeWidth.toPx() * 0.6f,
                    center = Offset(dotX, dotY)
                )
            }
        }

        // Time text in center
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = timeText,
                fontSize = 72.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary,
                lineHeight = 80.sp
            )
            Text(
                text = label,
                fontSize = 16.sp,
                color = TextSecondary
            )
        }
    }
}
