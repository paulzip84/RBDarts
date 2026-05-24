# Performance Evidence

Status: Draft.

## Launch Performance Targets

- App opens to the scoring setup surface without blocking on network.
- Score entry remains responsive during active games.
- Recovery checks do not block user interaction longer than necessary.
- Firebase Performance monitors production network and startup signals.

## Evidence To Collect

- iOS startup and score-entry interaction observations from TestFlight.
- Android startup and score-entry interaction observations from Play testing.
- Firebase Performance dashboard review for candidate builds.
- Any low-memory Android device issues from beta device matrix.

## Result

No known performance blocker. Final pass requires beta device evidence.
