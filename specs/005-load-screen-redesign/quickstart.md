# Quickstart: Load Screen Visual Redesign

Feature: `005-load-screen-redesign`

## Prerequisites

- Android Studio installed.
- Android SDK available at `$HOME/Library/Android/sdk`.
- Android Studio bundled JDK available at `/Applications/Android Studio.app/Contents/jbr/Contents/Home`.
- Attached image source available at `/Users/paulzip84/Downloads/RBDarts_Login`.
- Optimized runtime asset available at `android/app/src/main/res/drawable-nodpi/rbdarts_load_background.jpg`.
- Optional emulator `Medium_Phone_API_36.1` or another API 36 emulator.

## Build And Verify

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest compileDebugAndroidTestKotlin lintDebug assembleDebug
```

## Manual UI Smoke

1. Install the debug APK on an emulator or device.
2. Cold launch the app from a stopped state.
3. Confirm the load screen uses the optimized derivative of `RBDarts_Login`.
4. Confirm `RBDarts`, version/build, and loading status are readable over the artwork.
5. Confirm startup routes to login when unauthenticated.
6. Confirm authenticated routing still reaches home when a valid session is available.
7. Rotate the device and confirm image crop and text remain intentional.
8. Repeat in dark theme, light theme, large font, and display zoom.
9. Disable or simulate missing primary artwork and confirm branded fallback loads.
10. Capture screenshot evidence for at least the default load screen.

## Asset Review

Before committing runtime image assets:

- Confirm source image dimensions and format.
- Create an optimized Android-safe derivative.
- Check file size and APK impact.
- Scan source and derivative for scripts, remote references, secrets, and unsafe metadata.
- Document focal region and crop decisions.

The implemented derivative was created with:

```bash
sips -Z 1800 -s format jpeg -s formatOptions 82 \
  /Users/paulzip84/Downloads/RBDarts_Login \
  --out android/app/src/main/res/drawable-nodpi/rbdarts_load_background.jpg
```

## Expected Evidence

- Android unit test output.
- Android Compose UI test compilation output.
- Android lint output.
- Debug APK build output.
- Asset size/type review.
- Secret/metadata scan result.
- Emulator screenshot of redesigned load screen.
