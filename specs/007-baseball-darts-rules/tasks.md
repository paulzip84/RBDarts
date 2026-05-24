# Tasks: Baseball Darts Rules

**Input**: Design documents from `/specs/007-baseball-darts-rules/`

**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/, quickstart.md

**Tests**: Required for scoring-critical rules, Swift/Kotlin parity, team totals, extra innings, corrections, locking, substitutions, role authorization, persistence recovery, scoreboard state, analytics, privacy-safe diagnostics, performance, Android unit/androidTest verification, and iOS XCTest verification.

**Organization**: Tasks are grouped by user story so the Baseball Darts rules revamp can be implemented and tested incrementally.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel because it touches different files and does not depend on incomplete tasks.
- **[Story]**: Which user story this task belongs to, only used in user story phases.
- Every task includes an exact file path.

## Phase 1: Setup (Rules Revamp Workspace)

**Purpose**: Prepare security, fixture, performance, and verification documentation before code changes.

- [x] T001 Create scoring data threat model and data classification notes in `specs/007-baseball-darts-rules/security-threat-model.md`
- [x] T002 [P] Create shared fixture inventory and parity coverage plan in `specs/007-baseball-darts-rules/fixture-plan.md`
- [x] T003 [P] Create scoring performance and recovery validation checklist in `specs/007-baseball-darts-rules/performance-recovery-checklist.md`
- [x] T004 [P] Create implementation verification log in `specs/007-baseball-darts-rules/verification.md`
- [x] T005 Record current per-inning total scoring baseline and migration notes in `specs/007-baseball-darts-rules/scoring-baseline.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establish shared contracts, source-of-truth models, diagnostics, and fixture schema support before user story work.

**CRITICAL**: No user story implementation should begin until this foundation is complete.

- [x] T006 [P] Extend dart-level scoring fixture schema in `shared-contracts/schemas/scoring-fixture.schema.json`
- [x] T007 [P] Add correction and authorization fixture schema in `shared-contracts/schemas/security-rule-fixture.schema.json`
- [x] T008 [P] Add standard rule set fixture in `shared-contracts/fixtures/scoring/baseball-standard-ruleset.json`
- [x] T009 [P] Add invalid dart result fixture set in `shared-contracts/fixtures/scoring/baseball-invalid-darts.json`
- [x] T010 [P] Add locked correction fixture set in `shared-contracts/fixtures/corrections/baseball-locked-correction.json`
- [x] T011 [P] Add Android domain contract test coverage in `android/app/src/test/java/com/rbdarts/core/domain/BaseballDartsRulesTest.kt`
- [x] T012 [P] Add iOS domain contract test coverage in `ios/RBDartsTests/Domain/ScoringRulesTests.swift`
- [x] T013 Add Kotlin Baseball Darts enums and value models for rule set, post-20 rule, dart ring, dart validity, dart throw, turn, and summary data in `android/app/src/main/java/com/rbdarts/core/domain/Models.kt`
- [x] T014 Add Swift Baseball Darts enums and value models for rule set, post-20 rule, dart ring, dart validity, dart throw, turn, and summary data in `ios/RBDarts/Shared/Domain/Models.swift`
- [x] T015 Add privacy-safe scoring, correction, lock, and substitution diagnostics event names in `android/app/src/main/java/com/rbdarts/core/observability/Diagnostics.kt`
- [x] T016 Add privacy-safe scoring, correction, lock, and substitution diagnostics event names in `ios/RBDarts/Shared/Observability/Diagnostics.swift`
- [ ] T017 Update repository/session storage interfaces for dart-level scoring source records in `android/app/src/main/java/com/rbdarts/core/data/RBDartsRepository.kt`
- [ ] T018 Update repository/session storage interfaces for dart-level scoring source records in `ios/RBDarts/Shared/Data/RBDartsRepository.swift`
- [ ] T019 Update active score session persistence for restart recovery in `android/app/src/main/java/com/rbdarts/core/data/local/ActiveScoreDatabase.kt`
- [ ] T020 Update active score session persistence for restart recovery in `ios/RBDarts/Shared/Data/ActiveScoreSessionStore.swift`
- [x] T021 Run baseline compile check with `cd android && ./gradlew compileDebugKotlin` and record result in `specs/007-baseball-darts-rules/verification.md`

**Checkpoint**: Foundation ready. User story implementation can now begin.

---

## Phase 3: User Story 1 - Score A Standard Baseball Darts Game (Priority: P1) MVP

**Goal**: Enforce inning targets and per-dart scoring for a standard two-player Baseball Darts game from inning 1 through inning 9.

**Independent Test**: Start a two-player game, score innings 1 through 9 with misses, singles, doubles, and triples, and verify inning scores, running totals, winner, and margin.

### Tests for User Story 1

- [x] T022 [P] [US1] Add Android unit tests for target resolution, `scoreDart`, `scoreTurn`, invalid darts, max inning score 9, and max regulation score 81 in `android/app/src/test/java/com/rbdarts/core/domain/BaseballDartsRulesTest.kt`
- [x] T023 [P] [US1] Add iOS unit tests for target resolution, `scoreDart`, `scoreTurn`, invalid darts, max inning score 9, and max regulation score 81 in `ios/RBDartsTests/Domain/ScoringRulesTests.swift`
- [x] T024 [P] [US1] Add shared dart-level standard game fixture in `shared-contracts/fixtures/scoring/baseball-standard-nine-innings.json`
- [ ] T025 [P] [US1] Add Android standalone score entry UI test for three darts, target labels, misses, and winner state in `android/app/src/androidTest/java/com/rbdarts/feature/standalonegame/BaseballStandaloneScoringFlowTest.kt`
- [ ] T026 [P] [US1] Add iOS standalone score entry XCTest for three darts, target labels, misses, and winner state in `ios/RBDartsTests/StandaloneGame/BaseballStandaloneScoringFlowTests.swift`

### Implementation for User Story 1

- [x] T027 [US1] Implement Kotlin standard Baseball Darts rule set, target resolution, dart scoring, turn scoring, participant totals, winner, and margin in `android/app/src/main/java/com/rbdarts/core/domain/ScoringRules.kt`
- [x] T028 [US1] Implement Swift standard Baseball Darts rule set, target resolution, dart scoring, turn scoring, participant totals, winner, and margin in `ios/RBDarts/Shared/Domain/ScoringRules.swift`
- [x] T029 [US1] Update Android scoring UI state for current inning, target number, current thrower, winner, and margin in `android/app/src/main/java/com/rbdarts/core/ui/AppUiStates.kt`
- [ ] T030 [US1] Update Android score entry controls for miss/single/double/triple dart entry and invalid dart reasons in `android/app/src/main/java/com/rbdarts/feature/scoring/ScoreEntryControls.kt`
- [ ] T031 [US1] Update Android scoring ViewModel to record dart-level turns and derive inning totals in `android/app/src/main/java/com/rbdarts/feature/scoring/ScoringViewModel.kt`
- [ ] T032 [US1] Update Android standalone game service to save and recover dart-level standard games in `android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameService.kt`
- [ ] T033 [US1] Update Android standalone score entry screen to show inning target, dart states, turn total, running total, winner, and margin in `android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneScoreEntryScreen.kt`
- [ ] T034 [US1] Update Swift standalone game service to save and recover dart-level standard games in `ios/RBDarts/Features/StandaloneGame/StandaloneGameService.swift`
- [ ] T035 [US1] Update Swift standalone score entry view to show inning target, dart states, turn total, running total, winner, and margin in `ios/RBDarts/Features/StandaloneGame/StandaloneScoreEntryView.swift`
- [ ] T036 [US1] Emit privacy-safe invalid score and rejected dart diagnostics in `android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameDiagnostics.kt`
- [ ] T037 [US1] Emit privacy-safe invalid score and rejected dart diagnostics in `ios/RBDarts/Features/StandaloneGame/StandaloneGameDiagnostics.swift`

**Checkpoint**: User Story 1 is independently demoable as a standard two-player game.

---

## Phase 4: User Story 2 - Support Individual And Team Play (Priority: P1)

**Goal**: Support individual games and team games with lineups, batting order, player totals, team inning totals, and team game totals.

**Independent Test**: Create one individual game and one team game, enter one inning of scores for each participant, and verify individual totals, team inning totals, and team game totals.

### Tests for User Story 2

- [x] T038 [P] [US2] Add Android unit tests for individual participants, team lineup validation, team inning totals, team game totals, and maximum team score in `android/app/src/test/java/com/rbdarts/core/domain/BaseballDartsRulesTest.kt`
- [x] T039 [P] [US2] Add iOS unit tests for individual participants, team lineup validation, team inning totals, team game totals, and maximum team score in `ios/RBDartsTests/Domain/ScoringRulesTests.swift`
- [x] T040 [P] [US2] Add shared team scoring fixture with three-player and four-player examples in `shared-contracts/fixtures/scoring/baseball-team-scoring.json`
- [ ] T041 [P] [US2] Add Android match setup UI test for individual/team mode and batting order in `android/app/src/androidTest/java/com/rbdarts/feature/matchscoring/BaseballTeamSetupFlowTest.kt`
- [ ] T042 [P] [US2] Add iOS match setup XCTest for individual/team mode and batting order in `ios/RBDartsTests/MatchScoring/BaseballTeamSetupFlowTests.swift`

### Implementation for User Story 2

- [x] T043 [US2] Implement Kotlin participant/team lineup validation and team aggregation rules in `android/app/src/main/java/com/rbdarts/core/domain/ScoringRules.kt`
- [x] T044 [US2] Implement Swift participant/team lineup validation and team aggregation rules in `ios/RBDarts/Shared/Domain/ScoringRules.swift`
- [ ] T045 [US2] Update Android game setup state for individual/team mode, team roster, and batting order in `android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameViewModel.kt`
- [ ] T046 [US2] Update Android standalone setup screen for individual/team mode, team roster, and batting order in `android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameSetupScreen.kt`
- [ ] T047 [US2] Update Android league match setup to preserve team batting order and team participant state in `android/app/src/main/java/com/rbdarts/feature/matchscoring/MatchSetupScreen.kt`
- [ ] T048 [US2] Update Android league score entry to advance through team batting order and show team totals in `android/app/src/main/java/com/rbdarts/feature/matchscoring/LeagueScoreEntryScreen.kt`
- [ ] T049 [US2] Update Android totals and scorecard panels for individual and team totals in `android/app/src/main/java/com/rbdarts/feature/scoring/ScoreTotalsPanel.kt`
- [ ] T050 [US2] Update Android scorecard review panel for individual and team inning totals in `android/app/src/main/java/com/rbdarts/feature/scoring/ScorecardReviewPanel.kt`
- [ ] T051 [US2] Update Swift standalone setup and ViewModel for individual/team mode, team roster, and batting order in `ios/RBDarts/Features/StandaloneGame/StandaloneGameViewModel.swift`
- [ ] T052 [US2] Update Swift standalone setup view for individual/team mode, team roster, and batting order in `ios/RBDarts/Features/StandaloneGame/StandaloneGameSetupView.swift`
- [ ] T053 [US2] Update Swift league score entry for team batting order and team totals in `ios/RBDarts/Features/MatchScoring/LeagueScoreEntryView.swift`
- [ ] T054 [US2] Update Android roster/team service validation for Baseball Darts team constraints in `android/app/src/main/java/com/rbdarts/feature/league/LeagueService.kt`
- [ ] T055 [US2] Update Swift roster/team service validation for Baseball Darts team constraints in `ios/RBDarts/Features/League/LeagueService.swift`

**Checkpoint**: User Stories 1 and 2 are independently usable for individual and team games.

---

## Phase 5: User Story 3 - Resolve Ties With Configurable Extra Innings (Priority: P2)

**Goal**: Continue tied games into extra innings with target numbers 10 through 20, equal-turn completion, and configurable post-20 behavior.

**Independent Test**: Complete a tied 9-inning game, score the 10th inning for both sides, and verify a winner is declared only after equal turns or the game advances when still tied.

### Tests for User Story 3

- [x] T056 [P] [US3] Add Android unit tests for tied regulation, equal-turn completion, post-20 repeat-20, restart-at-1, and bullseye tiebreaker targets in `android/app/src/test/java/com/rbdarts/core/domain/BaseballDartsRulesTest.kt`
- [x] T057 [P] [US3] Add iOS unit tests for tied regulation, equal-turn completion, post-20 repeat-20, restart-at-1, and bullseye tiebreaker targets in `ios/RBDartsTests/Domain/ScoringRulesTests.swift`
- [x] T058 [P] [US3] Add shared extra-inning repeat-20 fixture in `shared-contracts/fixtures/scoring/baseball-extra-innings-repeat20.json`
- [x] T059 [P] [US3] Add shared restart-at-1 and bullseye tiebreaker fixtures in `shared-contracts/fixtures/scoring/baseball-post20-tiebreakers.json`
- [ ] T060 [P] [US3] Add Android UI test for extra inning target and no premature winner state in `android/app/src/androidTest/java/com/rbdarts/feature/scoring/BaseballExtraInningsFlowTest.kt`
- [ ] T061 [P] [US3] Add iOS UI/XCTest coverage for extra inning target and no premature winner state in `ios/RBDartsTests/StandaloneGame/BaseballExtraInningsFlowTests.swift`

### Implementation for User Story 3

- [x] T062 [US3] Implement Kotlin extra inning state, equal-turn gate, post-20 repeat-20, restart-at-1, and bullseye target resolution in `android/app/src/main/java/com/rbdarts/core/domain/ScoringRules.kt`
- [x] T063 [US3] Implement Swift extra inning state, equal-turn gate, post-20 repeat-20, restart-at-1, and bullseye target resolution in `ios/RBDarts/Shared/Domain/ScoringRules.swift`
- [x] T064 [US3] Update Android scoring state to display extra inning target and equal-turn status in `android/app/src/main/java/com/rbdarts/core/ui/AppUiStates.kt`
- [x] T065 [US3] Update Android scoring ViewModel to advance extra innings only after all sides complete the inning in `android/app/src/main/java/com/rbdarts/feature/scoring/ScoringViewModel.kt`
- [ ] T066 [US3] Update Android league settings for post-20 tiebreaker rule selection in `android/app/src/main/java/com/rbdarts/feature/league/LeagueSettingsScreen.kt`
- [ ] T067 [US3] Update Swift league settings for post-20 tiebreaker rule selection in `ios/RBDarts/Features/League/LeagueSettingsView.swift`
- [ ] T068 [US3] Update Android standalone scorecard and summary screens to present extra innings and post-20 target state in `android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneScorecardScreen.kt`
- [ ] T069 [US3] Update Swift standalone scorecard and summary views to present extra innings and post-20 target state in `ios/RBDarts/Features/StandaloneGame/StandaloneScorecardView.swift`

**Checkpoint**: User Stories 1, 2, and 3 support fair tiebreaking through configured extra innings.

---

## Phase 6: User Story 4 - Manage Official League Corrections, Locks, Substitutions, And Roles (Priority: P3)

**Goal**: Enforce official league authorization for score corrections, game locking, unlocking, substitutions, and audit history.

**Independent Test**: Complete and lock a league game, verify player edits are blocked, then perform an authorized league manager correction with audit details and recalculated totals.

### Tests for User Story 4

- [x] T070 [P] [US4] Add Android unit tests for correction reason, role permissions, locked-game rejection, manager correction, audit fields, and recalculation in `android/app/src/test/java/com/rbdarts/core/domain/BaseballDartsRulesTest.kt`
- [x] T071 [P] [US4] Add iOS unit tests for correction reason, role permissions, locked-game rejection, manager correction, audit fields, and recalculation in `ios/RBDartsTests/Domain/ScoringRulesTests.swift`
- [ ] T072 [P] [US4] Add Android unit tests for pre-game and mid-game substitution score ownership in `android/app/src/test/java/com/rbdarts/feature/league/BaseballSubstitutionRulesTest.kt`
- [ ] T073 [P] [US4] Add iOS unit tests for pre-game and mid-game substitution score ownership in `ios/RBDartsTests/League/BaseballSubstitutionRulesTests.swift`
- [x] T074 [P] [US4] Add shared locked correction and substitution fixtures in `shared-contracts/fixtures/corrections/baseball-correction-and-substitution.json`
- [ ] T075 [P] [US4] Add Android UI test for locked game edit denial and manager correction flow in `android/app/src/androidTest/java/com/rbdarts/feature/corrections/BaseballCorrectionFlowTest.kt`
- [ ] T076 [P] [US4] Add iOS UI/XCTest coverage for locked game edit denial and manager correction flow in `ios/RBDartsTests/Corrections/BaseballCorrectionFlowTests.swift`

### Implementation for User Story 4

- [x] T077 [US4] Implement Kotlin correction authorization, lock policy, unlock policy, substitution policy, and recalculation rules in `android/app/src/main/java/com/rbdarts/core/domain/ScoringRules.kt`
- [x] T078 [US4] Implement Swift correction authorization, lock policy, unlock policy, substitution policy, and recalculation rules in `ios/RBDarts/Shared/Domain/ScoringRules.swift`
- [ ] T079 [US4] Update Android correction workflow service for dart-level source records, audit entries, and role checks in `android/app/src/main/java/com/rbdarts/feature/corrections/CorrectionService.kt`
- [ ] T080 [US4] Update Swift correction workflow service for dart-level source records, audit entries, and role checks in `ios/RBDarts/Features/Corrections/CorrectionService.swift`
- [ ] T081 [US4] Update Android correction workflow screen for locked-game denial, manager correction, reason capture, and audit summary in `android/app/src/main/java/com/rbdarts/feature/corrections/CorrectionWorkflowScreen.kt`
- [ ] T082 [US4] Update Swift correction workflow view for locked-game denial, manager correction, reason capture, and audit summary in `ios/RBDarts/Features/Corrections/CorrectionWorkflowView.swift`
- [ ] T083 [US4] Add Android substitution support in league roster and match scoring services in `android/app/src/main/java/com/rbdarts/feature/league/TeamRosterScreen.kt`
- [ ] T084 [US4] Add Swift substitution support in league roster and match scoring views in `ios/RBDarts/Features/League/TeamRosterView.swift`
- [ ] T085 [US4] Emit privacy-safe correction, lock, unlock, and substitution diagnostics in `android/app/src/main/java/com/rbdarts/feature/corrections/CorrectionDiagnostics.kt`
- [ ] T086 [US4] Emit privacy-safe correction, lock, unlock, and substitution diagnostics in `ios/RBDarts/Features/Corrections/CorrectionDiagnostics.swift`

**Checkpoint**: Official league edits are role-gated, auditable, and recalculated from source records.

---

## Phase 7: User Story 5 - Present Scoreboards, Summaries, And Analytics (Priority: P4)

**Goal**: Present live scoreboards, final summaries, and derived analytics for active and historical Baseball Darts games.

**Independent Test**: Score a partial and completed game, then verify current inning, target, thrower, totals, leader, margin, remaining innings, final scorecard, and analytics values.

### Tests for User Story 5

- [x] T087 [P] [US5] Add Android unit tests for live scoreboard snapshot, leader, margin, remaining innings, and status text in `android/app/src/test/java/com/rbdarts/core/domain/BaseballDartsRulesTest.kt`
- [x] T088 [P] [US5] Add iOS unit tests for live scoreboard snapshot, leader, margin, remaining innings, and status text in `ios/RBDartsTests/Domain/ScoringRulesTests.swift`
- [x] T089 [P] [US5] Add Android stats tests for average score, team average, highest inning, shutout innings, triple rate, win percentage, projection, consistency, best target, and weakest target in `android/app/src/test/java/com/rbdarts/core/domain/BaseballDartsRulesTest.kt`
- [x] T090 [P] [US5] Add iOS stats tests for average score, team average, highest inning, shutout innings, triple rate, win percentage, projection, consistency, best target, and weakest target in `ios/RBDartsTests/Domain/ScoringRulesTests.swift`
- [ ] T091 [P] [US5] Add Android UI test for live scoreboard and final summary rendering in `android/app/src/androidTest/java/com/rbdarts/feature/scoring/BaseballScoreboardRenderingTest.kt`
- [ ] T092 [P] [US5] Add iOS UI/XCTest coverage for live scoreboard and final summary rendering in `ios/RBDartsTests/Stats/BaseballScoreboardRenderingTests.swift`
- [ ] T093 [P] [US5] Add Android performance smoke test for scoreboard update and single-game recalculation budgets in `android/app/src/test/java/com/rbdarts/feature/scoring/BaseballScoringPerformanceTest.kt`

### Implementation for User Story 5

- [x] T094 [US5] Implement Kotlin scoreboard snapshot and live status derivation in `android/app/src/main/java/com/rbdarts/core/ui/AppUiStates.kt`
- [x] T095 [US5] Implement Swift scoreboard snapshot and live status derivation in `ios/RBDarts/Shared/Domain/ScoringRules.swift`
- [x] T096 [US5] Update Android scoring context header for current inning, target, thrower, leader, margin, and remaining innings in `android/app/src/main/java/com/rbdarts/feature/scoring/ScoringContextHeader.kt`
- [ ] T097 [US5] Update Android scoring totals panel for player/team totals, inning totals, and leader state in `android/app/src/main/java/com/rbdarts/feature/scoring/ScoreTotalsPanel.kt`
- [ ] T098 [US5] Update Android standalone summary screen for final score, winner, margin, extra innings, and corrected-score indicators in `android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameSummaryScreen.kt`
- [ ] T099 [US5] Update Swift standalone summary view for final score, winner, margin, extra innings, and corrected-score indicators in `ios/RBDarts/Features/StandaloneGame/StandaloneGameSummaryView.swift`
- [ ] T100 [US5] Implement Android Baseball Darts analytics calculations in `android/app/src/main/java/com/rbdarts/feature/stats/StatsService.kt`
- [ ] T101 [US5] Implement Swift Baseball Darts analytics calculations in `ios/RBDarts/Features/Stats/StatsService.swift`
- [ ] T102 [US5] Update Android projection service for estimate labeling and insufficient-data states in `android/app/src/main/java/com/rbdarts/feature/stats/ProjectionService.kt`
- [ ] T103 [US5] Update Swift projection service for estimate labeling and insufficient-data states in `ios/RBDarts/Features/Stats/ProjectionService.swift`
- [ ] T104 [US5] Update Android stats dashboard with Baseball Darts metrics and privacy-safe labels in `android/app/src/main/java/com/rbdarts/feature/stats/StatsDashboardScreen.kt`
- [ ] T105 [US5] Update Swift stats dashboard with Baseball Darts metrics and privacy-safe labels in `ios/RBDarts/Features/Stats/StatsDashboardView.swift`
- [ ] T106 [US5] Emit privacy-safe stats and projection diagnostics in `android/app/src/main/java/com/rbdarts/feature/stats/StatsDiagnostics.kt`
- [ ] T107 [US5] Emit privacy-safe stats and projection diagnostics in `ios/RBDarts/Features/Stats/StatsDiagnostics.swift`

**Checkpoint**: Live scoring, final summaries, and analytics reflect the revamped rule model.

---

## Phase 8: Polish & Cross-Cutting Concerns

**Purpose**: Validate parity, security, persistence recovery, performance, Android/iOS builds, and documentation.

- [x] T108 [P] Update launch and verification notes for Baseball Darts rules in `README.md`
- [x] T109 [P] Update feature quickstart with final validated command results in `specs/007-baseball-darts-rules/quickstart.md`
- [x] T110 [P] Review scoring/correction diagnostics for privacy compliance and record results in `specs/007-baseball-darts-rules/security-threat-model.md`
- [x] T111 Run shared fixture/schema review and record coverage gaps in `specs/007-baseball-darts-rules/fixture-plan.md`
- [x] T112 Run Android unit tests with `cd android && ./gradlew testDebugUnitTest` and record result in `specs/007-baseball-darts-rules/verification.md`
- [x] T113 Run Android UI test source compilation with `cd android && ./gradlew compileDebugAndroidTestKotlin` and record result in `specs/007-baseball-darts-rules/verification.md`
- [x] T114 Run Android lint with `cd android && ./gradlew lintDebug` and record result in `specs/007-baseball-darts-rules/verification.md`
- [x] T115 Build debug APK with `cd android && ./gradlew assembleDebug` and record result in `specs/007-baseball-darts-rules/verification.md`
- [ ] T116 Run iOS tests with `xcodebuild test -project ios/RBDarts.xcodeproj -scheme RBDarts -destination 'platform=iOS Simulator,name=iPhone 16'` and record result in `specs/007-baseball-darts-rules/verification.md`
- [ ] T117 Validate app restart or process recreation recovery for an active scoring turn and record evidence in `specs/007-baseball-darts-rules/performance-recovery-checklist.md`
- [x] T118 Validate scoring update and single-game recalculation performance budgets and record evidence in `specs/007-baseball-darts-rules/performance-recovery-checklist.md`
- [x] T119 Review requirements against completed implementation and update `specs/007-baseball-darts-rules/checklists/requirements.md`
- [ ] T120 Confirm all tasks are complete and mark checklist state in `specs/007-baseball-darts-rules/tasks.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies.
- **Foundational (Phase 2)**: Depends on Setup and blocks all user stories.
- **US1 Standard Baseball Darts Scoring (Phase 3)**: Depends on Foundational and is the MVP.
- **US2 Individual And Team Play (Phase 4)**: Depends on Foundational and core US1 scoring rules.
- **US3 Extra Innings (Phase 5)**: Depends on US1 scoring and US2 participant/team completion rules.
- **US4 League Governance (Phase 6)**: Depends on US1 source scoring records and US2/US3 recalculation behavior.
- **US5 Scoreboards And Analytics (Phase 7)**: Depends on reliable source records from US1-US4.
- **Polish (Phase 8)**: Depends on all desired user stories.

### User Story Dependencies

- **US1 - Score a standard Baseball Darts game**: Can be implemented after Foundation only.
- **US2 - Support individual and team play**: Requires US1's dart-level turn model and can validate independently after that.
- **US3 - Resolve ties with configurable extra innings**: Requires US1 totals and US2 equal participant/team turn completion.
- **US4 - Manage official league corrections, locks, substitutions, and roles**: Requires source records and recalculation hooks from US1-US3.
- **US5 - Present scoreboards, summaries, and analytics**: Requires completed scoring, team, extra-inning, and correction behaviors for full coverage.

### Within Each User Story

- Write and run the listed tests first; they should fail before implementation.
- Implement Kotlin and Swift domain rules before platform UI wiring.
- Implement source records before derived totals.
- Implement services before screen integration.
- Implement diagnostics with no secrets, raw provider payloads, private notes, or free-form correction reasons in logs.
- Validate each user story independently before moving to the next priority.

### Parallel Opportunities

- T002-T004 can run in parallel after T001.
- T006-T012 can run in parallel after Setup.
- T015-T016 can run in parallel after model naming is agreed.
- US1 tests T022-T026 can run in parallel.
- US2 tests T038-T042 can run in parallel.
- US3 tests T056-T061 can run in parallel.
- US4 tests T070-T076 can run in parallel.
- US5 tests T087-T093 can run in parallel.
- T108-T110 can run in parallel after feature implementation stabilizes.

---

## Parallel Example: User Story 1

```text
Task: T022 BaseballScoringRulesTest.kt
Task: T023 BaseballScoringRulesTests.swift
Task: T024 baseball-standard-nine-innings.json
Task: T025 BaseballStandaloneScoringFlowTest.kt
Task: T026 BaseballStandaloneScoringFlowTests.swift
```

---

## Implementation Strategy

### MVP First (User Story 1)

1. Complete Setup.
2. Complete Foundational models, schemas, fixtures, diagnostics, and persistence hooks.
3. Complete US1 tests and standard scoring implementation.
4. Validate a two-player regulation game from inning 1 through inning 9.
5. Stop and demo standard game scoring before layering team, extra-inning, and league governance behavior.

### Incremental Delivery

1. Foundation -> source-of-truth models, contracts, parity fixtures, and diagnostics.
2. US1 -> dart-level standard two-player scoring.
3. US2 -> team scoring, batting order, team totals, and max team score.
4. US3 -> fair extra innings and configurable post-20 tiebreakers.
5. US4 -> official corrections, locking, substitutions, roles, and audit history.
6. US5 -> live scoreboards, final summaries, analytics, and performance checks.
7. Polish -> Android/iOS verification, recovery validation, docs, and requirements review.

### Scope Guardrails

- Do not add a cross-platform production rules runtime; keep Kotlin and Swift native and verify parity through fixtures.
- Do not add new SSO providers or first-party password handling in this feature.
- Do not log tokens, raw identity provider payloads, private notes, or unnecessary personal data.
- Do not allow direct edits to locked official games outside the authorized correction workflow.
- Do not make broad visual redesign changes beyond the controls needed to express the revamped rules clearly.
