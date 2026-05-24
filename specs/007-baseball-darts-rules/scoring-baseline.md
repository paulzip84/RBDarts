# Scoring Baseline: Baseball Darts Rules

## Before 007

- Android and iOS represented scoring primarily as per-inning totals from `0..9`.
- `ParticipantScorecard` stored `scoresByInning`.
- `summarizeStandalone` calculated totals, winner, margin, and extra-inning need from inning totals.
- Correction audit records existed for inning-score changes.

## 007 Migration Shape

- Keep existing per-inning total APIs for compatibility with current screens and tests.
- Add dart-level Baseball Darts source records for rule-accurate scoring.
- Derive turn, inning, participant, team, scoreboard, and analytics values from source darts.
- Use shared fixtures to keep Kotlin and Swift scoring behavior aligned.

## Compatibility Notes

- Existing `0..9` inning-total validation remains.
- Existing correction audit behavior remains and now has an authorized correction wrapper for locked/finalized games.
- Default target resolution now repeats target 20 after inning 20 for standard Baseball Darts.
