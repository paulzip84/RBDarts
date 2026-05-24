package com.rbdarts.core.designsystem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rbdarts.R
import com.rbdarts.core.ui.AccountSupportLink
import com.rbdarts.core.ui.LoginMessage
import com.rbdarts.core.ui.LoginMessageType
import com.rbdarts.core.ui.SsoProviderAction
import com.rbdarts.core.ui.SsoProviderId

private val LoginBackground = Color(0xFF101828)
private val LoginSurface = Color(0xFF172032)
private val LoginProviderSurface = Color(0xFF202A3A)
private val LoginAccent = Color(0xFF22B8A7)
private val LoginText = Color(0xFFF8FAFC)
private val LoginMutedText = Color(0xFFA7B0C0)

@Composable
fun RBDartsPremiumLoginSurface(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(LoginBackground)
            .semantics { contentDescription = "RBDarts sign in screen" }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .align(Alignment.TopCenter)
        ) {
            val center = Offset(size.width / 2f, -24f)
            repeat(5) { index ->
                drawCircle(
                    color = Color.White.copy(alpha = 0.035f),
                    radius = 80f + index * 48f,
                    center = center,
                    style = Stroke(width = 2f)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = content
        )
    }
}

@Composable
fun LoginBrandHeader(
    brandName: String,
    headline: String,
    supportingText: String,
    logoContentDescription: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.rbdarts_loading_mark),
            contentDescription = logoContentDescription,
            modifier = Modifier.size(88.dp)
        )
        Text(
            text = headline,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = LoginText,
            textAlign = TextAlign.Center
        )
        Text(
            text = supportingText,
            style = MaterialTheme.typography.bodyLarge,
            color = LoginMutedText,
            textAlign = TextAlign.Center
        )
        Text(
            text = brandName,
            style = MaterialTheme.typography.labelLarge,
            color = LoginAccent
        )
    }
}

@Composable
fun LoginMessageBanner(
    message: LoginMessage,
    modifier: Modifier = Modifier
) {
    val containerColor = when (message.type) {
        LoginMessageType.Error -> Color(0xFF4C1D1D)
        LoginMessageType.Warning -> Color(0xFF3B2F16)
        LoginMessageType.Info -> Color(0xFF1D2D4C)
        LoginMessageType.Success -> Color(0xFF173B2A)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor, RoundedCornerShape(RBDartsDesignTokens.CornerRadius))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(message.title, style = MaterialTheme.typography.titleSmall, color = LoginText)
        Text(message.body, style = MaterialTheme.typography.bodyMedium, color = LoginMutedText)
    }
}

@Composable
fun LoginSsoDivider(
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.28f))
        Text(label, style = MaterialTheme.typography.labelMedium, color = LoginMutedText)
        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha = 0.28f))
    }
}

@Composable
fun LoginProviderButton(
    action: SsoProviderAction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = action.canClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .semantics { contentDescription = action.accessibilityLabel },
        shape = RoundedCornerShape(RBDartsDesignTokens.CornerRadius),
        colors = ButtonDefaults.buttonColors(
            containerColor = LoginProviderSurface,
            contentColor = LoginText,
            disabledContainerColor = LoginProviderSurface.copy(alpha = 0.62f),
            disabledContentColor = LoginMutedText
        )
    ) {
        if (action.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = LoginText,
                strokeWidth = 2.dp
            )
        } else {
            Image(
                painter = painterResource(id = action.providerIconRes()),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.size(12.dp))
        Text(
            text = action.label,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun LoginSupportLinks(
    links: List<AccountSupportLink>,
    onOpenLink: (AccountSupportLink) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        links.forEach { link ->
            TextButton(onClick = { onOpenLink(link) }) {
                Text(
                    text = link.label,
                    color = if (link.kind.name == "SignInHelp") LoginAccent else LoginMutedText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun SsoProviderAction.providerIconRes(): Int =
    when (providerId) {
        SsoProviderId.Google -> R.drawable.ic_google_mark
        SsoProviderId.Facebook -> R.drawable.ic_facebook_mark
    }
