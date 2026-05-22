# Contract: Security Rules and Authorization

**Feature**: 001-baseball-darts-app  
**Purpose**: Define the access-control expectations for Firestore Security Rules,
Cloud Functions, and mobile clients.

## Global Requirements

- Default deny all reads and writes.
- Authenticated users may read only records permitted by league membership, team
  membership, explicit role assignment, or public league setting.
- Clients may not write derived official totals directly.
- Locked, finalized, corrected, and audit records require trusted backend
  operations for mutation.
- App Check is required for mobile client access where supported.
- Security-rule tests must cover every role and denied path.

## Collection Access Expectations

### `/users/{userId}`

- User may read their own account profile.
- User may update safe profile fields on their own account.
- Admin-only fields and account status changes require backend/admin authority.

### `/players/{playerId}`

- League members may read players visible in their leagues.
- League Managers may create and update league player records.
- Linked users may update limited self-profile fields if league policy allows.

### `/leagues/{leagueId}`

- League members may read league configuration.
- League Managers may update league settings.
- League settings that affect scoring, handicap, locking, ranking, or correction
  policy require audit metadata.

### `/leagues/{leagueId}/roles/{roleAssignmentId}`

- League Managers may read and manage league role assignments.
- Users may read their own role assignments.
- Clients may not grant themselves roles.

### `/leagues/{leagueId}/teams/{teamId}`

- League members may read teams.
- League Managers may create, update, deactivate, or delete teams.
- Team Managers may update limited team metadata only when league policy permits.

### `/leagues/{leagueId}/matches/{matchId}`

- League members may read matches.
- Authorized managers/scorekeepers may create scheduled matches.
- Finalization fields are backend-only.

### `/leagues/{leagueId}/matches/{matchId}/games/{gameId}`

- League members may read game records.
- Authorized scorekeepers may write setup/scoring fields while game is unlocked.
- Lock, finalize, corrected, winner, and aggregate fields are backend-only.

### `/leagues/{leagueId}/matches/{matchId}/games/{gameId}/inningScores/{inningScoreId}`

- Authorized scorekeepers may create or update scores only while the game is
  unlocked.
- Scores must be integers 0 through 9.
- Locked game scores are backend-only and require a correction audit record.

### `/leagues/{leagueId}/corrections/{correctionId}`

- Authorized Team Managers, Scorekeepers, and League Managers may create
  correction requests.
- League Managers may approve or reject correction requests.
- Applied correction records are immutable except for backend status updates.

### `/leagues/{leagueId}/stats/{statsDocId}`

- League members may read stats allowed by role and league privacy settings.
- Stats are backend-derived; clients may not write official aggregate stats.

### `/practiceAttempts/{practiceAttemptId}`

- Users may create and read their own practice attempts.
- Linked players may see their own practice history.
- Practice attempts are excluded from official league stats unless a future
  setting explicitly enables official use.

## Role Matrix

| Action | League Manager | Team Manager | Scorekeeper | Player | Viewer | Admin |
|--------|----------------|--------------|-------------|--------|--------|-------|
| Create league | Yes | No | No | No | No | Support only |
| Edit league settings | Yes | No | No | No | No | Support only |
| Manage teams | Yes | Limited | No | No | No | Support only |
| Manage players | Yes | Limited | No | Self limited | No | Support only |
| Create match | Yes | If permitted | No | No | No | Support only |
| Select lineup | Yes | Own team | If permitted | No | No | Support only |
| Enter unlocked scores | Yes | Own team if permitted | Yes | If permitted | No | Support only |
| Start next game / lock prior | Yes | If permitted | If permitted | No | No | Support only |
| Finalize match | Yes | If permitted | If permitted | No | No | Support only |
| Request correction | Yes | Yes | Yes | If permitted | No | Support only |
| Apply correction | Yes | No | No | No | No | Support only |
| Export league data | Yes | No | No | No | No | Support only |

## Security Rule Test Fixtures

Tests MUST include:

- Unauthenticated access denied.
- User cannot self-assign League Manager.
- Player cannot edit locked score.
- Scorekeeper cannot edit Game N after Game N+1 starts.
- Team Manager can request correction but cannot apply it by default.
- League Manager can apply correction and backend recalculates derived records.
- Client cannot write aggregate standings or player averages directly.
- Practice score write does not alter official stats.
- User from another league cannot read private league data.
- Export operation requires export permission.
