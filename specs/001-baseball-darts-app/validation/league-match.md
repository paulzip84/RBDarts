# League Match Validation

## Implemented Coverage

- League configuration service clamps invalid game, lineup, and handicap values.
- Handicap calculation snapshots lineup average sums and applies the configured
  rounding mode.
- Prior-game locking is represented by shared `lockableGameIds` rules.
- Standings ranking follows league points, games won, point differential,
  adjusted score, raw score, and team id fallback.
- Firestore rules restrict official aggregate writes and backend-only
  finalization fields.

## Remaining Emulator/Device Validation

- Seed Firebase emulator data for League Manager, Team Manager, Scorekeeper,
  Player, Viewer, and cross-league users.
- Run full createLeague, createMatch, startGame, submitInningScores,
  completeGame, and finalizeMatch callables against the emulator.
- Confirm Game N locks when Game N+1 starts in both native clients.
