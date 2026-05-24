# Feature Specification: Baseball Darts Rules Revamp

**Feature Branch**: `007-baseball-darts-rules`

**Created**: 2026-05-24

**Status**: Draft

**Input**: User description: "Revamp the rules of the darts game baseball by creating a new spec" with detailed Baseball Darts rules covering objective, innings 1-9, per-dart scoring, individual and team play, turn order, scorekeeping, extra innings, post-20 tiebreakers, valid dart rules, substitutions, corrections, game locking, league roles, scoreboard display, analytics, optional rules, and recommended defaults.

## User Scenarios & Testing *(mandatory)*

### User Story 1 - Score A Standard Baseball Darts Game (Priority: P1)

As a scorekeeper, I want the app to enforce standard Baseball Darts inning targets and per-dart scoring so that a casual or league game can be scored correctly from inning 1 through inning 9.

**Why this priority**: Correct inning targets, three-dart scoring, and totals are the core game rule. Without this, every other workflow is unreliable.

**Independent Test**: Start a two-player game, score innings 1 through 9 using misses, singles, doubles, and triples, and verify each player inning score, running total, winner, and margin.

**Acceptance Scenarios**:

1. **Given** the game is in the 4th inning, **When** a scorekeeper records darts that hit single 4, triple 4, and a wrong number, **Then** the inning score is 4 runs and non-4 darts score 0.
2. **Given** a player throws three darts in any regulation inning, **When** all three darts hit the triple ring of the target number, **Then** the player receives 9 runs for that inning.
3. **Given** a scorekeeper records a dart that misses the current target, **When** the dart is saved, **Then** that dart contributes 0 runs and remains visible as a miss.
4. **Given** a standard game reaches the end of the 9th inning with one player ahead, **When** both players have completed their 9th-inning turns, **Then** the app declares the higher total as the winner and shows the margin.

---

### User Story 2 - Support Individual And Team Play (Priority: P1)

As a player or league scorekeeper, I want to score individual games and team games so that Baseball Darts can be used for one-on-one play and multi-player league formats.

**Why this priority**: The rules explicitly support both player-vs-player and team-vs-team games, and team totals drive league scoring.

**Independent Test**: Create an individual game and a team game, enter one inning of scores for each participant, and verify individual totals, team inning totals, and team game totals.

**Acceptance Scenarios**:

1. **Given** an individual game has Player A and Player B, **When** each player completes an inning, **Then** the app displays each player's inning score and running total.
2. **Given** a team game has Team A with three players, **When** each Team A player scores 2, 1, and 3 in the 1st inning, **Then** Team A's 1st-inning total is 6.
3. **Given** a team game is configured, **When** the game starts, **Then** each team has an internal batting order and the app advances through that order consistently.
4. **Given** a team includes four players, **When** the scorekeeper views maximum possible standard-game score, **Then** the app can derive 324 maximum team runs from 4 players x 81 runs.

---

### User Story 3 - Resolve Ties With Configurable Extra Innings (Priority: P2)

As a scorekeeper, I want tied games to continue through extra innings using clear target numbers and fair turn completion so that winners are decided consistently.

**Why this priority**: Ties are common enough to require deterministic handling, and extra innings must be fair for both sides.

**Independent Test**: Complete a tied 9-inning game, score the 10th inning for both sides, and verify the game either declares a winner after equal turns or advances to the next extra inning when still tied.

**Acceptance Scenarios**:

1. **Given** the game is tied after 9 innings, **When** extra innings begin, **Then** the 10th inning target is 10.
2. **Given** Team A scores 2 in the 10th inning, **When** Team B has not thrown in the 10th inning, **Then** the app does not declare Team A the winner yet.
3. **Given** both sides complete the 10th inning and Team B leads, **When** Team B's turn is saved, **Then** Team B wins.
4. **Given** the game remains tied after the 20th inning, **When** the default post-20 rule is active, **Then** the next inning continues targeting 20 until the tie is broken.

---

### User Story 4 - Manage Official League Corrections, Locks, Substitutions, And Roles (Priority: P3)

As a League Manager, I want substitutions, score corrections, game locking, and role permissions to follow controlled rules so that official records stay trustworthy.

**Why this priority**: Organized play needs accountability and auditability after the core scoring flow works.

**Independent Test**: Complete a league game, lock it, attempt a correction as a player, then perform a correction as a league manager with a reason and verify the audit record and recalculated totals.

**Acceptance Scenarios**:

1. **Given** a game is active, **When** an authorized scorekeeper corrects a current-inning mistake with agreement, **Then** the correction is saved and visible in the score history.
2. **Given** a game is locked, **When** a player or team manager attempts to edit it without permission, **Then** the app blocks the edit and leaves the official score unchanged.
3. **Given** a League Manager corrects a locked game, **When** the correction is saved, **Then** the app records original score, corrected score, editor, timestamp, reason, and affected records.
4. **Given** a substitute replaces a player mid-game where league rules allow it, **When** the substitution is saved, **Then** prior scores remain with the original player and team totals remain unchanged.

---

### User Story 5 - Present Scoreboards, Summaries, And Analytics (Priority: P4)

As a player, team manager, or league manager, I want live scoreboards, final summaries, and performance analytics so that I can understand the game state and long-term performance.

**Why this priority**: Scorekeeping is the foundation; analytics and summaries increase value once score data is reliable.

**Independent Test**: Score a partial game and a completed game, then verify the live status, final scorecard, current leader, lead margin, remaining innings, and analytics values.

**Acceptance Scenarios**:

1. **Given** a game is in progress, **When** the live scoreboard is opened, **Then** it shows current inning, target number, current thrower, player inning score, player total, team inning score, team total, leader, lead margin, and remaining innings.
2. **Given** Team A leads Team B by 4 after the 6th inning, **When** the live status is displayed, **Then** the app states that Team A leads by 4 runs after the 6th inning.
3. **Given** completed score history exists, **When** analytics are viewed, **Then** the app can show player average score, team average score, highest inning score, shutout innings, triple rate, win percentage, predicted final score, consistency, best target numbers, and weakest target numbers.

### Edge Cases

- A player scores 0 in an inning because all three darts miss the current target.
- A dart hits the correct number but falls out before being scored.
- A dart is thrown out of turn.
- A strict league enforces throw-line foot faults.
- A player or team leads before the opposing side has completed the same extra inning.
- The game remains tied after the 20th inning.
- A league uses restart-at-1 or bullseye tiebreaker instead of repeat-20 extra innings.
- A bullseye tiebreaker records outer bull and inner bull values.
- A team changes its batting order after the game starts.
- A substitute enters before a game starts or during a game.
- A score is corrected after an inning but before the next game begins.
- A prior game is locked because the next scheduled game has started.
- A locked game correction changes the winner, totals, standings, or analytics.
- Optional rules are enabled, disabled, or changed between casual play and league play.
- Network loss, app restart, or OS interruption occurs during a live scoring turn.

## Requirements *(mandatory)*

### Functional Requirements

- **FR-001**: The app MUST support standard Baseball Darts games where regulation innings 1 through 9 target dartboard numbers 1 through 9 in order.
- **FR-002**: The app MUST count only darts that land in the current inning's target number.
- **FR-003**: The app MUST score each dart as 0 for a miss or invalid dart, 1 for a single target segment, 2 for a double target segment, and 3 for a triple target segment.
- **FR-004**: The app MUST support exactly three darts per player per inning by default.
- **FR-005**: The app MUST calculate player inning score as the sum of the three dart scores for that player's turn.
- **FR-006**: The app MUST enforce 0 through 9 as the valid player inning score range for standard three-dart innings.
- **FR-007**: The app MUST calculate player game totals as the sum of all player inning scores.
- **FR-008**: The app MUST calculate standard maximum individual game score as 81 runs.
- **FR-009**: The app MUST support individual games where one player competes against another player.
- **FR-010**: The app MUST support team games where each team has two or more players.
- **FR-011**: The app MUST calculate team inning score as the sum of every team player's score for that inning.
- **FR-012**: The app MUST calculate team game total as the sum of all team inning scores.
- **FR-013**: The app MUST allow turn order to be recorded using random selection, coin flip, cork/diddle, league schedule, or home/away designation.
- **FR-014**: The app MUST preserve the established player or team turn order for the full game unless an authorized correction or substitution rule changes it.
- **FR-015**: The app MUST allow team games to define an internal batting order before the game begins.
- **FR-016**: The app MUST provide a live scoresheet showing player inning scores, player totals, team inning scores, team totals, current leader, and lead margin.
- **FR-017**: The app MUST show current inning, target number, current thrower, remaining innings, and live status text during scoring.
- **FR-018**: The app MUST determine the winner after both sides complete the 9th inning when one player or team has the highest total.
- **FR-019**: The app MUST show final score, winner, and win margin after game completion.
- **FR-020**: The app MUST continue to extra innings when the game is tied after the 9th inning and extra innings are enabled.
- **FR-021**: Extra innings 10 through 20 MUST target dartboard numbers 10 through 20 respectively.
- **FR-022**: The app MUST require all players or teams to receive the same number of turns in each extra inning before declaring a winner.
- **FR-023**: The game MUST continue until one player or team leads after all sides have completed the same extra inning.
- **FR-024**: The default post-20 tie rule MUST continue targeting 20 until the tie is broken.
- **FR-025**: The app MUST support configurable post-20 tie rules: repeat 20s, restart at 1, or bullseye tiebreaker.
- **FR-026**: When bullseye tiebreaker is enabled, the app MUST support outer bull scoring 1 and inner bull scoring 2 by default.
- **FR-027**: The app MUST mark a dart valid only when it remains in the board until scored, lands in the correct target number, is thrown from behind the throw line, and is thrown during the proper turn.
- **FR-028**: The app MUST mark a dart invalid when it falls out before scoring, bounces off, lands in the wrong number, is thrown out of turn, or violates strict throw-line rules when those rules are enabled.
- **FR-029**: The app MUST support zero-score innings and display them explicitly.
- **FR-030**: The app MUST support player substitutions before the game starts when allowed by game or league rules.
- **FR-031**: The app MUST support mid-game substitutions only when league rules allow them.
- **FR-032**: The app MUST keep scores already recorded for an original player with that original player after substitution.
- **FR-033**: The app MUST keep team totals unchanged when substitution records are applied correctly.
- **FR-034**: The app MUST allow score corrections during the current inning when authorized users confirm the correction policy is satisfied.
- **FR-035**: The app MUST allow after-inning score corrections only when the mistake is permitted by league rules and authorized users agree.
- **FR-036**: The app MUST lock prior games once the next scheduled game begins or when a league manager marks the game final.
- **FR-037**: Locked games MUST be viewable but not editable by players or team managers unless league rules grant explicit permission.
- **FR-038**: League managers MUST be able to unlock or correct games when league policy allows it.
- **FR-039**: Every correction to a completed or locked game MUST create an audit history entry.
- **FR-040**: Correction audit entries MUST include original score, corrected score, edited by, timestamp, reason, and affected records.
- **FR-041**: The app MUST recalculate affected game totals, team totals, winners, standings, and analytics after an authorized correction.
- **FR-042**: The app MUST support League Manager, Team Manager, and Player roles for organized play.
- **FR-043**: League Managers MUST be able to create leagues, create teams, manage schedules, add or remove players, assign team managers, edit completed games if allowed, lock and unlock games, resolve disputes, and view analytics and standings.
- **FR-044**: Team Managers MUST be able to manage team roster, set player order, enter scores, correct active-game mistakes when allowed, view team statistics, and confirm final scores.
- **FR-045**: Players MUST be able to view schedules, scores, standings, personal statistics, and participate in games.
- **FR-046**: The app MUST support optional bullseye bonus rules, with default off.
- **FR-047**: The app MUST support optional 7th inning stretch rules, with default off.
- **FR-048**: The app MUST support optional mercy rule configuration.
- **FR-049**: The app MUST support optional handicap scoring configuration, with default off for the standard clean rule set.
- **FR-050**: The app MUST expose the recommended default rule set: innings 1 through 9, three darts per player per inning, single 1, double 2, triple 3, max 9 per player per inning, highest total after 9 wins, extra innings starting at 10, post-20 repeat 20s, bullseye bonus off, score corrections allowed until game lock, and audit history recommended.
- **FR-051**: The app MUST provide final and historical analytics from scored innings, including player average score, team average score, highest inning score, shutout innings, triple rate, win percentage, predicted final score, comeback probability, player consistency, best target numbers, and weakest target numbers where enough data exists.

### Security, Privacy, and Identity Requirements *(mandatory)*

- **SPR-001**: The feature MUST identify all scoring, player, team, league, role, schedule, correction, audit, analytics, and session data displayed, stored, transmitted, or deleted.
- **SPR-002**: Sensitive data MUST include authenticated account identifiers, role assignments, audit editor identity, private player notes, unreleased league schedules, score corrections, dispute records, and any provider or session data.
- **SPR-003**: League official scoring, locking, unlocking, substitutions, corrections, standings updates, and audit history MUST require authenticated users with appropriate roles.
- **SPR-004**: Casual local scoring MAY allow unauthenticated use only if no protected league, account, or cloud data is accessed.
- **SPR-005**: Score correction and lock/unlock diagnostics MUST avoid raw account identifiers, provider tokens, private notes, and unnecessary personal data.
- **SPR-006**: Audit history MUST preserve accountability for official corrections while exposing only role-appropriate details to non-manager users.
- **SPR-007**: The app MUST prevent unauthorized users from editing locked games, changing official corrections, or modifying role-controlled league records.

### Performance and Stability Requirements *(mandatory)*

- **PSR-001**: Live score entry actions MUST feel immediate and update visible totals within 250 ms on representative supported devices.
- **PSR-002**: Recalculating a single active game after score entry MUST complete without visible delay for regulation and extra-inning play.
- **PSR-003**: Authorized correction recalculation MUST preserve data integrity for player totals, team totals, standings, and analytics even if the app is interrupted.
- **PSR-004**: The app MUST preserve in-progress scoring state across app backgrounding, rotation, process recreation, and short network loss.
- **PSR-005**: The app MUST prevent silent data loss during live scoring, substitutions, corrections, locking, and finalization.
- **PSR-006**: Analytics MUST be clearly labeled as estimates when predictive values such as predicted final score or comeback probability are shown.
- **PSR-007**: Extra innings MUST not create unbounded UI or memory growth; long games must remain reviewable and recoverable.

### Key Entities *(include if feature involves data)*

- **Rule Set**: Configurable game rules, including regulation innings, darts per inning, scoring values, extra-inning behavior, post-20 tiebreaker, optional rules, correction policy, locking policy, and handicap policy.
- **Game**: A Baseball Darts contest with participants, turn order, innings, score entries, status, winner, margin, and lock state.
- **Participant**: A player or team competing in a game.
- **Team**: A group of players with roster, batting order, manager, and team totals.
- **Player**: A person who can throw darts, hold scores, appear on rosters, receive substitutions, and accumulate statistics.
- **Inning**: A numbered round with target number, participant turns, player scores, team scores, and completion state.
- **Dart Throw**: One recorded dart outcome with target number, hit area, run value, validity state, and optional invalid reason.
- **Score Entry**: A player's three-dart inning result, including per-dart outcomes and total runs from 0 through 9.
- **Extra Inning Rule**: The tie-resolution behavior after regulation and after target 20.
- **Substitution**: A roster change record defining original player, substitute, timing, allowed rule, and score ownership.
- **Correction Audit Entry**: An immutable record of score or state correction with original value, corrected value, editor, role, timestamp, reason, and affected records.
- **Game Lock**: A state preventing normal edits after next game start, finalization, or manager lock.
- **League Role**: Permission category such as League Manager, Team Manager, or Player.
- **Scoreboard Summary**: Live or final display of inning, target, current thrower, totals, leader, margin, and remaining innings.
- **Analytics Metric**: Derived performance result such as average score, triple rate, shutout innings, win percentage, consistency, or target strengths and weaknesses.

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 100% of standard regulation games map innings 1 through 9 to targets 1 through 9 and reject scoring that does not belong to the active target.
- **SC-002**: 100% of recorded player inning scores are constrained to 0 through 9 for the standard three-dart rule set.
- **SC-003**: Team inning totals and game totals match the sum of all roster player scores for every completed team inning.
- **SC-004**: Tied games after the 9th inning continue until one side leads after equal extra-inning turns.
- **SC-005**: The default post-20 tie behavior repeats target 20 unless a league explicitly selects restart-at-1 or bullseye tiebreaker.
- **SC-006**: Locked-game edits by unauthorized users are blocked 100% of the time in role-based tests.
- **SC-007**: 100% of authorized locked-game corrections produce audit history with original score, corrected score, editor, timestamp, reason, and affected records.
- **SC-008**: Live score entry updates visible player total, team total, leader, and margin within 250 ms on representative supported devices.
- **SC-009**: App restart or OS interruption during live scoring recovers the last saved inning, target, thrower, and score entries without silent loss.
- **SC-010**: Scoreboard views show current inning, target number, current thrower, participant totals, team totals, leader, lead margin, and remaining innings for active games.

## Assumptions

- This feature revamps the app's Baseball Darts rules model and scoring behavior; UI redesign is limited to whatever is needed to express those rules clearly.
- The recommended default rule set is the standard mode for new games unless a league or user selects a configurable house rule.
- After the 20th inning, the default tiebreaker is repeat 20s until the tie is broken.
- Bullseye bonus, 7th inning stretch, mercy rule, and handicap scoring are configurable optional rules, not part of the clean standard default.
- League roles and official game records require authenticated access through the existing SSO model.
- Casual local games may be supported without league authorization if they do not touch protected account or league data.
- Audit history is required for official corrections; implementation details for storage and retention will be decided during planning.
