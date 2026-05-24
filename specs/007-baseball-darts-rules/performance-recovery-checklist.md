# Performance And Recovery Checklist: Baseball Darts Rules

## Budgets

- Live score entry updates visible totals within 250 ms.
- Single-game recalculation after a correction completes within 250 ms for up to 20 innings, two teams, and 8 players per team.
- Analytics calculations avoid main-thread blocking when applied to historical records.

## Recovery Expectations

- Persist dart-level source turns rather than only derived totals.
- Restore active game ID, current inning, target number, current thrower, completed turns, and partial turn data after app backgrounding or process recreation.
- Recalculate totals, winner, standings inputs, and analytics from source turns after accepted corrections.

## Validation Log

- 2026-05-24: Added pure Kotlin/Swift scoring helpers so single-game recalculation can run in memory from source turns.
- 2026-05-24: Added Android domain test coverage for scoreboard and analytics derivation.

## Manual Follow-Up

- Exercise process recreation on Android after UI wiring uses dart-level state.
- Exercise iOS app restart after UI wiring uses dart-level state.
- Capture timing evidence on representative supported devices before launch.
