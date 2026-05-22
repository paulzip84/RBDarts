# Performance Validation

## Current Coverage

- Score calculations are synchronous pure functions over local scorecards.
- Live standalone scoring updates only the changed participant scorecard.
- Predictions are simple read-only calculations and never block official flows.
- Backend recalculation helpers are deterministic and fixture-testable.

## Required Measurements

- Score tap to visible total: target under 1 second.
- Full multi-game match scoring screen responsiveness on phone and tablet.
- Active-session recovery after restart: target no silent loss.
- Summary dashboard load time with realistic league history.
- Cloud Function finalization/correction runtime under emulator and production
  staging load.
