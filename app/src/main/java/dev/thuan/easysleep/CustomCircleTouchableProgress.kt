package dev.thuan.easysleep

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import dev.thuan.easysleep.ui.theme.scaleScreen
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularProgressBar() {
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
        mutableStateOf(0.0)
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize(0.6f)
            .aspectRatio(1f)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    Log.i(
                        "getRotationAngle",
                        "Content: dragAmount = $dragAmount ${
                            getRotationAngle(
                                change.position,
                                shapeCenter,
                            )
                        } ${
                            getRotationAngle(
                                change.position - Offset(dragAmount.x, dragAmount.y),
                                shapeCenter,
                            )
                        }",
                    )
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

        val x = (shapeCenter.x + cos(Math.toRadians(angle)) * radius).toFloat()
        val y = (shapeCenter.y + sin(Math.toRadians(angle)) * radius).toFloat()

        handleCenter = Offset(x, y)

        drawCircle(color = Color.Black.copy(alpha = 0.10f), style = Stroke(20f), radius = radius)
        drawArc(
            color = Color.Yellow,
            startAngle = 0f,
            sweepAngle = angle.toFloat(),
            useCenter = false,
            style = Stroke(20f),
        )
        drawCircle(color = Color.Yellow, center = shapeCenter, radius = 20f * scaleScreen)
        drawCircle(color = Color.Cyan, center = handleCenter, radius = 60f * scaleScreen)
    }
}

private fun getRotationAngle(currentPosition: Offset, center: Offset): Double {
    Log.i("getRotationAngle", "currentPosition: $currentPosition $center")
    val (dx, dy) = currentPosition - center
    Log.i("getRotationAngle", "dx dy: $dx $dy")
    val theta = atan2(dy, dx).toDouble()

    var angle = Math.toDegrees(theta)

    if (angle < 0) {
        angle += 360.0
    }
    return angle
}

@Preview
@Composable
fun CircularProgressBarScreen(modifier: Modifier = Modifier) {
    val progress = remember { mutableStateOf(0f) }

    Box(modifier = modifier) {
        // Hiển thị thanh progress bar hình tròn
        CircularProgressBar()
//        CircularProgressBar(progress = progress.value)
    }
}
