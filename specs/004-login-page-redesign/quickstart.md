# Quickstart: Login Page Visual Redesign

Feature: `004-login-page-redesign`

## Prerequisites

- Android Studio installed.
- Android SDK available at `$HOME/Library/Android/sdk`.
- Android Studio bundled JDK available at `/Applications/Android Studio.app/Contents/jbr/Contents/Home`.
- Optional emulator `Medium_Phone_API_36.1` for manual smoke.

## Build And Verify

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest compileDebugAndroidTestKotlin lintDebug assembleDebug
```

## Manual UI Smoke

1. Install the debug APK on an emulator.
2. Cold launch unauthenticated.
3. Confirm loading routes to the redesigned login page.
4. Confirm the login page has a dark premium visual treatment inspired by the reference image.
5. Confirm the screen shows RBDarts branding, welcome headline, and concise SSO copy.
6. Confirm no email field, password field, password reset link, or first-party password copy appears.
7. Confirm Google and Facebook provider actions are visible without scrolling on a compact phone.
8. Tap Google and verify selected-provider loading behavior.
9. Trigger or simulate provider failure and verify safe retry copy.
10. Trigger or simulate cancellation and verify the login page remains usable.
11. Verify support, privacy policy, account deletion, and sign-in help remain reachable.
12. Repeat in dark theme, light theme, large font, and after rotation.
13. Confirm provider buttons cannot be tapped repeatedly while a provider is loading.
14. Confirm support links remain secondary and reachable.

## Expected Evidence

- Android unit test output.
- Android Compose UI test compilation output.
- Android lint output.
- Debug APK build output.
- Emulator screenshots for default login, provider loading/failure, and large-font or dark-theme state.
- Note confirming no first-party password UI was introduced.
- Secret scan result for login UI and provider mark assets.
