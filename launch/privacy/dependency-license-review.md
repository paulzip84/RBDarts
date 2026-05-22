# Dependency And License Launch Review

Status: Draft.

## Review Scope

- iOS Swift Package dependencies for Firebase and Facebook Login.
- Android Gradle dependencies for Firebase, Credentials, Google ID, Facebook Login, Hilt, Room, Compose, and testing libraries.
- Firebase Functions dependencies for Firebase Admin and Functions SDK.

## Required Before Submission

- Generate dependency list from Xcode, Gradle, and npm.
- Confirm licenses are compatible with app store distribution.
- Confirm no dependency introduces prohibited tracking or unsupported SDK behavior.
- Confirm privacy disclosures match dependency behavior.

## Evidence Commands

```bash
npm --prefix firebase/functions ls --all
```

Gradle and Xcode dependency exports require local toolchains.
