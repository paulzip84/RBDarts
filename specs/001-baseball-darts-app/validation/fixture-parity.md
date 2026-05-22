# Fixture Parity Review

## Fixture Groups

- `shared-contracts/fixtures/scoring/standalone-basic.json`
- `shared-contracts/fixtures/scoring/extra-innings.json`
- `shared-contracts/fixtures/handicap/handicap-rounding.json`
- `shared-contracts/fixtures/locking/lock-prior-game.json`
- `shared-contracts/fixtures/corrections/apply-correction.json`
- `shared-contracts/fixtures/standings/standings-ranking.json`
- `shared-contracts/fixtures/practice/practice-isolation.json`

## Current Result

- Swift scoring, handicap, correction, practice, standings, and projection logic
  type-checks locally.
- Firebase tests pass for scoring, standalone game, league match, corrections,
  practice, stats, and rule expectation fixtures.
- Android fixture tests are present but require Gradle/Kotlin tooling to run.

## Parity Rule

Any future rule change must update the shared fixture first, then update iOS,
Android, and backend tests in the same change.
