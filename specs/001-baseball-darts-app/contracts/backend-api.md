# Contract: Backend API and Trusted Operations

**Feature**: 001-baseball-darts-app  
**Purpose**: Define backend operations that require trusted execution beyond
direct mobile client writes.

## General Rules

- All operations require an authenticated user unless explicitly documented as
  public read.
- Role checks are evaluated from league-scoped role assignments.
- Mutations that affect official records are idempotent by request id.
- Official calculations are repeated server-side before records are finalized.
- Error responses MUST be safe to show to users and MUST NOT expose secrets,
  tokens, internal stack traces, or private data from other leagues.

## Operation: Create League

**Caller**: Authenticated user

**Input**:

- league name
- league configuration
- initial manager assignment

**Authorization**:

- Any authenticated user may create a league.
- Creator becomes League Manager.

**Effects**:

- Create league record.
- Create creator role assignment.
- Initialize empty standings.

## Operation: Create Match

**Caller**: League Manager or permitted Team Manager

**Input**:

- league id
- match date
- home team id
- away team id
- games per match override if allowed

**Authorization**:

- User must have create-match permission in the league.

**Effects**:

- Create scheduled match.
- Create expected game shells if useful for client display.

## Operation: Start Game

**Caller**: League Manager, Team Manager, or Scorekeeper with match permission

**Input**:

- match id
- game number
- active lineups for both teams

**Authorization**:

- User must have scorekeeping permission for the match.

**Validation**:

- Game lineups must match league player-count rules.
- Players must be active roster members or league-permitted substitutes.
- Player average snapshots must be captured before scoring starts.

**Effects**:

- Move game to scoring.
- Lock prior game if this is Game N+1.
- Calculate handicap for the game.

## Operation: Submit Inning Scores

**Caller**: Authorized scorekeeper, Team Manager, or League Manager

**Input**:

- game id
- inning number
- player scores
- client request id

**Authorization**:

- User must have score entry permission.
- Game must be unlocked.

**Validation**:

- Each score is an integer 0 through 9.
- Players must be active in the game lineup.
- Request is rejected if game is locked, finalized, or voided.

**Effects**:

- Persist inning scores.
- Recalculate live totals and derived player/team game state.

## Operation: Complete Game

**Caller**: Authorized scorekeeper, Team Manager, or League Manager

**Input**:

- game id

**Authorization**:

- User must have game completion permission.

**Validation**:

- Required innings are complete.
- Extra innings are resolved or tie rule is applied.

**Effects**:

- Mark game complete.
- Store derived totals, winner, margin, zero counts, and 9-point counts.

## Operation: Finalize Match

**Caller**: League Manager or permitted Scorekeeper

**Input**:

- match id

**Authorization**:

- User must have match finalization permission.

**Validation**:

- All required games are complete.
- No unresolved score conflicts exist.

**Effects**:

- Lock all games.
- Award game points and match bonus.
- Update standings.
- Update player averages from raw official scores.
- Generate match and player-night summaries.

## Operation: Request Correction

**Caller**: Team Manager, Scorekeeper, League Manager

**Input**:

- game id
- inning score id or affected field
- proposed value
- reason

**Authorization**:

- User must have correction request permission.

**Validation**:

- Target record must be locked or completed.
- Reason is required.
- Proposed score values must be 0 through 9.

**Effects**:

- Create correction audit record in requested status.
- Notify or surface request to League Manager.

## Operation: Apply Correction

**Caller**: League Manager or explicitly permitted admin role

**Input**:

- correction id
- approval decision
- final value if adjusted

**Authorization**:

- User must have correction approval permission.

**Validation**:

- Correction target must still exist.
- Applied score values must be valid.

**Effects**:

- Update target record.
- Mark audit record applied.
- Recalculate all affected official totals, standings, stats, summaries, and
  analytics.

## Operation: Export League Data

**Caller**: League Manager or app administrator with support authority

**Input**:

- league id
- export scope
- date range

**Authorization**:

- User must have export permission.

**Effects**:

- Generate export with explicit scope.
- Record export audit event.

## Error Categories

- `unauthenticated`: user must sign in
- `permission_denied`: user lacks role or permission
- `validation_failed`: request violates score, lineup, or league rules
- `conflict`: record changed or locked before request completed
- `not_found`: requested record is unavailable to this user
- `retryable_unavailable`: backend/network temporarily unavailable
- `internal_safe`: unexpected failure with safe user-facing message
