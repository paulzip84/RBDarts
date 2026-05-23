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
    primary = Color(0xFF9EDDB2),
    onPrimary = Color(0xFF00391E),
    primaryContainer = Color(0xFF00522D),
    onPrimaryContainer = Color(0xFFB9F0CC),
    secondary = Color(0xFFC5C98D),
    onSecondary = Color(0xFF2E3300),
    secondaryContainer = Color(0xFF444914),
    onSecondaryContainer = Color(0xFFE1E5A7),
    tertiary = Color(0xFFFFB4A6),
    onTertiary = Color(0xFF541D12),
    tertiaryContainer = Color(0xFF703528),
    onTertiaryContainer = Color(0xFFFFDAD3),
    error = Color(0xFFFFB4AB),
    background = Color(0xFF111411),
    surface = Color(0xFF111411),
    surfaceVariant = Color(0xFF414941),
    outline = Color(0xFF8B9389)
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
