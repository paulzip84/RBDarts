# Standalone Performance Validation

## Scope

Validate the MVP standalone score entry path for local recalculation,
active-session persistence, invalid score rejection, extra innings, and final
scorecard generation.

## Current Result

- Swift domain/app source type-checks locally with `swiftc -typecheck`.
- Firebase standalone/domain tests pass with Node's TypeScript strip runner.
- Score entry recalculation is in-process and deterministic; expected latency is
  well under the 1 second requirement for typical league scorecards.
- Active standalone sessions are persisted before completion through
  `FileActiveScoreSessionStore` on iOS and an `ActiveScoreSessionStore`
  abstraction on Android.

## Remaining Device Validation

- Run Xcode UI tests once full Xcode is selected.
- Run Android Compose tests once Gradle/Android Studio is available.
- Measure score-tap-to-visible-total latency on physical low-end Android and
  older supported iOS devices.
