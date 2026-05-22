# Tasks: Baseball Darts Mobile App

**Input**: Design documents from `/specs/001-baseball-darts-app/`

**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/, quickstart.md

**Tests**: Required for security-sensitive, authentication, scoring-critical, persistence, performance, stability, and major user journey behavior.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel because it touches different files and does not depend on incomplete tasks.
- **[Story]**: Which user story this task belongs to, only used in user story phases.
- Every task includes an exact file path.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Create the native app, Firebase, and shared-contract workspace shape from the plan.

- [X] T001 Create repository structure directories in ios/, android/, firebase/, and shared-contracts/
- [X] T002 Scaffold iOS app project files in ios/RBDarts.xcodeproj/project.pbxproj and ios/RBDarts/App/RBDartsApp.swift
- [X] T003 Scaffold Android Gradle project files in android/settings.gradle.kts, android/build.gradle.kts, android/app/build.gradle.kts, and android/app/src/main/AndroidManifest.xml
- [X] T004 Scaffold Firebase project files in firebase/firebase.json, firebase/firestore.rules, firebase/firestore.indexes.json, firebase/functions/package.json, and firebase/functions/src/index.ts
- [X] T005 [P] Create iOS feature and shared module folders under ios/RBDarts/Features/ and ios/RBDarts/Shared/
- [X] T006 [P] Create Android feature and core package folders under android/app/src/main/java/com/rbdarts/feature/ and android/app/src/main/java/com/rbdarts/core/
- [X] T007 [P] Create test target folders in ios/RBDartsTests/, ios/RBDartsUITests/, android/app/src/test/java/com/rbdarts/, and android/app/src/androidTest/java/com/rbdarts/
- [X] T008 [P] Create shared contract fixture folders in shared-contracts/fixtures/scoring/, shared-contracts/fixtures/handicap/, shared-contracts/fixtures/locking/, shared-contracts/fixtures/corrections/, and shared-contracts/fixtures/standings/
- [X] T009 [P] Add non-sensitive environment templates in firebase/.env.example, ios/RBDarts/Resources/FirebaseConfig.example.plist, and android/app/src/main/res/values/firebase_config_example.xml
- [X] T010 Add secret and generated-output ignore patterns in .gitignore
- [X] T011 [P] Configure iOS dependency placeholders for Firebase/Auth/Firestore/Crashlytics/Performance and Facebook Login in ios/RBDarts.xcodeproj/project.pbxproj
- [X] T012 [P] Configure Android dependencies for Compose, Lifecycle, Hilt, Room, DataStore, Firebase, Credential Manager, and Facebook Login in android/app/build.gradle.kts
- [X] T013 [P] Configure Firebase Functions TypeScript tooling in firebase/functions/tsconfig.json and firebase/functions/src/index.ts
- [X] T014 Document local setup assumptions in README.md

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Shared domain, security, persistence, observability, and contract test foundations that every story needs.

**CRITICAL**: No user story work can begin until this phase is complete.

- [X] T015 Create threat model and data classification notes in specs/001-baseball-darts-app/security-threat-model.md
- [X] T016 [P] Create scoring fixture schema in shared-contracts/schemas/scoring-fixture.schema.json
- [X] T017 [P] Create role and permission fixture schema in shared-contracts/schemas/security-rule-fixture.schema.json
- [X] T018 [P] Create baseline scoring fixtures in shared-contracts/fixtures/scoring/standalone-basic.json and shared-contracts/fixtures/scoring/extra-innings.json
- [X] T019 [P] Create baseline handicap fixtures in shared-contracts/fixtures/handicap/handicap-rounding.json
- [X] T020 [P] Create baseline locking and correction fixtures in shared-contracts/fixtures/locking/lock-prior-game.json and shared-contracts/fixtures/corrections/apply-correction.json
- [X] T021 [P] Create iOS domain models for UserAccount, Player, League, Team, Match, Game, GameLineup, InningScore, PracticeAttempt, and CorrectionAuditRecord in ios/RBDarts/Shared/Domain/Models.swift
- [X] T022 [P] Create Android domain models for UserAccount, Player, League, Team, Match, Game, GameLineup, InningScore, PracticeAttempt, and CorrectionAuditRecord in android/app/src/main/java/com/rbdarts/core/domain/Models.kt
- [X] T023 [P] Create Firebase TypeScript domain types in firebase/functions/src/domain/types.ts
- [X] T024 [P] Create iOS scoring calculator tests from shared fixtures in ios/RBDartsTests/Domain/ScoringRulesTests.swift
- [X] T025 [P] Create Android scoring calculator tests from shared fixtures in android/app/src/test/java/com/rbdarts/core/domain/ScoringRulesTest.kt
- [X] T026 [P] Create Firebase Functions scoring calculator tests from shared fixtures in firebase/functions/test/scoringRules.test.ts
- [X] T027 Implement iOS scoring, extra innings, totals, handicap, locking, standings, and projection rules in ios/RBDarts/Shared/Domain/ScoringRules.swift
- [X] T028 Implement Android scoring, extra innings, totals, handicap, locking, standings, and projection rules in android/app/src/main/java/com/rbdarts/core/domain/ScoringRules.kt
- [X] T029 Implement Firebase trusted scoring, handicap, locking, correction, and standings rules in firebase/functions/src/domain/scoringRules.ts
- [X] T030 [P] Create iOS secure storage wrapper for auth/session metadata in ios/RBDarts/Shared/Security/SecureSessionStore.swift
- [X] T031 [P] Create Android secure storage and session interfaces in android/app/src/main/java/com/rbdarts/core/security/SecureSessionStore.kt
- [X] T032 [P] Create Firebase Auth provider abstractions for Google, Facebook, and Apple in ios/RBDarts/Features/Auth/AuthProvider.swift and android/app/src/main/java/com/rbdarts/feature/auth/AuthProvider.kt
- [X] T033 [P] Create iOS active scoring local persistence interfaces in ios/RBDarts/Shared/Data/ActiveScoreSessionStore.swift
- [X] T034 [P] Create Android Room active scoring database interfaces in android/app/src/main/java/com/rbdarts/core/data/local/ActiveScoreDatabase.kt
- [X] T035 [P] Create Firebase repository interfaces for leagues, matches, games, practice, stats, and corrections in ios/RBDarts/Shared/Data/RBDartsRepository.swift and android/app/src/main/java/com/rbdarts/core/data/RBDartsRepository.kt
- [X] T036 [P] Create Firebase callable function stubs for trusted operations in firebase/functions/src/trustedOperations.ts
- [X] T037 [P] Implement default-deny Firestore rules skeleton in firebase/firestore.rules
- [X] T038 [P] Create Firestore security rule tests for unauthenticated access and cross-league denial in firebase/functions/test/firestoreRules.test.ts
- [X] T039 [P] Create privacy-safe observability wrappers in ios/RBDarts/Shared/Observability/Diagnostics.swift and android/app/src/main/java/com/rbdarts/core/observability/Diagnostics.kt
- [X] T040 [P] Create shared UI design tokens for score entry, status, and validation in ios/RBDarts/Shared/DesignSystem/DesignTokens.swift and android/app/src/main/java/com/rbdarts/core/designsystem/DesignTokens.kt

**Checkpoint**: Foundation ready. User story implementation can now begin.

## Phase 3: User Story 1 - Keep a Standalone Game Score (Priority: P1)

**Goal**: Create and complete a standalone Baseball Darts game with live score entry, validation, totals, extra innings, history, and final scorecard.

**Independent Test**: Create a 9-inning standalone game with players or teams, enter scores from 0 through 9, verify live totals, complete the game, and view the saved final scorecard.

### Tests for User Story 1

- [X] T041 [P] [US1] Add iOS unit tests for standalone game setup, score validation, totals, extra innings, and final summary in ios/RBDartsTests/StandaloneGame/StandaloneGameTests.swift
- [X] T042 [P] [US1] Add Android unit tests for standalone game setup, score validation, totals, extra innings, and final summary in android/app/src/test/java/com/rbdarts/feature/standalonegame/StandaloneGameTest.kt
- [X] T043 [P] [US1] Add Firebase rules/function tests for standalone score ownership and invalid score rejection in firebase/functions/test/standaloneGame.test.ts
- [X] T044 [P] [US1] Add iOS UI test for creating and completing a standalone game in ios/RBDartsUITests/StandaloneGameFlowTests.swift
- [X] T045 [P] [US1] Add Android Compose UI test for creating and completing a standalone game in android/app/src/androidTest/java/com/rbdarts/feature/standalonegame/StandaloneGameFlowTest.kt

### Implementation for User Story 1

- [X] T046 [P] [US1] Implement iOS standalone game domain service in ios/RBDarts/Features/StandaloneGame/StandaloneGameService.swift
- [X] T047 [P] [US1] Implement Android standalone game domain service in android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameService.kt
- [X] T048 [P] [US1] Implement Firebase standalone game persistence operations in firebase/functions/src/standaloneGame.ts
- [X] T049 [P] [US1] Implement iOS standalone game setup screen in ios/RBDarts/Features/StandaloneGame/StandaloneGameSetupView.swift
- [X] T050 [P] [US1] Implement Android standalone game setup screen in android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameSetupScreen.kt
- [X] T051 [P] [US1] Implement iOS one-inning score entry screen with 0-9 controls and quick undo in ios/RBDarts/Features/StandaloneGame/StandaloneScoreEntryView.swift
- [X] T052 [P] [US1] Implement Android one-inning score entry screen with 0-9 controls and quick undo in android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneScoreEntryScreen.kt
- [X] T053 [P] [US1] Implement iOS live scoreboard state and scorecard view in ios/RBDarts/Features/StandaloneGame/StandaloneScorecardView.swift
- [X] T054 [P] [US1] Implement Android live scoreboard state and scorecard screen in android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneScorecardScreen.kt
- [X] T055 [US1] Integrate active standalone score persistence and recovery on iOS in ios/RBDarts/Features/StandaloneGame/StandaloneGameViewModel.swift
- [X] T056 [US1] Integrate active standalone score persistence and recovery on Android in android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameViewModel.kt
- [X] T057 [US1] Implement iOS standalone game summary and history entry in ios/RBDarts/Features/StandaloneGame/StandaloneGameSummaryView.swift
- [X] T058 [US1] Implement Android standalone game summary and history entry in android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameSummaryScreen.kt
- [X] T059 [US1] Add privacy-safe diagnostics for standalone invalid scores, recovery events, and completion in ios/RBDarts/Features/StandaloneGame/StandaloneGameDiagnostics.swift and android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameDiagnostics.kt
- [X] T060 [US1] Verify standalone score entry updates within 1 second and record results in specs/001-baseball-darts-app/validation/standalone-performance.md

**Checkpoint**: User Story 1 is functional and testable independently as the MVP.

## Phase 4: User Story 2 - Run League Matches With Handicaps (Priority: P2)

**Goal**: Configure leagues, teams, players, matches, lineups, handicaps, game locking, match points, finalization, standings, and official averages.

**Independent Test**: Configure a league, create two teams with players and seed averages, create a multi-game match, select lineups, score the match, verify handicaps, lock prior games, finalize the match, and confirm standings update.

### Tests for User Story 2

- [X] T061 [P] [US2] Add iOS unit tests for league settings, handicap calculation, lineup average snapshots, game locking, match points, standings, and average updates in ios/RBDartsTests/League/LeagueMatchTests.swift
- [X] T062 [P] [US2] Add Android unit tests for league settings, handicap calculation, lineup average snapshots, game locking, match points, standings, and average updates in android/app/src/test/java/com/rbdarts/feature/league/LeagueMatchTest.kt
- [X] T063 [P] [US2] Add Firebase function tests for createLeague, createMatch, startGame, submitInningScores, completeGame, and finalizeMatch in firebase/functions/test/leagueMatch.test.ts
- [X] T064 [P] [US2] Add Firestore security rule tests for League Manager, Team Manager, Scorekeeper, Player, Viewer, and cross-league permissions in firebase/functions/test/leagueRules.test.ts
- [X] T065 [P] [US2] Add iOS UI test for league match scoring and prior-game locking in ios/RBDartsUITests/LeagueMatchFlowTests.swift
- [X] T066 [P] [US2] Add Android Compose UI test for league match scoring and prior-game locking in android/app/src/androidTest/java/com/rbdarts/feature/league/LeagueMatchFlowTest.kt

### Implementation for User Story 2

- [X] T067 [P] [US2] Implement iOS league management service in ios/RBDarts/Features/League/LeagueService.swift
- [X] T068 [P] [US2] Implement Android league management service in android/app/src/main/java/com/rbdarts/feature/league/LeagueService.kt
- [X] T069 [P] [US2] Implement Firebase createLeague and role-assignment operations in firebase/functions/src/leagueOperations.ts
- [X] T070 [P] [US2] Implement iOS league creation and settings screens in ios/RBDarts/Features/League/LeagueSettingsView.swift
- [X] T071 [P] [US2] Implement Android league creation and settings screens in android/app/src/main/java/com/rbdarts/feature/league/LeagueSettingsScreen.kt
- [X] T072 [P] [US2] Implement iOS team, player, roster, and seed average management in ios/RBDarts/Features/League/TeamRosterView.swift
- [X] T073 [P] [US2] Implement Android team, player, roster, and seed average management in android/app/src/main/java/com/rbdarts/feature/league/TeamRosterScreen.kt
- [X] T074 [P] [US2] Implement Firebase match creation, lineup validation, average snapshots, and handicap calculation in firebase/functions/src/matchOperations.ts
- [X] T075 [P] [US2] Implement iOS match creation and lineup selection screens in ios/RBDarts/Features/MatchScoring/MatchSetupView.swift
- [X] T076 [P] [US2] Implement Android match creation and lineup selection screens in android/app/src/main/java/com/rbdarts/feature/matchscoring/MatchSetupScreen.kt
- [X] T077 [P] [US2] Implement iOS league match score entry, handicap display, game points, and lock warnings in ios/RBDarts/Features/MatchScoring/LeagueScoreEntryView.swift
- [X] T078 [P] [US2] Implement Android league match score entry, handicap display, game points, and lock warnings in android/app/src/main/java/com/rbdarts/feature/matchscoring/LeagueScoreEntryScreen.kt
- [X] T079 [US2] Implement trusted game start, prior-game lock, complete game, and finalize match functions in firebase/functions/src/matchFinalization.ts
- [X] T080 [US2] Implement iOS standings and official average updates after match finalization in ios/RBDarts/Features/League/StandingsView.swift
- [X] T081 [US2] Implement Android standings and official average updates after match finalization in android/app/src/main/java/com/rbdarts/feature/league/StandingsScreen.kt
- [X] T082 [US2] Harden Firestore rules for league, team, match, game, inning score, role, and stats collections in firebase/firestore.rules
- [X] T083 [US2] Add privacy-safe diagnostics for league match permission denial, lock transitions, finalize failures, and sync recovery in ios/RBDarts/Features/MatchScoring/LeagueMatchDiagnostics.swift and android/app/src/main/java/com/rbdarts/feature/matchscoring/LeagueMatchDiagnostics.kt
- [X] T084 [US2] Validate quickstart league match scenario and record results in specs/001-baseball-darts-app/validation/league-match.md

**Checkpoint**: User Stories 1 and 2 work independently and together.

## Phase 5: User Story 3 - Correct Locked Game Mistakes (Priority: P3)

**Goal**: Provide authorized correction request, approval/application, immutable audit trail, and downstream recalculation for locked or finalized games.

**Independent Test**: Lock a game, submit or perform a correction with a reason, verify audit record creation, and confirm affected totals, standings, and statistics are recalculated.

### Tests for User Story 3

- [X] T085 [P] [US3] Add iOS unit tests for correction request, approval state, audit record contents, and recalculation effects in ios/RBDartsTests/Corrections/CorrectionWorkflowTests.swift
- [X] T086 [P] [US3] Add Android unit tests for correction request, approval state, audit record contents, and recalculation effects in android/app/src/test/java/com/rbdarts/feature/corrections/CorrectionWorkflowTest.kt
- [X] T087 [P] [US3] Add Firebase function tests for requestCorrection and applyCorrection in firebase/functions/test/corrections.test.ts
- [X] T088 [P] [US3] Add Firestore security rule tests for Team Manager correction requests and League Manager correction application in firebase/functions/test/correctionRules.test.ts
- [X] T089 [P] [US3] Add iOS UI test for submitting and applying a locked-game correction in ios/RBDartsUITests/CorrectionFlowTests.swift
- [X] T090 [P] [US3] Add Android Compose UI test for submitting and applying a locked-game correction in android/app/src/androidTest/java/com/rbdarts/feature/corrections/CorrectionFlowTest.kt

### Implementation for User Story 3

- [X] T091 [P] [US3] Implement Firebase correction request, approval, immutable audit, and recalculation pipeline in firebase/functions/src/correctionOperations.ts
- [X] T092 [P] [US3] Implement iOS correction workflow service in ios/RBDarts/Features/Corrections/CorrectionService.swift
- [X] T093 [P] [US3] Implement Android correction workflow service in android/app/src/main/java/com/rbdarts/feature/corrections/CorrectionService.kt
- [X] T094 [P] [US3] Implement iOS correction request and approval views in ios/RBDarts/Features/Corrections/CorrectionWorkflowView.swift
- [X] T095 [P] [US3] Implement Android correction request and approval screens in android/app/src/main/java/com/rbdarts/feature/corrections/CorrectionWorkflowScreen.kt
- [X] T096 [US3] Implement recalculation of game summary, match summary, player stats, team stats, standings, and analytics after correction in firebase/functions/src/recalculation.ts
- [X] T097 [US3] Harden correction audit immutability and derived-stat write restrictions in firebase/firestore.rules
- [X] T098 [US3] Add privacy-safe diagnostics for correction requests, approvals, denials, and recalculation failures in ios/RBDarts/Features/Corrections/CorrectionDiagnostics.swift and android/app/src/main/java/com/rbdarts/feature/corrections/CorrectionDiagnostics.kt
- [X] T099 [US3] Validate quickstart locked correction scenario and record results in specs/001-baseball-darts-app/validation/corrections.md

**Checkpoint**: Locked official records can be corrected transparently without bypassing audit or recalculation.

## Phase 6: User Story 4 - Practice Targets Independently (Priority: P4)

**Goal**: Let players practice targets and view practice statistics while keeping practice data isolated from official league records.

**Independent Test**: Select a player and target, enter practice scores, view practice stats, and verify official league averages and standings do not change.

### Tests for User Story 4

- [X] T100 [P] [US4] Add iOS unit tests for practice score validation, target stats, and official-stat isolation in ios/RBDartsTests/Practice/PracticeModeTests.swift
- [X] T101 [P] [US4] Add Android unit tests for practice score validation, target stats, and official-stat isolation in android/app/src/test/java/com/rbdarts/feature/practice/PracticeModeTest.kt
- [X] T102 [P] [US4] Add Firebase tests for practiceAttempts ownership and official-stat isolation in firebase/functions/test/practice.test.ts
- [X] T103 [P] [US4] Add iOS UI test for practice target entry and stats review in ios/RBDartsUITests/PracticeFlowTests.swift
- [X] T104 [P] [US4] Add Android Compose UI test for practice target entry and stats review in android/app/src/androidTest/java/com/rbdarts/feature/practice/PracticeFlowTest.kt

### Implementation for User Story 4

- [X] T105 [P] [US4] Implement iOS practice domain service and target statistics in ios/RBDarts/Features/Practice/PracticeService.swift
- [X] T106 [P] [US4] Implement Android practice domain service and target statistics in android/app/src/main/java/com/rbdarts/feature/practice/PracticeService.kt
- [X] T107 [P] [US4] Implement Firebase practice attempt persistence and ownership rules support in firebase/functions/src/practiceOperations.ts
- [X] T108 [P] [US4] Implement iOS practice setup, score entry, and stats views in ios/RBDarts/Features/Practice/PracticeView.swift
- [X] T109 [P] [US4] Implement Android practice setup, score entry, and stats screens in android/app/src/main/java/com/rbdarts/feature/practice/PracticeScreen.kt
- [X] T110 [US4] Enforce practice attempt official-stat isolation in firebase/firestore.rules
- [X] T111 [US4] Add privacy-safe diagnostics for practice score validation and save failures in ios/RBDarts/Features/Practice/PracticeDiagnostics.swift and android/app/src/main/java/com/rbdarts/feature/practice/PracticeDiagnostics.kt
- [X] T112 [US4] Validate quickstart practice isolation scenario and record results in specs/001-baseball-darts-app/validation/practice.md

**Checkpoint**: Practice mode works without changing official league stats.

## Phase 7: User Story 5 - Review Statistics, Summaries, and Insights (Priority: P5)

**Goal**: Provide game summaries, match summaries, player night summaries, standings, player/team stats, league analytics, and baseline predictive insights.

**Independent Test**: Complete one or more games and matches, then verify summaries, standings, stats, and baseline predictions display expected values.

### Tests for User Story 5

- [X] T113 [P] [US5] Add iOS unit tests for game summary, match summary, player night summary, standings, and baseline projections in ios/RBDartsTests/Stats/SummaryAndStatsTests.swift
- [X] T114 [P] [US5] Add Android unit tests for game summary, match summary, player night summary, standings, and baseline projections in android/app/src/test/java/com/rbdarts/feature/stats/SummaryAndStatsTest.kt
- [X] T115 [P] [US5] Add Firebase function tests for summary snapshots, stats aggregation, standings ranking, and baseline projections in firebase/functions/test/stats.test.ts
- [X] T116 [P] [US5] Add iOS UI test for opening completed game, match, and player night summaries in ios/RBDartsUITests/SummaryFlowTests.swift
- [X] T117 [P] [US5] Add Android Compose UI test for opening completed game, match, and player night summaries in android/app/src/androidTest/java/com/rbdarts/feature/stats/SummaryFlowTest.kt

### Implementation for User Story 5

- [X] T118 [P] [US5] Implement Firebase summary snapshot and stats aggregation operations in firebase/functions/src/statsOperations.ts
- [X] T119 [P] [US5] Implement iOS stats and summary service in ios/RBDarts/Features/Stats/StatsService.swift
- [X] T120 [P] [US5] Implement Android stats and summary service in android/app/src/main/java/com/rbdarts/feature/stats/StatsService.kt
- [X] T121 [P] [US5] Implement iOS game summary, match summary, player night summary, standings, and analytics views in ios/RBDarts/Features/Stats/StatsDashboardView.swift
- [X] T122 [P] [US5] Implement Android game summary, match summary, player night summary, standings, and analytics screens in android/app/src/main/java/com/rbdarts/feature/stats/StatsDashboardScreen.kt
- [X] T123 [P] [US5] Implement baseline pace and average-based projection logic for iOS in ios/RBDarts/Features/Stats/ProjectionService.swift
- [X] T124 [P] [US5] Implement baseline pace and average-based projection logic for Android in android/app/src/main/java/com/rbdarts/feature/stats/ProjectionService.kt
- [X] T125 [US5] Restrict stats and analytics visibility by league role and privacy settings in firebase/firestore.rules
- [X] T126 [US5] Add privacy-safe diagnostics for stats loading, projection failures, and stale offline data in ios/RBDarts/Features/Stats/StatsDiagnostics.swift and android/app/src/main/java/com/rbdarts/feature/stats/StatsDiagnostics.kt
- [X] T127 [US5] Validate quickstart summary and insights scenario and record results in specs/001-baseball-darts-app/validation/summaries-and-insights.md

**Checkpoint**: Summaries, standings, stats, and MVP insights are available from completed official records.

## Phase 8: Polish & Cross-Cutting Concerns

**Purpose**: Hardening, verification, accessibility, privacy, and release-readiness across all completed stories.

- [X] T128 [P] Run full shared fixture parity review and document results in specs/001-baseball-darts-app/validation/fixture-parity.md
- [X] T129 [P] Add accessibility review notes for score entry, scorecards, correction flows, and stats screens in specs/001-baseball-darts-app/validation/accessibility.md
- [X] T130 [P] Add offline and app-restart recovery validation notes for iOS and Android in specs/001-baseball-darts-app/validation/recovery.md
- [X] T131 [P] Add privacy and observability review for logs, crash reports, analytics events, and exports in specs/001-baseball-darts-app/validation/privacy-observability.md
- [X] T132 [P] Add dependency security and license review for iOS, Android, Firebase, and Facebook SDK dependencies in specs/001-baseball-darts-app/validation/dependency-review.md
- [X] T133 [P] Add App Check, Firestore rules, and Cloud Functions emulator validation notes in specs/001-baseball-darts-app/validation/firebase-security.md
- [X] T134 [P] Add performance validation for score entry latency, full-match responsiveness, and summary loading in specs/001-baseball-darts-app/validation/performance.md
- [X] T135 Update quickstart with concrete local build/test commands in specs/001-baseball-darts-app/quickstart.md
- [X] T136 Update README with implementation commands, supported platforms, and security setup notes in README.md

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies.
- **Foundational (Phase 2)**: Depends on Setup and blocks all user stories.
- **User Story 1 (Phase 3)**: Depends on Foundational. This is the MVP.
- **User Story 2 (Phase 4)**: Depends on Foundational and can use US1 scoring components, but must remain testable through league fixtures.
- **User Story 3 (Phase 5)**: Depends on US2 locking/finalization.
- **User Story 4 (Phase 6)**: Depends on Foundational and can run after US1 or in parallel with US2 if capacity allows.
- **User Story 5 (Phase 7)**: Depends on US1 and US2 official records; correction-aware stats depend on US3.
- **Polish (Phase 8)**: Depends on all desired stories for the release.

### User Story Dependencies

- **US1 - Standalone Game**: Foundation only.
- **US2 - League Matches**: Foundation; reuses scoring rules and active session persistence.
- **US3 - Locked Corrections**: US2 locked/finalized game records.
- **US4 - Practice Mode**: Foundation only, with optional navigation reuse from US1.
- **US5 - Stats and Insights**: Completed official records from US1/US2; correction recalculation from US3 for full coverage.

### Within Each User Story

- Required tests MUST be written and fail before implementation.
- Domain and contract tests before UI screens.
- Local persistence and repository integration before live score UI.
- Backend trusted operations before client finalization/correction flows.
- Story validation notes before moving to the next release candidate.

## Parallel Opportunities

- Setup tasks T005-T013 can run in parallel after T001-T004.
- Foundational model, fixture, security, persistence, and observability tasks T016-T040 can run in parallel where files do not overlap.
- iOS, Android, and Firebase test tasks within each user story can run in parallel.
- iOS and Android UI implementation tasks within a story can run in parallel after shared domain/service contracts are ready.
- US4 can run in parallel with US2 after Foundation because practice records are isolated from official league stats.

## Parallel Example: User Story 1

```bash
Task: "T041 iOS standalone game tests in ios/RBDartsTests/StandaloneGame/StandaloneGameTests.swift"
Task: "T042 Android standalone game tests in android/app/src/test/java/com/rbdarts/feature/standalonegame/StandaloneGameTest.kt"
Task: "T043 Firebase standalone game tests in firebase/functions/test/standaloneGame.test.ts"
Task: "T049 iOS setup screen in ios/RBDarts/Features/StandaloneGame/StandaloneGameSetupView.swift"
Task: "T050 Android setup screen in android/app/src/main/java/com/rbdarts/feature/standalonegame/StandaloneGameSetupScreen.kt"
```

## Parallel Example: User Story 2

```bash
Task: "T061 iOS league match tests in ios/RBDartsTests/League/LeagueMatchTests.swift"
Task: "T062 Android league match tests in android/app/src/test/java/com/rbdarts/feature/league/LeagueMatchTest.kt"
Task: "T063 Firebase league match tests in firebase/functions/test/leagueMatch.test.ts"
Task: "T070 iOS league settings in ios/RBDarts/Features/League/LeagueSettingsView.swift"
Task: "T071 Android league settings in android/app/src/main/java/com/rbdarts/feature/league/LeagueSettingsScreen.kt"
```

## Parallel Example: User Story 3

```bash
Task: "T085 iOS correction tests in ios/RBDartsTests/Corrections/CorrectionWorkflowTests.swift"
Task: "T086 Android correction tests in android/app/src/test/java/com/rbdarts/feature/corrections/CorrectionWorkflowTest.kt"
Task: "T087 Firebase correction tests in firebase/functions/test/corrections.test.ts"
Task: "T094 iOS correction UI in ios/RBDarts/Features/Corrections/CorrectionWorkflowView.swift"
Task: "T095 Android correction UI in android/app/src/main/java/com/rbdarts/feature/corrections/CorrectionWorkflowScreen.kt"
```

## Parallel Example: User Story 4

```bash
Task: "T100 iOS practice tests in ios/RBDartsTests/Practice/PracticeModeTests.swift"
Task: "T101 Android practice tests in android/app/src/test/java/com/rbdarts/feature/practice/PracticeModeTest.kt"
Task: "T102 Firebase practice tests in firebase/functions/test/practice.test.ts"
Task: "T108 iOS practice UI in ios/RBDarts/Features/Practice/PracticeView.swift"
Task: "T109 Android practice UI in android/app/src/main/java/com/rbdarts/feature/practice/PracticeScreen.kt"
```

## Parallel Example: User Story 5

```bash
Task: "T113 iOS stats tests in ios/RBDartsTests/Stats/SummaryAndStatsTests.swift"
Task: "T114 Android stats tests in android/app/src/test/java/com/rbdarts/feature/stats/SummaryAndStatsTest.kt"
Task: "T115 Firebase stats tests in firebase/functions/test/stats.test.ts"
Task: "T121 iOS stats dashboard in ios/RBDarts/Features/Stats/StatsDashboardView.swift"
Task: "T122 Android stats dashboard in android/app/src/main/java/com/rbdarts/feature/stats/StatsDashboardScreen.kt"
```

## Implementation Strategy

### MVP First

1. Complete Phase 1 and Phase 2.
2. Complete Phase 3 for US1 standalone scoring.
3. Validate standalone scoring, invalid score rejection, extra innings, recovery, and final scorecard.
4. Demo or ship a limited standalone scorer if desired.

### Incremental Delivery

1. Add US2 league matches, handicaps, locking, and standings.
2. Add US3 locked correction workflow and audit trail.
3. Add US4 practice mode with official-stat isolation.
4. Add US5 summaries, statistics, and baseline insights.
5. Run Phase 8 hardening before release.

### Release Gates

- All required tests pass for completed stories.
- Firestore rules deny unauthorized paths in emulator tests.
- No first-party password flow exists.
- No secrets or tokens are present in logs, analytics, crash reports, fixtures, or repository files.
- Score entry latency and recovery validation meet the plan targets.
- Corrections preserve immutable audit records and recalculate downstream records.
