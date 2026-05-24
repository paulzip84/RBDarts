# Contract: Scoreboard And Analytics

## Purpose

Define live display and analytics data that scoring, summary, and stats screens must derive from Baseball Darts game state.

## Live Scoreboard Snapshot

Required fields:

- `gameId`
- `currentInning`
- `targetNumber`
- `currentThrowerId`
- `currentThrowerDisplayName`
- `playerInningScore`
- `playerTotal`
- `teamInningScore`
- `teamTotal`
- `leaderId`
- `leaderDisplayName`
- `leadMargin`
- `remainingInnings`
- `needsExtraInning`
- `gameStatus`

Behavior:

- `targetNumber` must match the current inning rule.
- `leaderId` is null during ties.
- `leadMargin` is `0` during ties.
- `remainingInnings` is `0` once regulation is complete, even when extra innings are needed.
- During extra innings, display the active extra inning and target.

## Inning Scorecard

Required display data:

- Player score per inning.
- Player running total.
- Team score per inning when in team mode.
- Team running total when in team mode.
- Current leader and margin.
- Lock/final status for official games.

Behavior:

- Incomplete turns are visually distinct from completed turns.
- Corrected scores must be identifiable without exposing private audit details.
- Locked games must not expose editable score controls to unauthorized users.

## Analytics

Required metrics:

- Player average score per inning.
- Team average score per inning.
- Highest inning score.
- Shutout innings.
- Triple rate.
- Win percentage.
- Projected final score.
- Comeback probability.
- Player consistency.
- Best target numbers.
- Weakest target numbers.

Behavior:

- Metrics derive from recorded darts/turns and completed games.
- Predictive metrics must be labeled as estimates.
- Metrics with insufficient data return unavailable state.
- Analytics must update after accepted corrections.
- Analytics must not persist or display raw SSO identity data.

## Performance Contract

- Updating a live scoreboard snapshot after score entry should complete in <= 250 ms for the supported game scope.
- Recalculating one game after a correction should complete in <= 250 ms for up to 20 innings, two teams, and 8 players per team.
- Analytics views should avoid blocking the main thread for long-running history calculations.
