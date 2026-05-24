# Contract: Scoring Audit And Authorization

## Purpose

Define official league behavior for score corrections, game locking, substitutions, role permissions, and audit records.

## Role Permission Matrix

| Action | League Manager | Admin | Team Manager | Scorekeeper | Player | Viewer |
| --- | --- | --- | --- | --- | --- | --- |
| Enter active-game scores | Yes | Yes | Yes, own team if allowed | Yes | No | No |
| Correct active-game score | Yes | Yes | Yes, own team if allowed | Yes, if policy allows | No | No |
| Correct completed unlocked game | Yes | Yes | Yes, own team if policy allows | No by default | No | No |
| Correct locked/finalized game | Yes | Yes | No | No | No | No |
| Lock game | Yes | Yes | No by default | No | No | No |
| Unlock game | Yes | Yes | No | No | No | No |
| Substitute player before game | Yes | Yes | Yes, own team | No | No | No |
| Substitute player mid-game | Yes, if policy allows | Yes | Yes, own team if policy allows | No | No | No |

## Correction Request

Required fields:

- `targetRecordId`
- `targetRecordType`
- `originalValue`
- `correctedValue`
- `editedByUserId`
- `editedByRole`
- `reason`
- `affectedRecords`
- `createdAt`

Behavior:

- Reject corrections with a blank reason.
- Reject corrections from unauthenticated users.
- Reject corrections where the actor lacks permission.
- Reject corrections that would make a dart, turn, inning, or team total invalid.
- Apply accepted corrections by updating the source scoring record and appending an audit entry.
- Recalculate affected totals, winner, standings, and analytics from source records.

## Game Locking

Behavior:

- A completed game can be locked when the next scheduled game begins or when a league manager marks it final.
- Locked games are read-only for players, viewers, scorekeepers, and team managers.
- League managers and admins can unlock or correct a locked game.
- Post-lock changes must create an audit entry with reason and affected record IDs.

## Substitution

Behavior:

- Pre-game substitutions can replace lineup players before scoring starts.
- Mid-game substitutions require league policy and authorized role.
- Scores already recorded remain attached to the original player.
- Team totals remain unchanged by the substitution event.
- Substitution events appear in audit/history views.

## Diagnostics

Allowed privacy-safe events:

- `score_entry_rejected`
- `correction_requested`
- `correction_rejected`
- `correction_applied`
- `game_locked`
- `game_unlock_requested`
- `substitution_applied`

Diagnostic payloads may include non-sensitive IDs, rule-set ID, action name, status, and reason code. Payloads must not include SSO tokens, provider credentials, email addresses, raw display names, or free-form correction reasons unless redacted.
