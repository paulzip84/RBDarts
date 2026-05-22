package com.rbdarts.app

import android.app.Application
import com.rbdarts.core.launch.ReleaseConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RBDartsApplication : Application() {
    lateinit var releaseConfiguration: ReleaseConfiguration
        private set

    override fun onCreate() {
        super.onCreate()
        releaseConfiguration = ReleaseConfiguration.fromContext(this)
        releaseConfiguration.validateForLaunch()
    }
}
