# Implementation Plan: Mobile App Store Launch Readiness

**Branch**: `002-launch-mobile-apps` | **Date**: 2026-05-22 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from `/specs/002-launch-mobile-apps/spec.md`

## Summary

Prepare the native RBDarts iOS and Android apps for Apple App Store and Google
Play launch by turning the current Speckit implementation scaffold into
release-candidate apps with production identity, signing, branding, store
metadata, privacy/data-safety disclosures, beta validation, release gates,
submission tracking, and first-release monitoring. The technical approach keeps
the native Swift and Kotlin app boundaries from the existing plan, adds
launch-readiness artifacts under the feature spec, and introduces platform
release configuration, smoke tests, compliance evidence, and operational runbook
documents without expanding gameplay scope.

## Technical Context

**Language/Version**: Swift 6 language mode for iOS; Kotlin 2.x for Android;
TypeScript 5.x for Firebase Functions launch checks and backend smoke tests

**Primary Dependencies**: Existing native platform stack from the RBDarts MVP:
SwiftUI, Observation, Security/Keychain, AuthenticationServices, Firebase Auth,
Cloud Firestore, Cloud Functions, Firebase App Check, Firebase Crashlytics,
Firebase Performance Monitoring, Jetpack Compose, AndroidX Lifecycle/ViewModel,
Kotlin Coroutines/Flow, Hilt, Room, DataStore, Android Credential Manager,
Google/Facebook/Apple SSO integrations, and the platform store consoles

**Storage**: Production Firebase project for synced official records and auth;
platform secure storage for session metadata; local active score recovery stores;
repository-only launch evidence files for non-secret checklists, store answers,
test results, release notes, and runbooks; no private keys, signing material,
real Firebase configs, or store credentials stored in git

**Testing**: XCTest and XCUITest for iOS smoke/regression tests; JUnit and
Compose UI tests for Android smoke/regression tests; Firebase emulator tests for
rules/functions; shared JSON fixture parity tests; manual device matrix checks;
TestFlight/internal testing and Google Play internal/closed testing; privacy,
accessibility, recovery, and launch monitoring validation

**Target Platform**: iOS 17+ and current iPadOS devices supported by the MVP;
Android API 29+ phones/tablets with target API level kept current for Google
Play submission requirements; first public release targets English-language
users in the United States

**Project Type**: Native mobile app with iOS and Android clients plus Firebase
backend services and store-launch evidence artifacts

**Performance Goals**: Cold launch to usable home screen within 3 seconds on
representative supported devices; score entry reflected within 1 second; active
score recovery after restart/network interruption without silent data loss; beta
release candidate reaches at least 99.5% crash-free sessions or records an
owner-approved alternate evidence threshold if beta volume is too small; no
critical startup, auth, score-save, or data-loss defect before submission

**Constraints**: Security-first launch; no first-party passwords; SSO reviewer
access must be safe and revocable; privacy disclosures must match app behavior;
store screenshots and metadata must match the submitted build; production
configuration must not expose dev endpoints or placeholder secrets; account
deletion/privacy support paths must be visible; store requirements must be
re-checked before final submission

**Scale/Scope**: Launch-readiness hardening for the existing RBDarts MVP scope
only: standalone scoring, auth, recovery, practice, summaries, privacy/support
entry points, and launch operations. Does not add paid goods, subscriptions,
ads, gambling positioning, additional gameplay modes, new locales, or a legal
review workflow unless requested separately.

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Security and Privacy Gate

- PASS: Plan requires data inventory, privacy/data-safety disclosures, reviewer
  access controls, account deletion/support paths, secret scanning, and a public
  privacy policy before submission.
- PASS: Real signing keys, store credentials, private Firebase configs,
  reviewer credentials, and secrets remain outside the repository.
- PASS: Crash/performance diagnostics are launch gates and must be reviewed for
  sensitive data before beta or public release.

### Native Platform Architecture Gate

- PASS: iOS launch work stays in the native Swift app and iOS release pipeline.
- PASS: Android launch work stays in the native Kotlin app and Android release
  pipeline.
- PASS: Shared launch behavior is expressed as contracts, evidence, checklists,
  and tests rather than shared production UI/domain code.

### Trusted SSO Identity Gate

- PASS: Launch readiness does not introduce first-party passwords.
- PASS: Reviewer and beta access are explicitly safe, revocable, and documented.
- PASS: Sign-in, sign-out, provider failure, cancellation, session expiration,
  and account deletion/request paths are launch gates.

### Performance and Stability Gate

- PASS: Plan defines launch, score entry, recovery, crash-free, startup,
  backend, and support monitoring targets.
- PASS: Store submission is blocked by critical crash, auth, score-save,
  configuration, privacy, reviewer-access, or data-loss issues.

### Test and Observability Gate

- PASS: Plan includes iOS, Android, Firebase, shared fixture, beta, manual
  device, accessibility, privacy, recovery, and launch monitoring validation.
- PASS: Observability must remain privacy-safe and exclude secrets, tokens,
  correction reasons, private reviewer credentials, and unnecessary personal
  data.

## Project Structure

### Documentation (this feature)

```text
specs/002-launch-mobile-apps/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   ├── launch-gates.md
│   ├── store-submission.md
│   └── privacy-disclosure.md
├── checklists/
│   └── requirements.md
└── tasks.md
```

### Source Code (repository root)

```text
ios/
├── RBDarts/
│   ├── App/
│   ├── Features/
│   │   ├── Auth/
│   │   ├── StandaloneGame/
│   │   ├── Practice/
│   │   ├── Stats/
│   │   └── Settings/
│   ├── Shared/
│   └── Resources/
├── RBDartsTests/
└── RBDartsUITests/

android/
├── app/
│   └── src/
│       ├── main/
│       ├── test/
│       └── androidTest/
└── build.gradle.kts

firebase/
├── functions/
├── firestore.rules
├── firestore.indexes.json
└── firebase.json

shared-contracts/
├── fixtures/
└── schemas/

launch/
├── app-store/
│   ├── metadata/
│   ├── screenshots/
│   └── review-notes/
├── google-play/
│   ├── metadata/
│   ├── screenshots/
│   └── app-content/
├── privacy/
├── beta/
├── release-gates/
└── runbooks/
```

**Structure Decision**: Keep production app work in the existing native iOS,
Android, Firebase, and shared-contract directories. Add a top-level `launch/`
directory for non-secret launch artifacts: store metadata drafts, screenshot
checklists, privacy/data-safety answers, beta evidence, launch gate reports, and
runbooks. Store credentials, signing assets, provisioning profiles, keystores,
private Firebase configs, and reviewer passwords remain outside the repository
and are referenced only by secure-owner process notes.

## Phase 0: Research Summary

See [research.md](./research.md) for full decisions and alternatives.

Key decisions:

- Use App Store Connect and Google Play Console as the authoritative submission
  paths; keep repository artifacts as source-of-truth drafts/evidence only.
- Use TestFlight plus Google Play internal/closed testing for beta validation.
- Treat Apple App Privacy and Google Play Data Safety/App Content answers as
  release-blocking contracts that must match the shipped app behavior.
- Provide safe reviewer access through revocable test accounts or a review-safe
  demo path, never personal credentials.
- Gate launch with native smoke tests, device matrix checks, privacy review,
  accessibility checks, crash-free evidence, backend availability, and launch
  monitoring readiness.

## Phase 1: Design Summary

See [data-model.md](./data-model.md) and [contracts](./contracts/) for launch
entities and interfaces.

Design outputs:

- Data model covers launch candidates, store listing packages, privacy
  disclosures, reviewer access plans, beta cycles, release gates, launch
  readiness reports, and store review issues.
- Contracts define release gate statuses, store submission package requirements,
  and privacy disclosure review rules.
- Quickstart defines the launch-readiness flow from branch setup through beta,
  submission, release, and monitoring.

## Complexity Tracking

No constitution violations.

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |

## Post-Design Constitution Recheck

- PASS: Research and contracts preserve native Swift/Kotlin boundaries and do
  not add cross-platform production abstractions.
- PASS: Launch artifacts explicitly avoid committed secrets and private signing
  materials.
- PASS: Store privacy/data-safety, reviewer access, SSO failure, account
  deletion, beta, monitoring, and recovery gates are documented.
- PASS: No constitution exceptions are required.
