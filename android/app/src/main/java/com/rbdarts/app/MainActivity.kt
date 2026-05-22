package com.rbdarts.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rbdarts.feature.settings.PrivacyAndSupportScreen
import com.rbdarts.feature.standalonegame.StandaloneGameSetupScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val configuration = (application as RBDartsApplication).releaseConfiguration
            StandaloneGameSetupScreen {
                PrivacyAndSupportScreen(configuration)
            }
        }
    }
}
