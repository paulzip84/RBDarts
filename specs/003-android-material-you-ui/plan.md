# Implementation Plan: Android Material You App Experience

**Branch**: `003-android-material-you-ui` | **Date**: 2026-05-22 | **Spec**: [spec.md](spec.md)

**Input**: Feature specification from `/specs/003-android-material-you-ui/spec.md`

## Summary

Redesign the Kotlin Android app into a Material You experience with a branded loading screen using the provided RBDarts image, Google/Facebook SSO login, protected adaptive navigation, and top-level sections for game type, player creation, season creation, individual player handicap management, robust scoring, stats/summary, and settings/privacy. The implementation will keep existing scoring/domain behavior intact while introducing a Compose Material 3 app shell, startup/auth routing, UI state holders, Android-compatible image asset handling, and UI/accessibility/performance verification.

## Technical Context

**Language/Version**: Kotlin 2.1.0, Jetpack Compose, Android Gradle Plugin 8.13.2, Gradle 8.13

**Primary Dependencies**: AndroidX Activity Compose, Compose Material 3, Lifecycle ViewModel Compose, Hilt, Room, DataStore, Firebase Auth/Firestore/Functions/Crashlytics/Performance, Google Identity/Credentials, Facebook Login

**Storage**: Existing local Room active scoring recovery, DataStore/preferences where needed for lightweight UI/session flags, Firebase-backed data for authenticated entities where already supported

**Testing**: JUnit unit tests, Compose UI tests under `androidTest`, Gradle lint, launch/auth/navigation smoke tests, accessibility checks, scoring regression tests

**Target Platform**: Android app, min SDK 29, target/compile SDK 36, compact phones first with adaptive support for larger screens

**Project Type**: Native mobile app with Android UI focus

**Performance Goals**: Branded loading visible within 1 second; auth routing within 2 seconds when local session state is available; score entry/totals update within 250 ms; no blank startup screen; no score data loss on background/resume

**Constraints**: Security-first SSO only; no committed secrets; no first-party passwords; privacy-safe diagnostics; offline/retry states; accessible Material You UI; preserve existing domain scoring behavior

**Scale/Scope**: Android app shell plus seven top-level sections; one locale for MVP; existing app data/domain services reused where possible

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Security and Privacy Gate

- Threat model impacts: loading asset safety, SSO routing, route protection, player/season/handicap/scoring data exposure, diagnostics.
- Sensitive data is minimized in UI logs and provider errors; no SSO tokens, raw provider payloads, private correction reasons, or secrets in diagnostics.
- Image assets and config templates are static non-secret files; real provider credentials remain outside the repository.

### Native Platform Architecture Gate

- Android architecture follows current Kotlin/Compose app direction with feature packages, UI state holders, domain/repository boundaries, Hilt-friendly dependencies, and lifecycle-aware state.
- iOS is out of scope for this feature; parity can be specified separately.
- Shared behavior remains in existing domain rules/tests rather than cross-platform UI abstractions.

### Trusted SSO Identity Gate

- Authentication remains Google and Facebook SSO only.
- Login, cancellation, provider failure, sign-out, session expiration, and account recovery states are specified.
- No first-party password storage, validation, or reset workflow is introduced.

### Performance and Stability Gate

- Loading, routing, navigation, scoring latency, state restoration, and accessibility targets are measurable.
- Recovery behavior is specified for provider failure, network loss, app restart, session expiration, and OS lifecycle changes.

### Test and Observability Gate

- Required tests include unit, Compose UI, navigation, accessibility, auth-state, and scoring regression coverage.
- Diagnostics must be privacy-safe and exclude tokens, raw provider responses, user contact data, and secrets.

## Project Structure

### Documentation (this feature)

```text
specs/003-android-material-you-ui/
├── spec.md
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   ├── navigation-map.md
│   └── ui-state-contract.md
└── checklists/
    └── requirements.md
```

### Source Code (repository root)

```text
android/
├── app/
│   └── src/
│       ├── main/
│       │   ├── java/com/rbdarts/app/
│       │   ├── java/com/rbdarts/core/
│       │   ├── java/com/rbdarts/feature/auth/
│       │   ├── java/com/rbdarts/feature/home/
│       │   ├── java/com/rbdarts/feature/gametype/
│       │   ├── java/com/rbdarts/feature/player/
│       │   ├── java/com/rbdarts/feature/season/
│       │   ├── java/com/rbdarts/feature/handicap/
│       │   ├── java/com/rbdarts/feature/scoring/
│       │   ├── java/com/rbdarts/feature/settings/
│       │   └── res/drawable*/ or res/drawable-nodpi/
│       ├── test/
│       └── androidTest/
└── gradle/wrapper/
```

**Structure Decision**: Android-only feature work will live under the existing `android/app` module. Add feature packages only where they clarify ownership of loading/auth/app shell/game type/player/season/handicap/scoring UI. Keep reusable theme/navigation/state helpers under `core` or `app` only when used by multiple feature screens.

## Phase 0: Research

Completed in [research.md](research.md).

Key decisions:

- Use Compose Material 3 / Material You patterns with dynamic color and branded fallback theme.
- Use popular Google app examples as inspiration for dynamic color, identity, and clear navigation, not as literal layouts.
- Convert the attached SVG to an Android-compatible committed static asset.
- Build a protected authenticated app shell with adaptive navigation.
- Drive UI from explicit data models and UI state, preserving existing domain scoring behavior.

## Phase 1: Design

### Data Model

Defined in [data-model.md](data-model.md).

Primary models:

- LaunchPresentationState
- AuthUiState
- AppDestination
- GameTypeOption
- PlayerProfileDraft
- SeasonDraft
- PlayerHandicapState
- ScoringUiState

### Contracts

- [navigation-map.md](contracts/navigation-map.md)
- [ui-state-contract.md](contracts/ui-state-contract.md)

### Quickstart

Manual and automated validation flow is documented in [quickstart.md](quickstart.md).

## Phase 2: Implementation Approach

1. Add Android-compatible RBDarts loading asset and loading screen.
2. Add Material You theme refinements, dynamic color fallback, and shared app shell.
3. Add startup routing from loading to login or authenticated home.
4. Add Google/Facebook login page states and route protection.
5. Add adaptive navigation destinations for Game Type, Players, Seasons, Handicaps, Scoring, Stats/Summary, and Settings.
6. Build player, season, and handicap screens against explicit UI state and current domain models.
7. Refactor scoring screen into a robust Material You flow with validation, totals, handicap display, and recovery states.
8. Add UI tests, accessibility tests, navigation tests, and scoring regression tests.
9. Verify with Gradle unit tests, Android lint, debug APK, emulator smoke, screenshot review, and manual SSO checks where provider config is available.

## Complexity Tracking

No constitution violations identified. Android-only scope is intentional because this feature request targets the Kotlin Android UI.
