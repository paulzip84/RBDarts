package com.rbdarts.core.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

private val RBDartsLightScheme = lightColorScheme(
    primary = Color(0xFF156B45),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB9F0CC),
    onPrimaryContainer = Color(0xFF002111),
    secondary = Color(0xFF5B5F2D),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE1E5A7),
    onSecondaryContainer = Color(0xFF1A1D00),
    tertiary = Color(0xFF8A3F32),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDAD3),
    onTertiaryContainer = Color(0xFF3A0801),
    error = Color(0xFFB3261E),
    background = Color(0xFFFBFDF8),
    surface = Color(0xFFFBFDF8),
    surfaceVariant = Color(0xFFDDE5DA),
    outline = Color(0xFF727970)
)

private val RBDartsDarkScheme = darkColorScheme(
    primary = Color(0xFF22B8A7),
    onPrimary = Color(0xFF062622),
    primaryContainer = Color(0xFF273449),
    onPrimaryContainer = Color(0xFFF8FAFC),
    secondary = Color(0xFF86D8D0),
    onSecondary = Color(0xFF072825),
    secondaryContainer = Color(0xFF16453F),
    onSecondaryContainer = Color(0xFFD5FFFA),
    tertiary = Color(0xFFFFC857),
    onTertiary = Color(0xFF2D2100),
    tertiaryContainer = Color(0xFF4A3900),
    onTertiaryContainer = Color(0xFFFFE5A1),
    error = Color(0xFFFFB4AB),
    background = Color(0xFF101828),
    surface = Color(0xFF172032),
    surfaceVariant = Color(0xFF202A3A),
    outline = Color(0xFF344055)
)

private val RBDartsShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(RBDartsDesignTokens.CornerRadius),
    medium = RoundedCornerShape(RBDartsDesignTokens.CornerRadius),
    large = RoundedCornerShape(12.dp),
    extraLarge = RoundedCornerShape(16.dp)
)

@Composable
fun RBDartsMaterialYouTheme(
    useDynamicColor: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = when {
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && darkTheme ->
            dynamicDarkColorScheme(context)
        useDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
            dynamicLightColorScheme(context)
        darkTheme -> RBDartsDarkScheme
        else -> RBDartsLightScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        shapes = RBDartsShapes,
        content = content
    )
}
