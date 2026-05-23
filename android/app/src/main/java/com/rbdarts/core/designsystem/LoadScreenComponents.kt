package com.rbdarts.core.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rbdarts.R
import com.rbdarts.core.ui.LaunchPresentationState
import com.rbdarts.core.ui.LoadArtworkAsset

@Composable
fun RBDartsLoadScreenSurface(
    state: LaunchPresentationState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF08101D))
            .semantics { contentDescription = state.screenDescription }
            .testTag("load_screen")
    ) {
        LoadScreenArtwork(
            artwork = state.artwork,
            modifier = Modifier.fillMaxSize()
        )
        LoadScreenScrim(Modifier.fillMaxSize())
        LoadScreenStatusPanel(
            appName = state.appName,
            versionLabel = state.versionLabel,
            loadingMessage = state.safeLoadingMessage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 36.dp)
        )
    }
}

@Composable
fun LoadScreenArtwork(
    artwork: LoadArtworkAsset,
    modifier: Modifier = Modifier
) {
    if (artwork.usesPrimaryImage) {
        Image(
            painter = painterResource(id = R.drawable.rbdarts_load_background),
            contentDescription = artwork.contentDescription,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = modifier.testTag("load_screen_artwork")
        )
    } else {
        Box(
            modifier = modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF111827), Color(0xFF03111F), Color(0xFF17221D))
                    )
                )
                .testTag("load_screen_fallback_artwork"),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.rbdarts_loading_mark),
                contentDescription = "RBDarts loading mark",
                modifier = Modifier.size(176.dp)
            )
        }
    }
}

@Composable
fun LoadScreenScrim(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    0.0f to Color.Black.copy(alpha = 0.18f),
                    0.46f to Color.Black.copy(alpha = 0.10f),
                    0.72f to Color.Black.copy(alpha = 0.62f),
                    1.0f to Color.Black.copy(alpha = 0.88f)
                )
            )
            .testTag("load_screen_scrim")
    )
}

@Composable
fun LoadScreenStatusPanel(
    appName: String,
    versionLabel: String,
    loadingMessage: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.testTag("load_screen_status"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = appName,
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = versionLabel,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White.copy(alpha = 0.82f),
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(22.dp))
        CircularProgressIndicator(
            modifier = Modifier
                .size(32.dp)
                .semantics { contentDescription = "Loading" }
                .testTag("load_screen_progress"),
            color = Color(0xFF23C7B7),
            trackColor = Color.White.copy(alpha = 0.28f),
            strokeWidth = 3.dp
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = loadingMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.90f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
