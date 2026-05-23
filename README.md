# RBDarts

Native Swift and Kotlin Baseball Darts app initialized with GitHub Spec Kit for
spec-driven development.

## Project Principles

RBDarts is governed by a security-first constitution. The apps are expected to use
native Swift architecture on iOS, native Kotlin architecture on Android, trusted
SSO providers for authentication, and measurable performance and stability gates.

## Repository Layout

- `ios/`: SwiftUI iOS app, XCTest, and XCUITest scaffolding.
- `android/`: Kotlin/Jetpack Compose Android app and test scaffolding.
- `firebase/`: Firestore rules, indexes, and Cloud Functions TypeScript.
- `launch/`: Non-secret app store launch package, beta evidence, release gates,
  privacy disclosures, and runbooks.
- `scripts/launch/`: Launch artifact and public URL validation scripts.
- `shared-contracts/`: JSON schemas and parity fixtures for scoring rules.
- `specs/001-baseball-darts-app/`: Speckit spec, plan, tasks, contracts, and
  validation notes.
- `specs/002-launch-mobile-apps/`: Speckit launch-readiness specification,
  plan, tasks, contracts, and quickstart.
- `specs/003-android-material-you-ui/`: Android Material You redesign
  specification, implementation tasks, tests, and launch evidence.

## Local Commands

Firebase function/domain tests can run with the system Node runtime:

```bash
node --test --experimental-strip-types firebase/functions/test/*.test.ts
```

Launch artifact tests and validators:

```bash
node --test scripts/launch/validate-launch-artifacts.test.mjs
node scripts/launch/validate-launch-artifacts.mjs
node scripts/launch/check-public-urls.mjs \
  launch/privacy/public-url-verification.md \
  launch/app-store/metadata/en-US.json \
  launch/google-play/metadata/en-US.json
```

Swift source can be type-checked from this repository with local module caches:

```bash
CLANG_MODULE_CACHE_PATH=/private/tmp/rbdarts-clang-cache \
SWIFT_MODULE_CACHE_PATH=/private/tmp/rbdarts-swift-cache \
swiftc -typecheck ios/RBDarts/Shared/Domain/*.swift \
  ios/RBDarts/Shared/Data/*.swift \
  ios/RBDarts/Shared/Security/*.swift \
  ios/RBDarts/Shared/Observability/*.swift \
  ios/RBDarts/Shared/DesignSystem/*.swift \
  ios/RBDarts/Shared/Launch/*.swift \
  ios/RBDarts/Features/**/*.swift \
  ios/RBDarts/App/RBDartsApp.swift
```

Full iOS builds require Xcode, selected with `xcode-select`, plus resolved Swift
Package dependencies. Full Android builds require Android Studio or a local
Gradle install/wrapper.

Android unit and lint verification can run through the checked-in wrapper with
Android Studio's bundled JDK:

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest lintDebug
```

The Android Google Services plugin is skipped when `android/app/google-services.json`
is absent so local verification does not require committing Firebase secrets.

Android Material You feature verification adds Compose UI test compilation and
APK assembly:

```bash
cd android
JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" \
ANDROID_HOME="$HOME/Library/Android/sdk" \
./gradlew testDebugUnitTest compileDebugAndroidTestKotlin lintDebug assembleDebug
```

The debug APK for local smoke testing is produced at
`android/app/build/outputs/apk/debug/app-debug.apk`.

iOS `xcodebuild` verification requires full Xcode, not only Command Line Tools.
After installing Xcode, select it and run:

```bash
sudo xcode-select -s /Applications/Xcode.app/Contents/Developer
sudo xcodebuild -runFirstLaunch
IOS_DESTINATION="platform=iOS Simulator,name=iPhone 16" \
  scripts/ios/verify-xcode.sh
```

## Security Setup Notes

- Do not commit real Firebase config files, provider secrets, signing keys,
  provisioning profiles, keystores, or `.env` files.
- Use `ios/RBDarts/Resources/FirebaseConfig.example.plist`,
  `android/app/src/main/res/values/firebase_config_example.xml`, and
  `firebase/.env.example` as templates only.
- Use `ios/RBDarts/Resources/ReleaseConfig.example.plist`,
  `android/app/src/main/res/values/release_config_example.xml`, and
  `firebase/.env.production.example` as launch templates only.
- Keep all signed builds, Apple provisioning files, Android keystores, store
  credentials, production Firebase configs, and reviewer credentials out of git.
- SSO is the only planned authentication path for MVP. First-party passwords are
  intentionally out of scope.
- Firestore rules default deny and reserve official aggregate writes for trusted
  backend operations.

## Launch Readiness

Start with `launch/INDEX.md` and
`specs/002-launch-mobile-apps/quickstart.md`. The committed launch artifacts are
templates and evidence records only; signed binaries, reviewer credentials,
provider console exports, and store-account materials must stay in secure
platform or CI storage.

For the Android Material You workflow, use
`specs/003-android-material-you-ui/quickstart.md` and capture screenshots from
the loading, login, game type, players, seasons, handicaps, scoring, and settings
screens before Play Store submission.

For the focused Android login redesign, use
`specs/004-login-page-redesign/quickstart.md`. The login screen must remain
SSO-only: no email fields, password fields, or password reset language.

For the Android load-screen visual redesign, use
`specs/005-load-screen-redesign/quickstart.md`. The load screen uses the local
optimized asset `android/app/src/main/res/drawable-nodpi/rbdarts_load_background.jpg`
with a fallback to the existing vector mark.

## Speckit Workflow

Use the installed Codex skills from this project directory:

- `$speckit-constitution`
- `$speckit-specify`
- `$speckit-plan`
- `$speckit-tasks`
- `$speckit-implement`
