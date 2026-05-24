# Verification: Authenticated Android App Shell Redesign

Feature: `006-reface-rest-android`

## Automated Checks

| Check | Status | Notes |
| --- | --- | --- |
| `compileDebugKotlin` | Passed | Ran with Android Studio JBR and local Android SDK after drawer/vector implementation. |
| `testDebugUnitTest` | Passed | Unit coverage includes protected routes, drawer menu contracts, shell state, and privacy-safe navigation diagnostics. |
| `compileDebugAndroidTestKotlin` | Passed | Compose UI test sources compile after hamburger drawer test updates. |
| `lintDebug` | Passed | Lint report generated at `android/app/build/reports/lint-results-debug.html`. |
| `assembleDebug` | Passed | Debug APK generated at `android/app/build/outputs/apk/debug/app-debug.apk`. |

Final bundle passed with:

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest compileDebugAndroidTestKotlin lintDebug assembleDebug
```

Follow-up pass: `compileDebugAndroidTestKotlin` was rerun after adding `NavigationDrawerBackBehaviorTest.kt` and passed.

## Manual Checks

- Emulator install and launch: passed on headless `Medium_Phone_API_36.1`.
- Login screenshot: `specs/006-reface-rest-android/evidence/emulator-login-validated.png`.
- Authenticated Home screenshot: `specs/006-reface-rest-android/evidence/emulator-authenticated-home.png`.
- Drawer screenshot: `specs/006-reface-rest-android/evidence/emulator-authenticated-drawer.png`.
- Drawer hierarchy: `specs/006-reface-rest-android/evidence/emulator-drawer-hierarchy.xml`.
- Scoring screenshot: `specs/006-reface-rest-android/evidence/emulator-scoring-validated.png`.
- Protected sign-out smoke: automated coverage only.
- Large font/display zoom smoke: automated coverage only.

## Performance Notes

- Drawer uses Material 3 state and local vector icons; no network, bitmap, or startup dependency was added.
- Scoring controls remain in the content surface and do not require opening the drawer.
