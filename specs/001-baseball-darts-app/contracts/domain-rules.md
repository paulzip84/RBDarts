# Contract: Domain Rules

**Feature**: 001-baseball-darts-app  
**Purpose**: Define deterministic scoring and league rules shared by iOS,
Android, and trusted backend validation.

## Score Validation

- Each player inning score MUST be an integer from 0 through 9.
- Invalid scores MUST be rejected without changing the last valid score.
- The inning target MUST equal the inning number, including extra innings.

## Regulation and Extra Innings

- A regulation game has 9 innings by default.
- If tied after regulation and extra innings are enabled, play inning 10 and
  continue until the tie is broken.
- If extra innings are disabled, the league tie rule determines the result.

## Totals

```text
Player Game Total = sum(player inning scores)
Team Raw Game Total = sum(active player game totals)
Margin = leading score - trailing score
```

## Handicap

```text
Team Average Sum = sum(active lineup player averages)
Handicap = (Higher Team Average Sum - Lower Team Average Sum) * Handicap Percent
Adjusted Game Total = Raw Game Total + Handicap
```

Rules:

- The lower-average team receives handicap.
- Equal team average sums produce no handicap.
- Handicap is calculated per game because lineups can change.
- Player averages used for all games in a match are captured at match start.
- Default rounding is nearest whole number.
- League settings may choose nearest, floor, ceiling, or decimal.

## Winners

- Standalone games use raw total by default.
- League games use adjusted total by default.
- A tied game continues to extra innings if enabled.

## Match Points

- Each game win awards the configured game win points.
- The team with the highest total adjusted score across the match receives the
  configured match total-score bonus.
- If total adjusted match score is tied, default behavior is no bonus.
- League settings may choose split bonus, tiebreaker game, or raw-score tiebreak.

## Locking

- Game N locks when Game N+1 starts.
- All games lock when the match finalizes.
- Locked games remain visible.
- Locked games can change only through authorized correction workflows.

## Corrections

Every applied correction MUST:

- Record editor, role, timestamp, reason, previous value, new value, and affected
  records.
- Recalculate player totals, team raw totals, adjusted totals, winners, match
  points, standings, stats, summaries, and analytics.
- Preserve an immutable audit record.

## Official Averages

```text
Player Average = Official Raw Score Total / Official Games Played
```

Rules:

- Practice scores do not affect official averages by default.
- Averages update after match finalization, not after each game.
- New players use a League Manager-provided seed average or league default before
  handicap participation.

## Standings

Default ranking order:

1. League points
2. Games won
3. Point differential
4. Head-to-head record
5. Total adjusted score
6. Total raw score

## Projections

- MVP projections use simple pace and average-based estimates.
- Projections MUST be labeled as estimates.
- Projections MUST NOT block score entry, official scoring, locking, or
  finalization.

## Fixture Requirements

Shared fixtures MUST cover:

- Valid and invalid score entry.
- Standalone raw winner.
- League adjusted winner.
- Extra innings.
- Equal averages and no handicap.
- Handicap rounding variants.
- Match bonus tie default.
- Game locking.
- Locked-game correction with downstream recalculation.
- Practice isolation from official stats.
