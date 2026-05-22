# Implementation Plan: Baseball Darts Mobile App

**Branch**: `001-baseball-darts-app` | **Date**: 2026-05-22 | **Spec**: [spec.md](./spec.md)

**Input**: Feature specification from `/specs/001-baseball-darts-app/spec.md`

## Summary

Build RBDarts as two native mobile clients, one iOS app in Swift/SwiftUI and one
Android app in Kotlin/Jetpack Compose, backed by a shared Firebase platform for
trusted SSO, cloud persistence, authorization, auditability, and cross-device
league sync. Core Baseball Darts scoring rules live in platform-native domain
modules with shared contract fixtures so iOS and Android remain native while
producing identical score, handicap, locking, correction, standings, and summary
results.

The first delivery should focus on a secure scoring foundation: standalone
games, league core, locked-game correction auditability, stats, and practice mode.
Predictive insights start with simple pace and average-based projections and
must never block score entry or official scoring workflows.

## Technical Context

**Language/Version**: Swift 6 language mode for iOS; Kotlin 2.x for Android

**Primary Dependencies**: SwiftUI, Observation, Security/Keychain,
AuthenticationServices, Firebase Auth, Cloud Firestore, Cloud Functions,
Firebase App Check, Firebase Crashlytics, Firebase Performance Monitoring,
Jetpack Compose, AndroidX Lifecycle/ViewModel, Kotlin Coroutines/Flow, Hilt,
Room, DataStore, Android Credential Manager, Facebook Login SDK

**Storage**: Cloud Firestore for league and synced official records; platform
secure storage for tokens/session metadata; Room and Firestore cache on Android;
SwiftData or lightweight local persistence plus Firestore cache on iOS for active
score recovery; DataStore/UserDefaults-equivalent settings only for non-sensitive
preferences

**Testing**: XCTest and XCUITest for iOS; JUnit, Compose UI tests, and Android
instrumentation tests for Android; shared JSON contract fixtures for scoring,
handicap, locking, correction, standings, and summaries; Firebase emulator tests
for rules/functions; performance tests for score entry latency and recovery

**Target Platform**: iOS 17+ and current iPadOS for tablet scorecard views;
Android API 29+ phones/tablets, with adaptive layouts for large screens and
foldables

**Project Type**: Native mobile app with iOS and Android clients plus Firebase
backend services

**Performance Goals**: Score entry recalculates visible totals within 1 second;
active score session recovers after restart/network loss without silent data
loss; game lock/finalize operations complete atomically from the user's
perspective; app remains responsive through a full multi-game league match

**Constraints**: Security-first; no first-party passwords; SSO through trusted
providers; role-based authorization; audit trail for locked records; native
platform architecture; privacy-safe diagnostics; official calculations must be
deterministic and repeatable from saved score records

**Scale/Scope**: MVP supports standalone games, practice sessions, league setup,
two-team multi-game matches, configurable handicaps, roles, locked-game
corrections, standings, player/team stats, summaries, and baseline predictions
for recreational leagues

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Security and Privacy Gate

- PASS: The plan uses trusted SSO with no first-party password collection.
- PASS: Firebase Auth, Security Rules, App Check, and Cloud Functions create
  explicit authentication, authorization, validation, and trusted-write
  boundaries.
- PASS: Tokens and session material stay in platform secure storage; logs,
  analytics, crash reports, and exports must exclude secrets and unnecessary
  personal data.

### Native Platform Architecture Gate

- PASS: iOS uses SwiftUI, Observation, Swift concurrency, Keychain, and
  platform-native app lifecycle patterns.
- PASS: Android uses Kotlin, Jetpack Compose, ViewModel, Flow, Hilt, Room, and
  DataStore per Android architecture guidance.
- PASS: Shared behavior is captured in contracts and fixtures; production UI and
  app architecture remain platform native.

### Trusted SSO Identity Gate

- PASS: Authentication is planned around Firebase Auth with Google, Facebook,
  and Apple providers. Apple support is included for iOS policy and user trust.
- PASS: Sign-in, sign-out, token refresh, provider cancellation, account linking,
  and session expiration are explicit planning concerns.
- PASS: No direct password collection is introduced.

### Performance and Stability Gate

- PASS: Score entry latency, recovery, offline/degraded behavior, deterministic
  recalculation, and non-blocking analytics are defined as plan-level targets.
- PASS: Core score state uses a local active-session store before/alongside sync
  so scoring survives app lifecycle interruptions.

### Test and Observability Gate

- PASS: Required test layers are defined for domain logic, UI journeys,
  Firebase rules/functions, contract fixtures, auth/session flows, and
  performance.
- PASS: Crash, error, and performance diagnostics are privacy-safe by design and
  reviewed as release gates.

### Post-Design Recheck

- PASS: Research, data model, contracts, and quickstart preserve all gates. No
  constitution exceptions are required.

## Project Structure

### Documentation (this feature)

```text
specs/001-baseball-darts-app/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   ├── backend-api.md
│   ├── domain-rules.md
│   └── security-rules.md
└── checklists/
    └── requirements.md
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
│   │   ├── League/
│   │   ├── MatchScoring/
│   │   ├── Corrections/
│   │   ├── Stats/
│   │   └── Settings/
│   ├── Shared/
│   │   ├── Domain/
│   │   ├── Data/
│   │   ├── Security/
│   │   ├── Observability/
│   │   └── DesignSystem/
│   └── Resources/
├── RBDartsTests/
└── RBDartsUITests/

android/
├── app/
│   └── src/
│       ├── main/
│       │   └── java/com/rbdarts/
│       │       ├── app/
│       │       ├── feature/
│       │       │   ├── auth/
│       │       │   ├── standalonegame/
│       │       │   ├── practice/
│       │       │   ├── league/
│       │       │   ├── matchscoring/
│       │       │   ├── corrections/
│       │       │   ├── stats/
│       │       │   └── settings/
│       │       └── core/
│       │           ├── domain/
│       │           ├── data/
│       │           ├── security/
│       │           ├── observability/
│       │           └── designsystem/
│       ├── test/
│       └── androidTest/
└── build-logic/

firebase/
├── functions/
├── firestore.rules
├── firestore.indexes.json
└── appcheck/

shared-contracts/
├── fixtures/
│   ├── scoring/
│   ├── handicap/
│   ├── locking/
│   ├── corrections/
│   └── standings/
└── schemas/
```

**Structure Decision**: Use separate native iOS and Android apps with matching
feature boundaries, a Firebase backend directory for trusted backend logic and
security rules, and shared contract fixtures for cross-platform parity. Do not
introduce Kotlin Multiplatform, React Native, Flutter, or a shared production
domain library in the initial implementation because the constitution prioritizes
native platform architecture.

## Phase 0: Research Summary

See [research.md](./research.md) for full decisions and alternatives.

Key decisions:

- Native iOS: SwiftUI with data-driven state and Swift concurrency.
- Native Android: Jetpack Compose with layered architecture, UDF, ViewModel,
  Flow, Hilt, Room, and DataStore.
- Identity: Firebase Auth as the common identity broker with Google, Facebook,
  and Apple providers; no direct password auth.
- Backend: Cloud Firestore for synced records, Security Rules/App Check for
  client access control, Cloud Functions for trusted official finalization,
  correction, standings, and export operations.
- Offline/recovery: Firestore offline cache plus explicit active-score session
  persistence before each sync-sensitive transition.

## Phase 1: Design Summary

See [data-model.md](./data-model.md) and [contracts](./contracts/) for model and
interface details.

Design outputs:

- Domain model covers users, roles, players, leagues, teams, rosters, matches,
  games, lineups, inning scores, practice attempts, stats, summaries, insights,
  and correction audit records.
- Backend API contract defines trusted operations for league creation, match
  creation, score submission, game start/lock, match finalization, corrections,
  recalculation, and export.
- Domain rules contract defines scoring, extra innings, handicap, match points,
  averages, standings, and projection behavior.
- Security rules contract defines access-control expectations by collection and
  role.

## Complexity Tracking

No constitution violations.

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |
