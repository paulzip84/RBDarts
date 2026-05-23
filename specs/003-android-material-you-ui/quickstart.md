# Quickstart: Android Material You App Experience

**Feature**: `003-android-material-you-ui`

## Prerequisites

- Android Studio installed.
- Android SDK available at `$HOME/Library/Android/sdk`.
- Android Studio bundled JDK available at `/Applications/Android Studio.app/Contents/jbr/Contents/Home`.
- Existing emulator `Medium_Phone_API_36.1` or another API 36 emulator.
- Optional: Google and Facebook debug provider configuration for full SSO testing.

## Build And Verify

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest lintDebug assembleDebug
```

To verify the Compose UI test source set without requiring a connected device:

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew compileDebugAndroidTestKotlin
```

## Manual UI Smoke

1. Install the debug APK on an emulator or device.
2. Cold launch the app.
3. Confirm the loading page shows the provided RBDarts image, `RBDarts`, and version/build.
4. Confirm unauthenticated users route to login.
5. Confirm Google and Facebook actions are visible, accessible, and show safe failure/cancel states if providers are not configured.
6. Sign in with a configured provider when available.
7. Confirm primary navigation reaches Game Type, Players, Seasons, Handicaps, Scoring, Stats/Summary, and Settings.
8. Create a sample player.
9. Create a sample season.
10. View or edit a player handicap according to current permissions.
11. Start scoring and enter valid/invalid inning values.
12. Background and resume during scoring and confirm state is restored.
13. Rotate or resize the emulator and confirm navigation and scoring remain usable.
14. Enable dark theme and large font and repeat the loading, login, navigation, and scoring smoke path.
15. Capture the debug APK from `android/app/build/outputs/apk/debug/app-debug.apk` for local tester installation when needed.

## Expected Evidence

- Android unit test output.
- Android lint output.
- Emulator screenshots for loading, login, navigation, setup, handicap, and scoring.
- Notes for SSO provider configuration gaps if real provider credentials are not available locally.
- Accessibility smoke notes for TalkBack labels, large font, and contrast.
- APK path and emulator install/launch notes.
