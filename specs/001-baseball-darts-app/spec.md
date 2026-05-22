# Feature Specification: Baseball Darts Mobile App

**Feature Branch**: `001-baseball-darts-app`

**Created**: 2026-05-22

**Status**: Draft

**Input**: User description: "Please create the initial set of requirements by using the attached markdown."

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Keep a Standalone Game Score (Priority: P1)

As a scorekeeper or player, I want to create a standalone Baseball Darts game,
enter each player's inning scores, and see live totals so that I can replace the
current spreadsheet scorekeeping workflow during casual or non-league play.

**Why this priority**: This is the scoring foundation for every other mode. It
delivers the smallest useful app experience and proves the core game rules.

**Independent Test**: Create a 9-inning standalone game with players or teams,
enter scores from 0 through 9 for each inning, verify live totals, complete the
game, and view the saved final scorecard.

**Acceptance Scenarios**:

1. **Given** a new standalone game with two players, **When** the scorekeeper
   enters valid scores for innings 1 through 9, **Then** the app displays each
   player's inning scores, running total, final total, winner, and margin.
2. **Given** a tied standalone game after inning 9, **When** extra innings are
   enabled, **Then** the app continues to inning 10 and beyond until the tie is
   broken.
3. **Given** a scorekeeper attempts to enter a value below 0, above 9, or a
   non-integer value, **When** the score is submitted, **Then** the app rejects
   the value and preserves the last valid score.

---

### User Story 2 - Run League Matches With Handicaps (Priority: P2)

As a League Manager or Team Manager, I want to create leagues, teams, players,
matches, lineups, handicaps, and league scorecards so that organized league play
can be scored consistently and official standings can be maintained.

**Why this priority**: League play is the most complex operational workflow and
drives roles, permissions, match locking, standings, and official statistics.

**Independent Test**: Configure a league, create two teams with players and seed
averages, create a multi-game match, select lineups for each game, score the
match, verify handicap calculations, lock prior games as the next game starts,
finalize the match, and confirm standings update.

**Acceptance Scenarios**:

1. **Given** a league match with two teams and configured handicap rules, **When**
   active lineups are selected for Game 1, **Then** the app snapshots player
   averages and calculates which team receives handicap.
2. **Given** Game 1 is complete, **When** Game 2 starts, **Then** Game 1 becomes
   locked for regular scorekeepers and players.
3. **Given** all games in a match are finalized, **When** the match is completed,
   **Then** the app awards game win points, applies the match total-score bonus
   according to league settings, updates standings, and updates player averages
   after the match, not after each game.

---

### User Story 3 - Correct Locked Game Mistakes (Priority: P3)

As a League Manager or Team Manager, I want controlled correction workflows for
locked games so that official records can be fixed without losing trust in the
score history.

**Why this priority**: Locked records protect official scoring, but leagues need
a transparent path for correcting scorekeeping mistakes.

**Independent Test**: Lock a completed game, submit or perform a correction with
a required reason, verify an audit record is created, and confirm affected
totals, standings, and statistics are recalculated.

**Acceptance Scenarios**:

1. **Given** a locked game contains an incorrect inning score, **When** an
   authorized correction is submitted with a reason, **Then** the app records
   who made the change, their role, the timestamp, previous value, new value,
   reason, and affected records.
2. **Given** a locked game correction changes the winner, **When** the correction
   is approved, **Then** game totals, match points, player stats, team stats,
   league standings, and analytics are recalculated from the corrected values.

---

### User Story 4 - Practice Targets Independently (Priority: P4)

As a player, I want to practice a specific inning target and save practice
results separately from official league records so that I can improve weak
targets without changing league averages or standings.

**Why this priority**: Practice mode supports player improvement while preserving
the integrity of official league data.

**Independent Test**: Select a player and target, enter multiple practice scores,
view target-specific practice statistics, and verify official league averages and
standings are unchanged.

**Acceptance Scenarios**:

1. **Given** a player starts a practice session for target 5, **When** they enter
   multiple valid scores, **Then** the app saves attempts, average score, best
   score, zero count, 9-count, and recent trend for that target.
2. **Given** practice scores exist for a player, **When** league stats are viewed,
   **Then** those practice scores do not affect official averages, handicaps,
   standings, or league statistics by default.

---

### User Story 5 - Review Statistics, Summaries, and Insights (Priority: P5)

As a player, Team Manager, or League Manager, I want post-game summaries,
standings, player statistics, team statistics, and predictive insights so that I
can understand performance and make league decisions.

**Why this priority**: Reporting and analytics are valuable once reliable scoring
and league data exist.

**Independent Test**: Complete one or more games and matches, then verify game
summary, match summary, player night summary, standings, player statistics, team
statistics, and baseline predictions display the expected values.

**Acceptance Scenarios**:

1. **Given** a completed game, **When** the user opens the game summary, **Then**
   the app shows the full scorecard, winner, raw and adjusted scores, handicap,
   margin, player totals, zero innings, 9-point innings, and performance versus
   average.
2. **Given** a completed match, **When** the user opens the match summary, **Then**
   the app shows game-by-game raw totals, handicaps, adjusted totals, winners,
   game points, bonus points, total match points, and player night summaries.
3. **Given** enough completed score history exists, **When** the user views live
   or historical analytics, **Then** the app shows projected final scores and
   performance trends using clearly labeled estimates.

### Edge Cases

- Tied games after regulation innings continue into extra innings until a winner
  is determined when extra innings are enabled.
- A league that disables extra innings must apply its configured tie rule.
- Scores outside 0 through 9, blank scores, decimal scores, and text input are
  rejected.
- "Blows" are treated as player-inning scores of 0 unless the league later
  configures a different meaning.
- New players without established averages use a league-provided seed average
  before participating in handicap calculations.
- Equal team average sums produce no handicap.
- Lower-average teams receive handicap even when they are already winning raw
  score unless league settings say otherwise.
- A tied total adjusted match score awards no bonus by default unless league
  settings choose split bonus, tiebreaker game, or raw-score tiebreak.
- Starting the next game locks the prior game and requires a correction workflow
  for later edits.
- A correction to a locked game recalculates every downstream total, statistic,
  standing, and insight derived from the corrected score.
- SSO provider unavailability, user cancellation, expired sessions, and sign-out
  must leave score data and official records in a safe, recoverable state.
- Network loss, app restart, or OS interruption during live scoring must not
  silently lose entered scores.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The app MUST support American Baseball Darts games with regulation
  innings 1 through 9, where each inning corresponds to the matching target.
- **FR-002**: The app MUST allow scores from 0 through 9 for each player in each
  inning and MUST reject invalid values.
- **FR-003**: The app MUST calculate player game totals as the sum of that
  player's inning scores.
- **FR-004**: The app MUST support standalone games for one player, multiple
  players, singles, and teams.
- **FR-005**: The app MUST allow standalone game setup with optional game name,
  date defaulting to the current date, participant type, player names, team
  names, rosters, regulation inning count, and extra innings setting.
- **FR-006**: The app MUST show live score state during scoring, including current
  inning, target, inning scores, player totals, team inning totals, team totals,
  leader, margin, and projected pace when available.
- **FR-007**: The app MUST support extra innings beyond 9 and dynamically extend
  the scorecard until the tie is resolved when extra innings are enabled.
- **FR-008**: The app MUST save completed standalone games with final scorecard,
  winner, margin, extra innings played, zero-inning counts, 9-point inning
  counts, and performance versus average where applicable.
- **FR-009**: The app MUST support Practice Mode with player selection, target or
  inning selection, optional number of attempts, date defaulting to the current
  date, score entry from 0 through 9, and saved practice results.
- **FR-010**: The app MUST keep practice scores separate from official league
  averages, handicaps, standings, and player statistics by default.
- **FR-011**: The app MUST support league creation and configuration for league
  name, games per match, players per team per game, handicap percentage,
  handicap rounding rule, points per game win, match bonus value, average update
  rule, extra innings rule, match bonus tie rule, score correction policy, and
  manager permissions.
- **FR-012**: The app MUST support league teams, players, rosters, active or
  inactive status, and manager assignments.
- **FR-013**: The app MUST enforce role-based permissions for League Managers,
  Team Managers, Scorekeepers, Players, Viewers, and app administrators.
- **FR-014**: League Managers MUST be able to configure leagues, manage teams and
  players, assign managers, set starting averages, create matches, correct
  official records according to policy, finalize matches, manage standings
  corrections, view league analytics, and export league data.
- **FR-015**: Team Managers MUST be able to manage their roster when permitted,
  select lineups, enter or confirm scores for their team, review summaries, flag
  mistakes, request locked-game corrections, and view team/player analytics.
- **FR-016**: Scorekeepers MUST be able to enter inning scores, edit unlocked game
  scores, review totals, submit games for confirmation, and finalize games when
  permitted.
- **FR-017**: Players MUST be able to view their own stats, team and league
  standings, game and match history, and enter practice scores.
- **FR-018**: The app MUST support matches between two teams with one or more
  games and configurable active lineups for each game.
- **FR-019**: The app MUST snapshot each active player's average at the beginning
  of the match and use that snapshot for every handicap calculation in that
  match.
- **FR-020**: The app MUST calculate team average sums from active lineup player
  averages for each game.
- **FR-021**: The app MUST calculate handicap as the difference between higher
  and lower active lineup average sums multiplied by the league handicap
  percentage.
- **FR-022**: The app MUST apply handicap to the lower-average team's final game
  total by default and calculate adjusted game totals.
- **FR-023**: The app MUST support handicap rounding rules of nearest whole
  number, floor, ceiling, and decimal allowed, with nearest whole number as the
  default.
- **FR-024**: The app MUST determine standalone game leaders by raw totals and
  league game leaders by adjusted totals unless league settings say otherwise.
- **FR-025**: The app MUST award game win points according to league settings.
- **FR-026**: The app MUST award the match total-score bonus to the team with the
  highest total adjusted score across all games unless the match bonus is tied or
  league settings say otherwise.
- **FR-027**: The app MUST default to no bonus point when total adjusted match
  score is tied.
- **FR-028**: The app MUST update player averages from raw official league scores
  only, after match finalization, not after each game.
- **FR-029**: The app MUST calculate player average as official raw score total
  divided by official games played.
- **FR-030**: The app MUST lock Game N when Game N+1 starts and lock all match
  games when the match is finalized.
- **FR-031**: Locked games MUST remain visible but MUST NOT be editable by regular
  players or scorekeepers.
- **FR-032**: The app MUST provide controlled correction workflows for locked or
  completed games according to league policy.
- **FR-033**: Any edit to a locked or completed game MUST create an audit record
  with editor, role, timestamp, reason, previous value, new value, and affected
  records.
- **FR-034**: Correcting a locked game MUST recalculate affected player totals,
  team raw totals, adjusted totals, winners, match points, statistics, standings,
  and analytics.
- **FR-035**: The app MUST provide a mobile score entry view optimized for live
  play with one-inning-at-a-time entry, large 0 through 9 controls, current inning
  indicator, quick undo before lock, and clear completion confirmation.
- **FR-036**: The app MUST provide scorecard views that include player lineups,
  averages, inning scores, player totals, team inning totals, cumulative totals,
  raw totals, handicap, adjusted totals, winner, and margin.
- **FR-037**: The app MUST provide standings with rank, team, matches, games won,
  games lost, league points, adjusted score for, adjusted score against, and
  differential.
- **FR-038**: The app MUST rank league standings by league points, then games won,
  point differential, head-to-head record, total adjusted score, and total raw
  score by default.
- **FR-039**: The app MUST provide player statistics including games played, total
  score, average, high game, low game, zero innings, and 9-point innings.
- **FR-040**: The app MUST provide recommended extended statistics including
  matches played, night average, recent average, trend, consistency, performance
  by target, zero rate, and 9-rate when sufficient data exists.
- **FR-041**: The app MUST provide team statistics including games won/lost, match
  points, raw score average, adjusted score average, handicap received/given, win
  percentage, match win percentage, point differential, inning differential, and
  player contribution.
- **FR-042**: The app MUST provide league analytics including team standings,
  player leaderboards, high games, improvement, consistency, zero innings,
  9-point innings, best team offense, best adjusted team, upset wins, and handicap
  impact.
- **FR-043**: The app MUST provide game summaries, match summaries, and player
  night summaries after completion.
- **FR-044**: The app SHOULD provide baseline predictive insights including pace
  projection and average-based predicted final score.
- **FR-045**: The app SHOULD support future analytics for inning-specific
  predictions, simulation-based win probability, lineup optimization, handicap
  impact reports, exports, notifications, scheduling, attendance, and league
  messaging.

### Security, Privacy, and Identity Requirements *(mandatory)*

- **SPR-001**: The app MUST classify and protect user account data, role
  assignments, league membership, player identity, scores, corrections,
  standings, analytics, and audit history.
- **SPR-002**: The app MUST minimize personal data collection to information
  needed for account identity, league participation, scorekeeping, statistics,
  support, and optional exports.
- **SPR-003**: User authentication MUST use trusted SSO identity providers
  configured for the app; the app MUST NOT collect or store first-party passwords
  for this feature.
- **SPR-004**: The app MUST define safe user-visible behavior for sign-in,
  sign-out, session expiration, provider failure, user cancellation, account
  linking, and account recovery.
- **SPR-005**: Authorization MUST be enforced for league configuration, team
  management, score entry, game finalization, locked-game correction, data export,
  analytics visibility, and app administration.
- **SPR-006**: Locked-game corrections and administrative actions MUST be auditable
  and attributable to an authenticated user and their role at the time of action.
- **SPR-007**: Error, crash, analytics, and diagnostic events MUST exclude access
  tokens, identity tokens, secrets, precise personal data not needed for support,
  and unredacted score correction reasons when those reasons contain private
  information.
- **SPR-008**: Exports MUST be available only to authorized users and MUST make the
  exported scope clear before the export is created.

### Performance and Stability Requirements *(mandatory)*

- **PSR-001**: Live score entry MUST feel immediate; after a valid score is
  entered, visible player, team, leader, margin, and adjusted totals should update
  within 1 second under normal device conditions.
- **PSR-002**: The primary score entry screen MUST remain usable during a full
  multi-game match without requiring app restart or manual scorecard refresh.
- **PSR-003**: The app MUST preserve entered scores through app backgrounding,
  foregrounding, restart, session refresh, and transient network loss.
- **PSR-004**: Completing a game, starting the next game, locking the prior game,
  and finalizing a match MUST be transactional from the user's perspective: the
  app must not leave records half-locked or half-finalized.
- **PSR-005**: Score calculations, standings, summaries, and correction
  recalculations MUST be deterministic and repeatable from the saved official
  score records.
- **PSR-006**: Predictive analytics MUST be clearly labeled as estimates and MUST
  never block core score entry or official scoring workflows.
- **PSR-007**: Failure states for SSO, network access, save conflicts, correction
  conflicts, and permission denial MUST preserve existing score data and provide a
  clear recovery path.

### Key Entities *(include if feature involves data)*

- **User Account**: Authenticated person using the app; includes display name,
  email or provider identity, account status, and app-level access.
- **Role Assignment**: User's permissions within a league or team; includes role,
  scope, and effective permissions.
- **Player**: Darts participant who may or may not have a linked user account;
  includes display name and active status.
- **League**: Configured competition with scoring, handicap, match, correction,
  standings, and manager settings.
- **Team**: League participant group with active status, roster, captain or team
  manager, and league association.
- **Team Roster Entry**: Association between a team and player over a date range.
- **Match**: Contest between two teams on a date; contains one or more games,
  match points, adjusted totals, bonus results, and finalization status.
- **Game**: One Baseball Darts game within standalone or league play; contains
  participants, lineups, scores, raw totals, handicap, adjusted totals, winner,
  lock status, and completion status.
- **Game Lineup**: Active players for a team in a specific game and their average
  snapshot for handicap calculations.
- **Inning Score**: One player's score for one inning target in one game.
- **Player Game Score**: A player's raw total, performance versus average, zero
  count, and 9-point count for a game.
- **Player League Stats**: Aggregated official league stats for a player.
- **Team League Stats**: Aggregated official league standings and scoring stats
  for a team.
- **Practice Attempt**: Non-official score entry for a player practicing a
  specific target.
- **Correction Audit Record**: Immutable record of locked or completed game
  changes and downstream recalculation impact.
- **Summary and Insight**: Derived game, match, player-night, standings,
  analytics, projection, or win-probability output.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: A scorekeeper can create and complete a standalone 9-inning game
  with at least two players in under 5 minutes during usability testing.
- **SC-002**: 100% of invalid inning scores below 0, above 9, blank, decimal, or
  non-numeric are rejected without changing saved totals.
- **SC-003**: In a test match with changing lineups, calculated handicaps,
  adjusted totals, game points, match bonus points, standings, and updated player
  averages match independently calculated expected values.
- **SC-004**: Starting Game N+1 locks Game N in 100% of tested match flows, and
  locked games cannot be edited by unauthorized users.
- **SC-005**: 100% of locked-game corrections in test scenarios create audit
  records and recalculate every affected summary, statistic, and standing.
- **SC-006**: Practice scores remain excluded from official league averages,
  handicaps, standings, and player statistics in 100% of tested scenarios.
- **SC-007**: After each score entry, visible live totals and leader/margin state
  update within 1 second under normal test conditions.
- **SC-008**: A completed game summary, match summary, and player night summary
  show all required fields from this specification for tested league matches.
- **SC-009**: Role-based access tests demonstrate that League Managers, Team
  Managers, Scorekeepers, Players, Viewers, and administrators can perform only
  the actions allowed by their role and league settings.
- **SC-010**: During simulated app restart or transient network loss, previously
  entered scores for the active scoring session are recoverable without silent
  data loss.
- **SC-011**: At least 90% of beta scorekeepers report that live score entry is
  fast enough to keep up with play.
- **SC-012**: No privacy review findings identify tokens, secrets, or unnecessary
  personal data in logs, crash reports, analytics, or exported files.

## Assumptions

- The initial feature covers the product requirements baseline for Game Mode,
  Practice Mode, League Mode, summaries, standings, statistics, and baseline
  predictive insights.
- Mobile support is the intended product surface; web administration is outside
  this initial specification unless later added.
- Scores are entered per player per inning, and team totals are calculated from
  player scores.
- "Blows" means the count of player innings with a score of 0.
- Handicap is added to the lower-average team's final game total, not distributed
  across innings.
- Handicap rounding defaults to nearest whole number.
- Standalone game winners use raw score; league game winners use adjusted score.
- No match total-score bonus is awarded when total adjusted match score is tied
  unless league settings choose a different rule.
- Team Managers can request locked-game corrections by default; League Managers
  can approve or perform corrections according to league policy.
- New players require a League Manager-provided seed average or league default
  before they are included in handicap calculations.
- Default league ranking is league points, games won, point differential,
  head-to-head record, total adjusted score, then total raw score.
- Official player averages use raw official league scores only and update after
  match finalization.
- Practice scores do not affect official league stats unless a future league
  setting explicitly enables that behavior.
- Predictive scoring and win probability are secondary to accurate scoring,
  locking, corrections, and standings.
