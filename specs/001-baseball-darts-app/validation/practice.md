# Practice Isolation Validation

## Implemented Coverage

- Practice attempts validate target and score separately from official games.
- Practice stats calculate attempts, average, best score, zero count, nine count,
  and recent trend.
- Firestore rules prevent practice attempt documents from carrying an
  `official` flag and deny official stats writes from clients.
- Shared fixture `practice-isolation.json` preserves unchanged official stats
  after practice entry.

## Remaining Emulator/Device Validation

- Run a signed-in user through practice entry on both apps.
- Confirm Firestore writes land only in `practiceAttempts`.
- Confirm official player averages and standings are unchanged after sync.
