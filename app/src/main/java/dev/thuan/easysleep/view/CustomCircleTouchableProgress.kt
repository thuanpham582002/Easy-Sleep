package dev.thuan.easysleep.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import dev.thuan.easysleep.ui.theme._dp
import timber.log.Timber
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularProgressBar(
    sweepAnglePercentUpdate: (Float) -> Unit = {},
    stroke: Dp = 3._dp,
) {
    var radius by remember {
        mutableStateOf(0f)
    }

    var shapeCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var handleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var angle by remember {
        mutableStateOf<Double>(270.0)
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    handleCenter += dragAmount
                    angle += getRotationAngle(
                        change.position,
                        shapeCenter,
                    ) - getRotationAngle(
                        change.position - Offset(dragAmount.x, dragAmount.y),
                        shapeCenter,
                    )
                    angle = angle.coerceIn(0.0, 360.0)
                    change.consume()
                }
            },
    ) {
        shapeCenter = center

        radius = size.minDimension / 2
        Timber.i("radius: $radius")
        val x = (shapeCenter.x + cos(Math.toRadians(angle)) * radius).toFloat()
        val y = (shapeCenter.y + sin(Math.toRadians(angle)) * radius).toFloat()

        handleCenter = Offset(x, y)

        drawCircle(
            color = Color.Black.copy(alpha = 0.10f),
            style = Stroke(stroke.toPx()),
            radius = radius,
        )
        val sweepAngle = angle.toFloat().run {
            if (270f > this) {
                this + 360f - 270f
            } else {
                this - 270f
            }
        }

        sweepAnglePercentUpdate(sweepAngle / 360f)
        Timber.i("sweepAngle: $sweepAngle")
        drawArc(
            color = Color.Yellow,
            startAngle = 270f,
            sweepAngle = angle.toFloat().run {
                if (270f > this) {
                    this + 360f - 270f
                } else {
                    this - 270f
                }
            },
            useCenter = false,
            style = Stroke(stroke.toPx()),
        )
//        drawCircle(color = Color.Yellow, center = shapeCenter, radius = 20f * scaleScreen)
        drawCircle(color = Color.Cyan, center = handleCenter, radius = stroke.toPx() * 2.5f)
    }
}

private fun getRotationAngle(currentPosition: Offset, center: Offset): Double {
    Timber.i("currentPosition: $currentPosition $center")
    val (dx, dy) = currentPosition - center
    Timber.i("dx dy: $dx $dy")
    val theta = atan2(dy, dx).toDouble()

    var angle = Math.toDegrees(theta)

    if (angle < 0) {
        angle += 360.0
    }
    return angle
}

@Preview
@Composable
fun CircularProgressBarScreen(
    modifier: Modifier = Modifier,
    maxHourCycle: Int = 5,
    timeFromProcess: (Float) -> Unit = {},
) {
    val progress = remember { mutableStateOf(0f) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false,
    ) {
        // Hiển thị thanh progress bar hình tròn
        CircularProgressBar(sweepAnglePercentUpdate = {
            progress.value = it
            timeFromProcess(it * maxHourCycle)
        }, stroke = 3._dp)
        Column() {
            Text(text = "Setting time", style = MaterialTheme.typography.labelMedium)
            Text(
                text = calculateTimeFromProgress(
                    progress = progress.value,
                    maxHourCycle = maxHourCycle,
                ),
                style = MaterialTheme.typography.headlineLarge,
            )
        }
    }
}

fun calculateTimeFromProgress(progress: Float, maxHourCycle: Int = 5): String {
    val hour = (progress * maxHourCycle).toInt()
    val minute = ((progress * maxHourCycle - hour) * 60).toInt()
    val second = (((progress * maxHourCycle - hour) * 60 - minute) * 60).toInt()
    // xx:xx:xx
    return "${hour.toString().padStart(2, '0')}:${
        minute.toString().padStart(2, '0')
    }:${second.toString().padStart(2, '0')}"
}
