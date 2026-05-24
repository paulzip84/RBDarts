# Contract: Baseball Darts Rules Engine

## Purpose

Define platform-independent behavior that Kotlin and Swift implementations must match for Baseball Darts scoring.

## Rule Defaults

```text
regulationInnings = 9
dartsPerTurn = 3
single = 1
double = 2
triple = 3
miss = 0
extraInningsEnabled = true
post20TieRule = repeat20
bullseyeBonusEnabled = false
seventhInningStretchEnabled = false
handicapEnabled = false
```

## Required Pure Functions

### `targetForInning(inningNumber, ruleSet) -> target`

Behavior:

- Reject inning numbers less than `1`.
- Return `1..9` for regulation innings.
- Return `10..20` for extra innings 10 through 20.
- Return `20` after inning 20 when `post20TieRule = repeat20`.
- Return cycling `1..20` after inning 20 when `post20TieRule = restartAt1`.
- Return `bullseye` after inning 20 when `post20TieRule = bullseyeTiebreaker`.

### `scoreDart(dartThrow, inningTarget, ruleSet) -> score`

Behavior:

- Return `0` for bounce-out, fell-out, foul-line, wrong-number, out-of-turn, and miss results.
- Return `1` for a valid single on the inning target.
- Return `2` for a valid double on the inning target.
- Return `3` for a valid triple on the inning target.
- Return `0` for bullseye unless a configured bullseye tiebreaker/bonus applies.

### `scoreTurn(dartThrows, inningTarget, ruleSet) -> turnScore`

Behavior:

- Require exactly three darts for a complete standard turn.
- Sum `scoreDart` for each dart.
- Return a value from `0` through `9` for standard Baseball Darts.
- Reject turn totals outside the configured maximum.

### `participantTotal(turns, throughInning) -> total`

Behavior:

- Sum completed turn scores for a participant through the requested inning.
- Exclude incomplete turns from official totals.
- Preserve historical scores for substituted players.

### `teamInningTotal(teamTurns, inningNumber) -> total`

Behavior:

- Sum all completed player turns for the team in the inning.
- A team inning is complete only when every active lineup player has completed the inning.

### `summarizeGame(gameState, ruleSet) -> GameSummary`

Behavior:

- Report participant/team totals.
- Report leader and margin when one participant/team leads.
- Report no winner during ties.
- After inning 9, set `needsExtraInning = true` when scores are tied and extra innings are enabled.
- In extra innings, declare a winner only after all participants/teams have completed the same extra inning.

## Invariants

- A standard individual player score per inning is always `0..9`.
- A standard individual regulation game maximum is `81`.
- Team totals equal the sum of player scores.
- A correction changes derived totals only through the corrected source record.
- A locked game cannot be changed by direct score entry.
- Kotlin and Swift must produce the same target, score, winner, margin, and extra-inning state for the same fixture.

## Acceptance Fixtures

Shared fixtures must cover:

- Standard 9-inning individual win.
- All-miss inning scores 0.
- Maximum individual inning scores 9.
- Team inning aggregation across multiple players.
- Tie after 9 requiring extra inning.
- Equal-turn extra inning where the first team leads before the second team throws but no winner is declared yet.
- Extra inning resolved after both teams complete the inning.
- Post-20 repeat-20 target.
- Post-20 restart-at-1 target.
- Bullseye tiebreaker configured state.
