package com.rbdarts.core.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object AuthenticatedShellDefaults {
    val Background = Color(0xFF101828)
    val Surface = Color(0xFF172032)
    val SurfaceRaised = Color(0xFF202A3A)
    val SurfacePressed = Color(0xFF273449)
    val Accent = Color(0xFF22B8A7)
    val AccentStrong = Color(0xFF16A394)
    val Text = Color(0xFFF8FAFC)
    val MutedText = Color(0xFFA7B0C0)
    val Outline = Color(0xFF344055)
    val ErrorContainer = Color(0xFF4C1D1D)
    val OnErrorContainer = Color(0xFFFFD5D5)
    val WarningContainer = Color(0xFF3B2F16)
    val SuccessContainer = Color(0xFF173B2A)

    val ContentPadding = 16.dp
    val SectionSpacing = 12.dp
    val DrawerWidth = 320.dp
}

private val AuthenticatedDarkColorScheme = darkColorScheme(
    primary = AuthenticatedShellDefaults.Accent,
    onPrimary = Color(0xFF062622),
    primaryContainer = AuthenticatedShellDefaults.SurfacePressed,
    onPrimaryContainer = AuthenticatedShellDefaults.Text,
    secondary = Color(0xFF86D8D0),
    onSecondary = Color(0xFF072825),
    secondaryContainer = Color(0xFF16453F),
    onSecondaryContainer = Color(0xFFD5FFFA),
    tertiary = Color(0xFFFFC857),
    onTertiary = Color(0xFF2D2100),
    tertiaryContainer = Color(0xFF4A3900),
    onTertiaryContainer = Color(0xFFFFE5A1),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = AuthenticatedShellDefaults.ErrorContainer,
    onErrorContainer = AuthenticatedShellDefaults.OnErrorContainer,
    background = AuthenticatedShellDefaults.Background,
    onBackground = AuthenticatedShellDefaults.Text,
    surface = AuthenticatedShellDefaults.Surface,
    onSurface = AuthenticatedShellDefaults.Text,
    surfaceVariant = AuthenticatedShellDefaults.SurfaceRaised,
    onSurfaceVariant = AuthenticatedShellDefaults.MutedText,
    outline = AuthenticatedShellDefaults.Outline,
    outlineVariant = Color(0xFF243044),
    inverseSurface = AuthenticatedShellDefaults.Text,
    inverseOnSurface = AuthenticatedShellDefaults.Background,
    inversePrimary = AuthenticatedShellDefaults.AccentStrong
)

@Composable
fun RBDartsAuthenticatedTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AuthenticatedDarkColorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}

@Composable
fun RBDartsAuthenticatedBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AuthenticatedShellDefaults.Background)
    ) {
        content()
    }
}

@Composable
fun RBDartsAuthenticatedScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(AuthenticatedShellDefaults.ContentPadding),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(AuthenticatedShellDefaults.SectionSpacing),
        content = content
    )
}

@Composable
fun RBDartsAuthenticatedPanel(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = AuthenticatedShellDefaults.SurfaceRaised,
                shape = RoundedCornerShape(RBDartsDesignTokens.CornerRadius)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = content
    )
}
