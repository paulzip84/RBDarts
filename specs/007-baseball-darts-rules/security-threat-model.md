# Security Threat Model: Baseball Darts Rules

## Data Touched

- Player and team display names, lineup order, scoring records, dart results, game status, standings inputs, and analytics.
- Authenticated user IDs and league roles for official corrections, substitutions, locking, unlocking, and finalization.
- Correction audit fields: original value, corrected value, editor, role, timestamp, reason, affected records, and status.

## Sensitive Data

- SSO tokens, refresh tokens, ID tokens, provider payloads, signing material, and private keys must never be logged or committed.
- Authenticated account identifiers, role assignments, correction editor identity, private league records, disputes, and audit history are sensitive operational data.
- Free-form correction reasons may contain personal information and must be redacted from diagnostics.

## Trust Boundaries

- Casual local scoring can run unauthenticated only when no protected league or cloud data is accessed.
- Official league scoring crosses into authenticated, role-controlled data and must verify role permissions before mutation.
- Locked and finalized games require League Manager or Admin authority for changes.

## Threats And Mitigations

- **Unauthorized locked-game edit**: Block normal score entry for locked/finalized games and route changes through authorized correction APIs.
- **Score tampering without accountability**: Append correction audit entries with actor, role, timestamp, original value, corrected value, and affected records.
- **Invalid score injection**: Validate dart-level source records and derived turn totals before saving or recalculating.
- **Silent data loss during live scoring**: Persist source turns and restore current inning, target, thrower, and partial score state.
- **Sensitive diagnostics leakage**: Use allowlisted event names and reason codes; redact token, secret, password, email, reason, and provider identifiers.

## Implemented Privacy-Safe Events

- `score_entry_rejected`
- `correction_requested`
- `correction_rejected`
- `correction_applied`
- `game_locked`
- `game_unlock_requested`
- `substitution_applied`
- `baseball_stats_viewed`
- `baseball_projection_unavailable`
