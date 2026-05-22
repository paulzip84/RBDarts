# Locked Correction Validation

## Implemented Coverage

- Correction services require a non-empty reason.
- Applied corrections validate score values from 0 through 9.
- Audit records capture editor id, role, previous value, new value, reason,
  timestamp, affected records, and status.
- Firestore rules prevent deletion of correction audit documents and restrict
  apply/update transitions to League Manager/Admin roles.
- Recalculation helpers identify affected game, player, match, stats,
  standings, and analytics records.

## Remaining Emulator/Device Validation

- Apply a correction to a finalized match in the Firebase emulator.
- Verify downstream stats and standings regenerate from corrected score records.
- Confirm Team Manager can request but cannot apply corrections by default.
