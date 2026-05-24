# Verification: Load Screen Visual Redesign

Feature: `005-load-screen-redesign`

## Environment

- Date: 2026-05-22
- Android module: `android/app`
- JDK: `/Applications/Android Studio.app/Contents/jbr/Contents/Home`
- Android SDK: `$HOME/Library/Android/sdk`
- Source image: `/Users/paulzip84/Downloads/RBDarts_Login`

## Commands

### Baseline Compile

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew compileDebugKotlin
```

Result: Passed. `BUILD SUCCESSFUL in 25s`.

### Unit Tests

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest
```

Result: Passed as part of the final combined verification command. `BUILD SUCCESSFUL in 50s`.

### Compose UI Test Compilation

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew compileDebugAndroidTestKotlin
```

Result: Passed as part of the final combined verification command. `BUILD SUCCESSFUL in 50s`. Re-ran after accessibility test assertion cleanup: `BUILD SUCCESSFUL in 14s`.

### Lint

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew lintDebug
```

Result: Passed as part of the final combined verification command. `BUILD SUCCESSFUL in 50s`.

### Debug APK

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew assembleDebug
```

Result: Passed as part of the final combined verification command. `BUILD SUCCESSFUL in 50s`.

## Asset Size

- Source PNG: 2.9 MB.
- Runtime JPEG: 347 KB.
- Runtime dimensions: 806 x 1800.
- Runtime impact: local packaged image, no remote fetch, reduced source size by roughly 88%.

## Manual Smoke

APK install and launch attempt:

- `adb devices` initially showed `emulator-5554`.
- First install attempt hung while emulator package services were unstable.
- Emulator reboot recovered package manager service.
- `adb -s emulator-5554 install -r -t android/app/build/outputs/apk/debug/app-debug.apk` eventually returned `Success`.
- RBDarts launch command returned `Starting: Intent { cmp=com.rbdarts/.app.MainActivity }`.
- Screenshot capture was blocked by emulator instability: Pixel Launcher and Digital Wellbeing system ANR dialogs appeared after reboot, subsequent capture showed a black app frame, and final `adb devices` returned no connected devices.

Status: Manual screenshot evidence is blocked by emulator state and should be retried on a stable emulator/device.
