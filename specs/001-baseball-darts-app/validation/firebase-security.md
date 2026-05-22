# Firebase Security Validation

## Current Coverage

- Firestore rules default deny unknown documents.
- User documents are self-readable only.
- League documents are role-readable.
- Role self-assignment is blocked for clients.
- Official aggregate fields and stats writes are backend-only.
- Practice attempts are user-owned and excluded from official stats.
- Locked/finalized correction paths require controlled status transitions.

## Required Emulator Checks

- Unauthenticated read/write denial.
- Cross-league read denial.
- Scorekeeper cannot edit Game N after Game N+1 starts.
- Team Manager can request but cannot apply corrections.
- Client cannot write standings, summaries, or official averages.
- App Check behavior in development and production Firebase projects.
