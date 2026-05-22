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
- `shared-contracts/`: JSON schemas and parity fixtures for scoring rules.
- `specs/001-baseball-darts-app/`: Speckit spec, plan, tasks, contracts, and
  validation notes.

## Local Commands

Firebase function/domain tests can run with the system Node runtime:

```bash
node --test --experimental-strip-types firebase/functions/test/*.test.ts
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
  ios/RBDarts/Features/**/*.swift \
  ios/RBDarts/App/RBDartsApp.swift
```

Full iOS builds require Xcode, selected with `xcode-select`, plus resolved Swift
Package dependencies. Full Android builds require Android Studio or a local
Gradle install/wrapper.

## Security Setup Notes

- Do not commit real Firebase config files, provider secrets, signing keys,
  provisioning profiles, keystores, or `.env` files.
- Use `ios/RBDarts/Resources/FirebaseConfig.example.plist`,
  `android/app/src/main/res/values/firebase_config_example.xml`, and
  `firebase/.env.example` as templates only.
- SSO is the only planned authentication path for MVP. First-party passwords are
  intentionally out of scope.
- Firestore rules default deny and reserve official aggregate writes for trusted
  backend operations.

## Speckit Workflow

Use the installed Codex skills from this project directory:

- `$speckit-constitution`
- `$speckit-specify`
- `$speckit-plan`
- `$speckit-tasks`
- `$speckit-implement`
