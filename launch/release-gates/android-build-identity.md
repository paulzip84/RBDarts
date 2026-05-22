# Android Build Identity Checklist

Status: Draft evidence, pending signed release artifact.

## Required Identity

- Application id: `com.rbdarts`
- App name: `RBDarts`
- Version name: `0.1.0`
- Version code: `1`
- Configuration profile: `production`
- Signing: Play App Signing or release keystore referenced through local/CI properties
- Firebase project: `rbdarts-production`
- SSO providers: Google and Facebook production clients

## Gate Checks

- Release build uses the `release` build type.
- Signing values are loaded from Gradle properties and no key material is committed.
- Gradle wrapper is checked in at `android/gradlew` and uses Gradle 8.13.
- Android Gradle plugin is pinned to `8.13.2` for compile SDK 36 support.
- App Check enforcement is enabled for production Firebase services.
- Privacy/support/account deletion links render in the release candidate.
- Target SDK and app content declarations match Google Play policy requirements.

## Evidence To Attach

- AAB or APK artifact reference
- Play Console internal testing build number
- Smoke test device and Android version
- Sign-in and scoring recovery notes
