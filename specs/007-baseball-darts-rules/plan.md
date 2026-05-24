# Implementation Plan: Baseball Darts Rules

**Branch**: `007-baseball-darts-rules` | **Date**: 2026-05-24 | **Spec**: `specs/007-baseball-darts-rules/spec.md`
**Input**: Feature specification from `/specs/007-baseball-darts-rules/spec.md`

## Summary

Revamp the Baseball Darts rules implementation from simple per-inning `0..9` score entry into a rule-set-driven scoring model. The feature will support standard nine-inning Baseball Darts, dart-level scoring, individual and team games, equal-turn extra innings, configurable post-20 tiebreakers, official score corrections, lock behavior, role-aware league actions, live scoreboard data, and analytics derived from recorded throws.

The implementation should preserve the current native architecture: Kotlin domain/UI/state on Android, Swift domain/UI/state on iOS, and shared JSON contracts/fixtures for parity. No shared production runtime is planned.

## Technical Context

**Language/Version**: Kotlin 2.1.0 with JVM 17 target; Swift 6.0
**Primary Dependencies**: AndroidX/Jetpack Compose, Kotlin coroutines, Firebase libraries already configured, XCTest/SwiftUI on iOS
**Storage**: Existing repository/local score session storage; official league records must remain compatible with future authenticated persistence and correction audit records
**Testing**: Android JUnit/unit tests and Compose/androidTest flows; iOS XCTest; shared JSON fixtures under `shared-contracts/`
**Target Platform**: Android minSdk 29, targetSdk/compileSdk 36; iOS deployment target 17.0
**Project Type**: Native mobile apps with mirrored domain rules and shared contracts
**Performance Goals**: Score entry feedback and live totals in <= 250 ms for an active game; full single-game recalculation in <= 250 ms for up to 20 innings, two teams, and 8 players per team; no main-thread blocking for persistence or fixture parsing
**Constraints**: Security first; no secrets or tokens in diagnostics; correction/audit behavior must be role-gated; app restart must not lose entered scores; locked games must not be modified without authorized correction workflow
**Scale/Scope**: Affects scoring domain, standalone scoring, league scoring, corrections, scoreboards, stats, shared contracts, and mirrored Swift/Kotlin tests. Broad UI redesign is out of scope except where score entry/scoreboard screens need controls for new rules.

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

**Security and Privacy First**: PASS. The plan classifies scoring, correction, role, and audit data. Official corrections require authenticated users, role checks, immutable audit fields, and privacy-safe diagnostics.

**Native Platform Architecture**: PASS. Android remains Kotlin/Compose/domain services with ViewModels and repositories. iOS remains Swift/SwiftUI/domain services with XCTest. Cross-platform parity is handled with contracts and fixtures rather than a shared runtime.

**Trusted SSO Identity**: PASS. This feature does not add new SSO providers, but league corrections, locking, and role actions depend on the existing authenticated session and role assignments. No first-party password handling is introduced.

**Performance and Stability Budgets**: PASS. Score calculation and live scoreboard targets are defined above. Persistence and recalculation must tolerate app termination, restart, and interrupted scoring flows without data loss.

**Testable Quality and Privacy-Safe Observability**: PASS. Scoring-critical and authorization-sensitive behavior requires Kotlin tests, Swift tests, shared fixture coverage, and privacy-safe diagnostic events for rejected corrections, lock attempts, invalid scores, and recovery.

## Project Structure

### Documentation

```text
specs/007-baseball-darts-rules/
|-- plan.md
|-- research.md
|-- data-model.md
|-- quickstart.md
|-- contracts/
|   |-- baseball-darts-rules-contract.md
|   |-- scoring-audit-authorization-contract.md
|   `-- scoreboard-analytics-contract.md
`-- checklists/
    `-- requirements.md
```

### Source Code

```text
android/app/src/main/java/com/rbdarts/
|-- core/domain/
|   |-- Models.kt
|   `-- ScoringRules.kt
|-- feature/scoring/
|-- feature/standalonegame/
|-- feature/matchscoring/
|-- feature/corrections/
`-- feature/stats/

android/app/src/test/java/com/rbdarts/
android/app/src/androidTest/java/com/rbdarts/

ios/RBDarts/
|-- Shared/Domain/
|   |-- Models.swift
|   `-- ScoringRules.swift
|-- Features/StandaloneGame/
|-- Features/MatchScoring/
|-- Features/Corrections/
`-- Features/Stats/

ios/RBDartsTests/
shared-contracts/
|-- schemas/
`-- fixtures/
```

**Structure Decision**: Keep domain rule logic in existing platform-native domain files, split only when the implementation grows beyond the current file's readability. Add shared fixture/schema files for parity rather than a shared production module.

## Phase 0: Research

Research output: `research.md`

Resolved questions:

1. Use dart-level scoring records as the source of truth, with inning totals derived from throws.
2. Introduce a first-class Baseball Darts rule set with standard defaults and optional rule switches.
3. Implement target resolution for innings 1 through 20 and configurable behavior after inning 20.
4. Enforce equal-turn extra-inning completion before declaring a winner.
5. Model corrections, locks, substitutions, and role permissions as official league lifecycle rules.
6. Maintain Swift/Kotlin parity with shared contracts and JSON fixtures.
7. Treat analytics as derived, labeled values with clear minimum data requirements.

## Phase 1: Design And Contracts

Design output:

- `data-model.md`: Entities, relationships, validation, and lifecycle states.
- `contracts/baseball-darts-rules-contract.md`: Pure scoring and game-state behavior.
- `contracts/scoring-audit-authorization-contract.md`: Official correction, lock, role, and audit behavior.
- `contracts/scoreboard-analytics-contract.md`: Live scoreboard and analytics display contract.
- `quickstart.md`: Verification commands and manual smoke scenarios for the feature.

Post-design constitution check: PASS. The design artifacts include security/data classification, role authorization, native implementation boundaries, performance validation, tests, and diagnostics expectations.

## Phase 2: Task Planning Approach

`$speckit-tasks` should generate tasks in story priority order:

1. Foundational contracts, schemas, parity fixtures, and data model extensions.
2. P1 standard game scoring: dart-level score entry, target validation, inning totals, winner/margin.
3. P1 individual and team play: team score aggregation, batting order, player/team totals.
4. P2 extra innings: target resolver, equal-turn completion, post-20 configuration.
5. P3 league governance: substitutions, corrections, locks, role permissions, audit records.
6. P4 scoreboard and analytics: live display model, projections, consistency metrics, diagnostics.
7. Cross-platform verification: Android unit/androidTest, iOS XCTest, shared contract fixture validation, quickstart smoke testing.

Tasks must mark tests for scoring-critical and authorization-sensitive behavior as required, not optional.

## Complexity Tracking

No constitution violations or complexity exceptions are expected. If implementation requires new dependencies for persistence, authentication, analytics, or cryptography, the task list must add an explicit security/dependency review before adoption.
