# Offline And Restart Recovery Validation

## Current Coverage

- iOS active standalone sessions are written to app support storage after score
  changes and before completion.
- Android defines Room-backed persistence interfaces plus an in-memory default
  for early development.
- Repositories expose async boundaries so Firestore retry/outbox behavior can be
  introduced without changing feature services.

## Required Pre-Release Checks

- Force quit during standalone score entry and verify recovery.
- Disable network during league scoring and verify no silent score loss.
- Revoke/expire auth session and verify local score data remains recoverable.
- Confirm official records do not become partially finalized after interruption.
