package com.rbdarts.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rbdarts.core.designsystem.RBDartsMaterialYouTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val configuration = (application as RBDartsApplication).releaseConfiguration
            RBDartsMaterialYouTheme {
                RBDartsAppRoot(configuration)
            }
        }
    }
}
