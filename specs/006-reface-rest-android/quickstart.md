# Quickstart: Authenticated Android App Shell Redesign

Feature: `006-reface-rest-android`

## Prerequisites

- Android Studio installed.
- Android SDK available at `$HOME/Library/Android/sdk`.
- Android Studio bundled JDK available at `/Applications/Android Studio.app/Contents/jbr/Contents/Home`.
- Existing redesigned loading and login features committed or present in the working tree.
- Optional emulator `Medium_Phone_API_36.1` or another API 36 emulator.

## Build And Verify

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest compileDebugAndroidTestKotlin lintDebug assembleDebug
```

## Manual UI Smoke

1. Install the debug APK on a stable emulator or device.
2. Launch the app and authenticate or render the authenticated shell test state.
3. Confirm loading and login still use their existing redesigned screens.
4. Confirm authenticated Home uses the premium dark shell.
5. Confirm the top-left hamburger button is visible and labeled.
6. Open the hamburger menu and confirm it lists Home, Game Type, Players, Seasons, Handicaps, Scoring, Settings, and Sign Out.
7. Confirm no bottom navigation bar or navigation rail appears on phone, tablet, or landscape layouts.
8. Navigate to each destination from the menu and confirm the selected state updates and menu closes.
9. Complete representative player, season, handicap, and scoring interactions.
10. Sign out from the hamburger menu and confirm protected content is no longer visible.
11. Repeat key checks with large font/display zoom and TalkBack where possible.
12. Capture screenshots for Home, open drawer, Players/Seasons form, Handicaps, and Scoring.

If no emulator is already attached, list available AVDs and start one before installing:

```bash
ANDROID_HOME="$HOME/Library/Android/sdk" \
"$HOME/Library/Android/sdk/emulator/emulator" -list-avds

ANDROID_HOME="$HOME/Library/Android/sdk" \
"$HOME/Library/Android/sdk/emulator/emulator" -avd Medium_Phone_API_36.1
```

Install and launch the debug APK:

```bash
cd android
ANDROID_HOME="$HOME/Library/Android/sdk" \
"$HOME/Library/Android/sdk/platform-tools/adb" install -r app/build/outputs/apk/debug/app-debug.apk

ANDROID_HOME="$HOME/Library/Android/sdk" \
"$HOME/Library/Android/sdk/platform-tools/adb" shell monkey -p com.rbdarts 1
```

## Expected Evidence

- Android unit test output.
- Android Compose UI test compilation output.
- Android lint output.
- Debug APK build output.
- Privacy-safe diagnostics review.
- Manual screenshot evidence for representative authenticated screens.
