# Dependency Security And License Review

## Current Dependency Plan

- iOS: SwiftUI, Observation, Security/Keychain, AuthenticationServices,
  Firebase Auth, Firestore, Crashlytics, Performance, Facebook Login.
- Android: Jetpack Compose, Lifecycle, Coroutines, Hilt, Room, DataStore,
  Firebase, Credential Manager, Facebook Login.
- Backend: Firebase Admin and Firebase Functions.

## Current Result

- Real secret-bearing Firebase configuration files are ignored.
- Dependency versions are pinned in Gradle and package manifests where scaffolded.
- Full license and vulnerability scan requires dependency installation.

## Required Pre-Release Checks

- Run Swift Package dependency resolution in Xcode.
- Run Gradle dependency verification and license reporting.
- Run `npm audit` or an equivalent security scan for Functions dependencies.
- Review Facebook and Firebase SDK license obligations before release.
