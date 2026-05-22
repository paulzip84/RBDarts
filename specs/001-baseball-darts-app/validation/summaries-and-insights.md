# Summaries And Insights Validation

## Implemented Coverage

- Game summaries include winners, margin, innings played, zeros, and 9-counts.
- Standings snapshots use deterministic shared ranking rules.
- Projection services label baseline predictions as estimates.
- Projection logic is read-only and does not block score entry, locking,
  finalization, or correction flows.

## Remaining Emulator/Device Validation

- Generate summaries after a full finalized match in the Firebase emulator.
- Confirm role-restricted stats visibility for each league role.
- Review stale/offline summary behavior after network interruption.
