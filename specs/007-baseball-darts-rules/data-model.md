# Data Model: Baseball Darts Rules

## Data Classification

- **Public/non-sensitive**: Rule descriptions, scoring labels, target number labels, default rule set names.
- **User/profile data**: Player display names, team names, role assignments, lineup order.
- **Scoring data**: Dart results, inning totals, team totals, game outcome, analytics.
- **Security-sensitive operational data**: User IDs, role authorization outcomes, correction audit records, lock/unlock events.
- **Never log**: SSO tokens, refresh tokens, provider credentials, private keys, raw session payloads.

## Entities

### BaseballRuleSet

Represents the rule configuration applied to a game.

Fields:

- `ruleSetId`
- `name`
- `regulationInnings` default `9`
- `dartsPerTurn` default `3`
- `singleValue` default `1`
- `doubleValue` default `2`
- `tripleValue` default `3`
- `extraInningsEnabled` default `true`
- `post20TieRule`: `repeat20`, `restartAt1`, or `bullseyeTiebreaker`; default `repeat20`
- `bullseyeBonusEnabled` default `false`
- `seventhInningStretchEnabled` default `false`
- `mercyRuleEnabled` default depends on league/casual context
- `handicapEnabled` default `false`
- `correctionPolicy`
- `lockPolicy`

Validation:

- `regulationInnings` must be at least `1`.
- `dartsPerTurn` must be `3` for standard Baseball Darts unless a future custom rule explicitly opts out.
- Segment values must keep a player turn score within `0..9` for the standard rule set.

### BaseballGame

Represents a standalone or league game using a Baseball Darts ruleset.

Fields:

- `gameId`
- `matchId`
- `standaloneGameId`
- `mode`: `individual` or `team`
- `status`: `setup`, `scoring`, `complete`, `locked`, `finalized`, `corrected`, `voided`
- `ruleSetId`
- `participants`
- `turnOrder`
- `currentInning`
- `currentTarget`
- `winningParticipantId`
- `winningTeamId`
- `leadMargin`
- `inningsPlayed`
- `lockedAt`

Validation:

- Game cannot move to `complete` until all required turns are complete for the deciding inning.
- Game cannot move from `locked` to editable state without an authorized unlock/correction flow.

### Participant

Represents a player or team competing in a game.

Fields:

- `participantId`
- `participantType`: `player` or `team`
- `displayName`
- `teamId`
- `playerIds`
- `lineupOrder`
- `startingHandicap`

Validation:

- Individual games require at least two player participants.
- Team games require at least two teams.
- Team participants require at least two players when created for official team play.

### Turn

Represents one player throwing during one inning.

Fields:

- `turnId`
- `gameId`
- `inningNumber`
- `targetNumber`
- `participantId`
- `teamId`
- `playerId`
- `lineupSlot`
- `darts`
- `turnScore`
- `enteredByUserId`
- `createdAt`
- `updatedAt`

Validation:

- Turn must contain exactly three dart records for standard play before it is complete.
- `turnScore` is derived from dart records and cannot exceed `9` for standard rules.
- Player must match the game turn order and team lineup unless an authorized correction/substitution changes it.

### DartThrow

Represents one dart thrown during a turn.

Fields:

- `dartThrowId`
- `turnId`
- `dartIndex`: `1`, `2`, or `3`
- `targetNumber`
- `landedNumber`
- `ring`: `miss`, `single`, `double`, `triple`, `outerBull`, `innerBull`
- `score`
- `validity`: `valid`, `wrongNumber`, `bounceOut`, `fellOut`, `foulLine`, `outOfTurn`

Validation:

- Dart score is `0` when validity is not `valid`.
- Dart score is `0` when `landedNumber` does not match the current target, except configured bullseye tiebreaker/bonus rules.
- A valid single/double/triple on the target scores `1`, `2`, or `3`.

### TeamInningScore

Represents a team aggregate for one inning.

Fields:

- `gameId`
- `teamId`
- `inningNumber`
- `playerTurnIds`
- `teamInningTotal`
- `teamRunningTotal`

Validation:

- Values are derived from player turns and cannot be manually overwritten.
- A team inning is complete only when every required player in the lineup has completed that inning.

### ExtraInningState

Tracks tie resolution after regulation.

Fields:

- `gameId`
- `inningNumber`
- `targetNumber`
- `turnsCompletedByParticipant`
- `isEqualTurnComplete`
- `isTieBroken`
- `winnerId`

Validation:

- Winner is null until equal turns are complete.
- Innings 10 through 20 target matching numbers.
- After 20, target follows `post20TieRule`.

### Substitution

Represents a league-managed player replacement.

Fields:

- `substitutionId`
- `gameId`
- `teamId`
- `outPlayerId`
- `inPlayerId`
- `effectiveInning`
- `effectiveTurn`
- `authorizedByUserId`
- `authorizedByRole`
- `createdAt`
- `reason`

Validation:

- Active-game substitutions require league policy permission.
- Historical scores remain attached to the player who earned them.
- Team totals remain unchanged after substitution.

### CorrectionAuditEntry

Represents a controlled score or lifecycle correction.

Fields:

- `correctionId`
- `leagueId`
- `matchId`
- `gameId`
- `targetRecordId`
- `targetRecordType`
- `originalValue`
- `correctedValue`
- `editedByUserId`
- `editedByRole`
- `reason`
- `affectedRecords`
- `status`
- `createdAt`
- `appliedAt`

Validation:

- Reason is required.
- Actor and role are required for league corrections.
- Locked games require league manager or admin authorization.
- Audit records are append-only.

### ScoreboardSnapshot

Represents the data needed by live scoring screens.

Fields:

- `gameId`
- `currentInning`
- `targetNumber`
- `currentThrower`
- `playerInningScore`
- `playerTotal`
- `teamInningScore`
- `teamTotal`
- `leaderId`
- `leadMargin`
- `remainingInnings`
- `needsExtraInning`

Validation:

- Snapshot values are derived from current game state.
- Leader is null during ties.

### AnalyticsSnapshot

Represents derived statistics for players or teams.

Fields:

- `subjectId`
- `subjectType`: `player` or `team`
- `averageScorePerInning`
- `highestInningScore`
- `shutoutInnings`
- `tripleRate`
- `winPercentage`
- `projectedFinalScore`
- `comebackProbability`
- `consistency`
- `bestTargetNumbers`
- `weakestTargetNumbers`

Validation:

- Predictive values must be labeled as estimates.
- Metrics with insufficient data must return unavailable state rather than fabricated values.

## Relationships

- `BaseballGame` has one `BaseballRuleSet`.
- `BaseballGame` has many `Participant` records.
- `Participant` has many `Turn` records.
- `Turn` has exactly three `DartThrow` records for standard games.
- `TeamInningScore` derives from all player `Turn` records for a team and inning.
- `ScoreboardSnapshot` and `AnalyticsSnapshot` derive from `BaseballGame`, `Turn`, and `DartThrow`.
- `CorrectionAuditEntry` references the corrected score, throw, turn, substitution, lock, or game lifecycle record.

## State Transitions

```text
setup -> scoring -> complete -> locked -> finalized
                    |          |
                    |          -> corrected -> locked
                    -> voided
```

Rules:

- Draft/setup games can be configured but do not count in standings.
- Scoring games can accept valid turns from authorized scorekeepers.
- Complete games can be reviewed and corrected while policy allows.
- Locked games are read-only for players and team managers.
- Finalized games require league manager/admin authority for post-final corrections.
- Voided games remain visible in audit history but do not count toward standings.
