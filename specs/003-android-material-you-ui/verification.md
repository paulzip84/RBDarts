# Verification: Android Material You UI

Feature: `003-android-material-you-ui`

## Automated Verification

| Date | Command | Result | Notes |
| --- | --- | --- | --- |
| 2026-05-22 | `JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew compileDebugKotlin` | Passed | Material You app sources compile. |
| 2026-05-22 | `JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew testDebugUnitTest` | Passed | Route, UI state, diagnostics, forms, handicap, and scoring unit tests passed. |
| 2026-05-22 | `JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew compileDebugAndroidTestKotlin` | Passed | Compose UI test sources compile. |
| 2026-05-22 | `JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew lintDebug` | Passed | Android lint report generated successfully. |
| 2026-05-22 | `JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew assembleDebug` | Passed | Debug APK built at `android/app/build/outputs/apk/debug/app-debug.apk`. |
| 2026-05-22 | `JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew testDebugUnitTest lintDebug compileDebugAndroidTestKotlin assembleDebug` | Passed | Final combined verification on latest code. |
| 2026-05-22 | `adb install -r android/app/build/outputs/apk/debug/app-debug.apk` and `adb shell am start -n com.rbdarts/.app.MainActivity` | Passed | Installed and launched on `emulator-5554`; evidence screenshots saved. |

## Notes

- Google Services plugin was skipped because `android/app/google-services.json` is intentionally absent from the repository.
- APK assembly produced no build failure. An earlier package run emitted ASM class-resolution warnings for optional runtime/tooling classes, but the final combined verification passed.
- Emulator display slept once during evidence capture; the device was woken and the login screen was captured successfully.
