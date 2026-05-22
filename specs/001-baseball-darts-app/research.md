# Research: Baseball Darts Mobile App

**Feature**: 001-baseball-darts-app  
**Date**: 2026-05-22

## Decision: Build Separate Native iOS and Android Apps

Use separate Swift/SwiftUI and Kotlin/Jetpack Compose apps with matching feature
boundaries and shared contracts, not shared production UI or domain code.

**Rationale**: The project constitution requires native platform architecture.
Apple positions SwiftUI around data-driven app structure and model state as the
source of truth. Android's architecture guidance recommends clear boundaries,
UI/data layers, optional domain layer, single source of truth, unidirectional
data flow, coroutines/flows, and dependency injection. RBDarts needs fast live
score entry, secure platform auth, and reliable lifecycle recovery, all of which
benefit from native platform behavior.

**Alternatives considered**:

- Kotlin Multiplatform: Useful for some shared data layers, but rejected for MVP
  to avoid forcing a cross-platform architecture before native behavior is stable.
- Flutter or React Native: Rejected because they conflict with the native
  Swift/Kotlin constitution.
- Shared generated clients only: Accepted for schemas, fixtures, and contracts,
  but not for production UI/domain logic in MVP.

**Sources**:

- Apple SwiftUI apps: https://developer.apple.com/documentation/technologyoverviews/swiftui
- Apple model data in SwiftUI: https://developer.apple.com/documentation/SwiftUI/Model-data
- Android app architecture: https://developer.android.com/topic/architecture

## Decision: Use Firebase Auth as the Identity Broker

Use Firebase Auth for Google, Facebook, and Apple sign-in. Do not support
first-party email/password authentication in the MVP.

**Rationale**: The constitution puts security first and requires trusted SSO.
Firebase Auth supports federated identity providers including Google, Facebook,
and Apple on iOS and Android, while handling account linking and recovery edge
cases that are security-sensitive. Android should use Credential Manager for
Google sign-in because Google identifies it as the modern unified auth surface
for passkeys, passwords, and federated sign-in. Facebook provider integration
uses Meta's official SDKs where needed.

**Alternatives considered**:

- Custom OAuth broker: Rejected for MVP because it increases token lifecycle,
  account linking, revocation, and secure storage risk.
- Email/password accounts: Rejected by constitution unless a future amendment
  approves a first-party credential system.
- Per-provider custom auth only: Rejected because it fragments account linking,
  authorization claims, and backend identity checks.

**Sources**:

- Firebase Authentication: https://firebase.google.com/docs/auth
- Android Credential Manager API: https://developers.google.com/identity/android-credential-manager
- Apple AuthenticationServices: https://developer.apple.com/documentation/AuthenticationServices
- Meta Facebook SDK for iOS: https://facebook-facebook-ios-sdk.mintlify.app/
- Meta Facebook SDK for Android: https://github.com/facebook/facebook-android-sdk

## Decision: Use Cloud Firestore Plus Cloud Functions for Synced Official Data

Use Cloud Firestore for synced league records and Cloud Functions for trusted
official operations such as finalization, locked-game correction, recalculation,
and export generation.

**Rationale**: RBDarts needs cross-device league play, roles, audit trails,
standings, and recoverable live scoring. Firestore supports offline access on
Apple and Android clients and can enforce mobile/web access through Firebase
Authentication and Security Rules. Cloud Functions provide a trusted backend
boundary for operations that clients must not be allowed to forge, such as
official finalization and correction recalculation.

**Alternatives considered**:

- Local-only database: Rejected because league play, roles, corrections, and
  cross-device standings need shared official records.
- Custom REST backend: Viable later, but rejected for MVP to reduce backend
  operational scope and accelerate secure SSO-backed development.
- Direct client-only Firestore writes for all records: Rejected for official
  locked/finalized records because clients should not be authoritative for audit
  and recalculation transitions.

**Sources**:

- Firestore offline access: https://firebase.google.com/docs/firestore/manage-data/enable-offline
- Firestore security rules: https://firebase.google.com/docs/firestore/security/get-started
- Firestore security overview: https://firebase.google.com/docs/firestore/security/overview

## Decision: Use Platform Local Persistence for Active Scoring Recovery

Persist active scoring session state locally before each sync-sensitive state
transition, then sync to Firestore. Android uses Room for structured active
session and outbox data, DataStore only for simple non-sensitive preferences.
iOS uses SwiftData or a lightweight local store for structured active session
recovery, with Keychain reserved for tokens and sensitive session metadata.

**Rationale**: Firestore's offline cache helps with synced documents, but active
score entry must survive app restarts, network loss, and OS lifecycle
interruption without silent data loss. The local active-session store gives the
UI a deterministic source for recovery, retry, and conflict handling. Android
DataStore is designed for small key-value or typed data, while Room is better
for larger relational datasets and partial updates.

**Alternatives considered**:

- Rely only on Firestore cache: Rejected because live score entry needs explicit
  recovery semantics and outbox visibility.
- Store active game in preferences: Rejected for structured scores, lineups, and
  retry state.
- Store tokens in app database: Rejected; token material belongs in platform
  secure storage.

**Sources**:

- Android Room: https://developer.android.com/training/data-storage/room
- Android DataStore: https://developer.android.com/topic/libraries/architecture/datastore
- Apple Security framework and Keychain services: https://developer.apple.com/documentation/security

## Decision: Make Calculation Logic Deterministic and Fixture-Driven

Maintain independent native calculation implementations on iOS and Android, but
validate both against shared JSON fixtures for scoring, extra innings, handicap,
locking, correction, standings, averages, summaries, and projections.

**Rationale**: Official scoring must be repeatable from saved records, and the two
native apps must agree. Shared fixtures preserve cross-platform parity without
violating native implementation boundaries.

**Alternatives considered**:

- Server-only calculation: Rejected for live scoring responsiveness and offline
  score entry. Server still validates official transitions.
- Client-only calculation: Rejected because official finalization and correction
  must be trusted and auditable.
- Shared compiled calculation library: Rejected for MVP to keep native app
  boundaries simple.

## Decision: Use Privacy-Safe Observability

Use Crashlytics and Firebase Performance Monitoring for crash, error, and
performance diagnostics, with event schemas reviewed before implementation.
Never log tokens, secrets, unredacted correction reasons, or unnecessary personal
data.

**Rationale**: The constitution requires stability and privacy-safe diagnostics.
Score entry latency, app restarts, session failures, save conflicts, and
permission denials must be observable without leaking sensitive data.

**Alternatives considered**:

- No analytics or crash reporting: Rejected because stability issues would be
  difficult to diagnose.
- Broad behavioral analytics: Rejected because it increases privacy risk and is
  not needed for MVP scoring reliability.

## Decision: Stage Delivery by Independent User Value

Implement in this order: standalone scoring foundation, league core, locked-game
corrections, stats/summaries, practice mode, baseline analytics.

**Rationale**: Standalone scoring proves validation, totals, scorecard UI, and
extra innings. League core layers roles, lineups, handicaps, locking, points, and
standings on top. Corrections and audit logs then protect official records.
Practice and analytics can use the same stable scoring and stats model.

**Alternatives considered**:

- Build all modes in parallel: Rejected due to risk in core scoring and
  authorization boundaries.
- Build league admin first: Rejected because it delays the core score entry loop
  that all later workflows depend on.
