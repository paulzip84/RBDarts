# Data Model: Baseball Darts Mobile App

**Feature**: 001-baseball-darts-app  
**Date**: 2026-05-22

## Modeling Principles

- Official league records are append-friendly and audit-friendly.
- Score calculations are derived from saved inning scores and league settings.
- Locked and finalized records are changed only through correction workflows.
- Practice records are isolated from official league stats by default.
- Role and permission checks are explicit for every league-scoped action.
- Local active scoring state can exist on device, but official records sync to
  backend storage.

## Entities

### UserAccount

Represents an authenticated app user.

**Fields**:

- `userId`: stable identity id from auth provider
- `displayName`
- `email`
- `linkedProviderIds`: Google, Facebook, Apple, or future providers
- `accountStatus`: active, disabled, deleted
- `createdAt`
- `updatedAt`

**Validation**:

- `userId` is required for any authenticated operation.
- Email may be absent or anonymized depending on provider.
- First-party password fields are not allowed.

**Relationships**:

- May link to zero or more `Player` records.
- Has many `RoleAssignment` records.

### RoleAssignment

Represents scoped permissions for a user.

**Fields**:

- `roleAssignmentId`
- `userId`
- `leagueId`
- `teamId`: optional for team-scoped roles
- `role`: LeagueManager, TeamManager, Scorekeeper, Player, Viewer, Admin
- `permissions`: explicit permission flags when league settings override defaults
- `createdAt`
- `updatedAt`

**Validation**:

- League-scoped roles require `leagueId`.
- TeamManager roles require `teamId`.
- Admin roles are app-scoped and must not imply league membership by default.

### Player

Represents a darts participant.

**Fields**:

- `playerId`
- `userId`: optional linked account
- `firstName`
- `lastName`
- `displayName`
- `activeStatus`: active, inactive
- `createdAt`
- `updatedAt`

**Validation**:

- `displayName` is required.
- A player can exist without a user account for roster setup.

### League

Represents league rules and configuration.

**Fields**:

- `leagueId`
- `leagueName`
- `gamesPerMatch`
- `playersPerTeamPerGame`
- `handicapPercent`
- `handicapRoundingRule`: nearest, floor, ceiling, decimal
- `pointsPerGameWin`
- `matchBonusPointValue`
- `averageUpdateRule`: afterMatch
- `extraInningsRule`: enabled, disabled, leagueTieRule
- `tieBonusRule`: noBonus, splitBonus, tiebreakerGame, rawScoreTiebreak
- `lockRule`: lockPriorGameOnNextStart
- `correctionPolicy`
- `rankingRule`
- `createdAt`
- `updatedAt`

**Validation**:

- `gamesPerMatch` must be at least 1.
- `playersPerTeamPerGame` must be at least 1.
- `handicapPercent` must be 0 through 100 unless a future rule allows more.
- Default `averageUpdateRule` is afterMatch.

### Team

Represents a team within a league.

**Fields**:

- `teamId`
- `leagueId`
- `teamName`
- `captainPlayerId`
- `activeStatus`
- `createdAt`
- `updatedAt`

**Relationships**:

- Belongs to one `League`.
- Has many `TeamRosterEntry` records.

### TeamRosterEntry

Represents a player's membership on a team over time.

**Fields**:

- `teamRosterId`
- `teamId`
- `playerId`
- `startDate`
- `endDate`
- `activeStatus`

**Validation**:

- A player may not have overlapping active roster entries in the same league
  unless league settings explicitly allow it.

### Match

Represents a league contest between two teams.

**Fields**:

- `matchId`
- `leagueId`
- `matchDate`
- `awayTeamId`
- `homeTeamId`
- `status`: scheduled, inProgress, finalized, corrected, voided
- `awayTeamMatchPoints`
- `homeTeamMatchPoints`
- `awayTeamAdjustedTotal`
- `homeTeamAdjustedTotal`
- `matchBonusWinnerTeamId`
- `createdAt`
- `startedAt`
- `completedAt`

**Validation**:

- Away and home teams must belong to the league and be different teams.
- A finalized match can change only through a correction workflow.

### Game

Represents one game within a match or a standalone game.

**Fields**:

- `gameId`
- `matchId`: optional for standalone games
- `standaloneGameId`: optional for non-league games
- `gameNumber`
- `status`: setup, scoring, complete, locked, finalized, corrected, voided
- `awayTeamRawScore`
- `homeTeamRawScore`
- `awayTeamHandicap`
- `homeTeamHandicap`
- `awayTeamAdjustedScore`
- `homeTeamAdjustedScore`
- `winningTeamId`
- `inningsPlayed`
- `lockedAt`
- `createdAt`
- `startedAt`
- `completedAt`

**Validation**:

- Regulation games default to 9 innings.
- Extra innings are allowed only when required by tie rules.
- Raw and adjusted totals are derived values.

### StandaloneGame

Represents a non-league game.

**Fields**:

- `standaloneGameId`
- `createdByUserId`
- `gameName`
- `gameDate`
- `participantType`: singles, teams
- `regulationInnings`
- `extraInningsEnabled`
- `status`: setup, scoring, complete
- `createdAt`
- `completedAt`

**Relationships**:

- Has one `Game` record.
- Has many `StandaloneParticipant` records.

### StandaloneParticipant

Represents a standalone player or team participant.

**Fields**:

- `participantId`
- `standaloneGameId`
- `participantType`: player, team
- `displayName`
- `playerIds`
- `sortOrder`

### GameLineup

Represents an active player in a team lineup for a game.

**Fields**:

- `gameLineupId`
- `gameId`
- `teamId`
- `playerId`
- `lineupSlot`
- `playerAverageAtStartOfMatch`

**Validation**:

- Lineup slots are unique within a team/game.
- League games require exactly the configured number of active players per team.

### InningScore

Represents one score for one player in one inning.

**Fields**:

- `inningScoreId`
- `gameId`
- `playerId`
- `teamId`: optional for standalone singles
- `inningNumber`
- `target`
- `score`
- `enteredByUserId`
- `createdAt`
- `updatedAt`

**Validation**:

- `score` must be an integer from 0 through 9.
- `target` equals `inningNumber`.
- A player can have at most one active score per inning/game.
- Updates are allowed only while the game is unlocked, except through correction.

### PlayerGameScore

Derived summary for a player's game.

**Fields**:

- `playerGameScoreId`
- `gameId`
- `playerId`
- `teamId`
- `rawScore`
- `vsAverage`
- `zeroInningCount`
- `ninePointInningCount`
- `createdAt`

**Derivation**:

- `rawScore` is the sum of `InningScore.score` for the player/game.
- `zeroInningCount` counts scores equal to 0.
- `ninePointInningCount` counts scores equal to 9.

### PracticeAttempt

Represents a non-official practice score.

**Fields**:

- `practiceAttemptId`
- `playerId`
- `userId`
- `target`
- `score`
- `attemptDate`
- `createdAt`

**Validation**:

- `score` must be 0 through 9.
- Practice attempts do not update official league stats by default.

### PlayerLeagueStats

Aggregated official league statistics for a player.

**Fields**:

- `playerId`
- `leagueId`
- `gamesPlayed`
- `totalScore`
- `average`
- `highGame`
- `lowGame`
- `zeroInningCount`
- `ninePointInningCount`
- `lastUpdatedMatchId`

**Derivation**:

- Official average is `totalScore / gamesPlayed` using raw league scores.
- Updated only after match finalization or correction recalculation.

### TeamLeagueStats

Aggregated league statistics for a team.

**Fields**:

- `teamId`
- `leagueId`
- `matchesPlayed`
- `matchesWon`
- `matchesLost`
- `gamesWon`
- `gamesLost`
- `leaguePoints`
- `rawScoreFor`
- `adjustedScoreFor`
- `adjustedScoreAgainst`
- `pointDifferential`

### CorrectionAuditRecord

Immutable audit record for locked or completed game changes.

**Fields**:

- `correctionId`
- `leagueId`
- `matchId`
- `gameId`
- `inningScoreId`
- `editedByUserId`
- `editedByRole`
- `reason`
- `previousValue`
- `newValue`
- `affectedRecords`
- `status`: requested, approved, rejected, applied
- `createdAt`
- `appliedAt`

**Validation**:

- Reason is required.
- Previous and new values are required.
- Applied corrections must trigger recalculation.

### SummarySnapshot

Represents generated game, match, standings, or player-night summary output.

**Fields**:

- `summaryId`
- `summaryType`: game, match, playerNight, standings
- `sourceRecordId`
- `generatedAt`
- `version`
- `displayData`

**Validation**:

- Summary snapshots are derived from official records and can be regenerated.

## State Transitions

### Match

```text
scheduled -> inProgress -> finalized
finalized -> corrected
finalized -> voided
corrected -> finalized
```

Rules:

- Only authorized League Managers or permitted Scorekeepers can finalize.
- Corrections create audit records and return the match to finalized after
  recalculation.

### Game

```text
setup -> scoring -> complete -> locked -> finalized
locked -> corrected -> locked
finalized -> corrected -> finalized
```

Rules:

- Game N locks when Game N+1 starts.
- A finalized match locks all games.
- Regular score edits stop when a game is locked.

### CorrectionAuditRecord

```text
requested -> approved -> applied
requested -> rejected
approved -> applied
```

Rules:

- Team Managers can request corrections by default.
- League Managers can approve/apply corrections according to league policy.
- Every applied correction triggers recalculation.

### PracticeAttempt

```text
created -> retained
created -> deleted
```

Rules:

- Practice attempts never enter official league aggregates unless a future
  league setting changes that behavior.

## Derived Calculation Rules

- Player game total: sum of player inning scores.
- Team raw game total: sum of active player game totals.
- Handicap: `(higher team average sum - lower team average sum) * handicapPercent`.
- Adjusted total: raw total plus handicap for the lower-average team.
- Standalone leader: highest raw total.
- League leader: highest adjusted total by default.
- Match points: game win points plus match total-score bonus.
- Player average: raw official league score total divided by official games
  played.
- Default ranking: league points, games won, point differential, head-to-head,
  total adjusted score, total raw score.
