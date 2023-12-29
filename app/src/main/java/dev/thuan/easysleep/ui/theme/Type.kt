package dev.thuan.easysleep.ui.theme

import android.content.res.Resources
import androidx.compose.material3.Typography
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import dev.thuan.easysleep.R

val scaleScreen = (Resources.getSystem().configuration.smallestScreenWidthDp * 1.0f / 392).run {
    if (this > 1) 1f else this
}

/**
 * Creates a SP unit [TextUnit]
 */
@Stable
val Float._sp: TextUnit get() = this.sp * scaleScreen

/**
 * Creates an EM unit [TextUnit]
 */
@Stable
val Float._em: TextUnit get() = this.em * scaleScreen

/**
 * Creates a SP unit [TextUnit]
 */
@Stable
val Double._sp: TextUnit get() = this.sp * scaleScreen

/**
 * Creates an EM unit [TextUnit]
 */
@Stable
val Double._em: TextUnit get() = this.em * scaleScreen

/**
 * Creates a SP unit [TextUnit]
 */
@Stable
val Int._sp: TextUnit get() = this.sp * scaleScreen

/**
 * Creates an EM unit [TextUnit]
 */
@Stable
val Int._em: TextUnit get() = this.em * scaleScreen

@Stable
inline val Int._dp: Dp get() = Dp(value = (this * scaleScreen))

/**
 * Create a [Dp] using a [Double]:
 *     val left = 10.0
 *     val x = left.dp
 *     // -- or --
 *     val y = 10.0.dp
 */
@Stable
inline val Double._dp: Dp get() = Dp(value = (this * scaleScreen).toFloat())

/**
 * Create a [Dp] using a [Float]:
 *     val left = 10f
 *     val x = left.dp
 *     // -- or --
 *     val y = 10f.dp
 */
@Stable
inline val Float._dp: Dp get() = Dp(value = this * scaleScreen)

private val Montserrat = FontFamily(
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semibold, FontWeight.SemiBold),
)

// Set of Material typography styles to start with
val Typography = Typography().run {
    copy(
        displayLarge = this.displayLarge.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        displayMedium = this.displayMedium.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        displaySmall = this.displaySmall.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        headlineLarge = this.headlineLarge.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        headlineMedium = this.headlineMedium.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        headlineSmall = this.headlineSmall.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        titleLarge = this.titleLarge.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        titleMedium = this.titleMedium.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        titleSmall = this.titleSmall.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        bodyLarge = this.bodyLarge.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        bodyMedium = this.bodyMedium.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        bodySmall = this.bodySmall.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        labelLarge = this.labelLarge.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        labelMedium = this.labelMedium.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
        labelSmall = this.labelSmall.run {
            copy(
                fontFamily = Montserrat,
                fontSize = this.fontSize * scaleScreen,
                lineHeight = this.lineHeight * scaleScreen,
            )
        },
    )
}
