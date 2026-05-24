# Tasks: Android Material You App Experience

**Input**: Design documents from `/specs/003-android-material-you-ui/`

**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/, quickstart.md

**Tests**: Required for loading/auth routing, protected navigation, SSO failure/cancel states, player/season/handicap forms, scoring validation/recovery, accessibility, performance smoke, and privacy-safe diagnostics.

**Organization**: Tasks are grouped by user story so each Material You increment can be implemented and tested independently.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel because it touches different files and does not depend on incomplete tasks.
- **[Story]**: Which user story this task belongs to, only used in user story phases.
- Every task includes an exact file path.

## Phase 1: Setup (Android UI Foundation)

**Purpose**: Prepare Android-only asset, theme, navigation, and test scaffolding for the Material You redesign.

- [x] T001 Create Android feature package marker files in android/app/src/main/java/com/rbdarts/feature/home/.gitkeep, android/app/src/main/java/com/rbdarts/feature/gametype/.gitkeep, android/app/src/main/java/com/rbdarts/feature/player/.gitkeep, android/app/src/main/java/com/rbdarts/feature/season/.gitkeep, android/app/src/main/java/com/rbdarts/feature/handicap/.gitkeep, and android/app/src/main/java/com/rbdarts/feature/scoring/.gitkeep
- [x] T002 Create Android UI test package marker files in android/app/src/androidTest/java/com/rbdarts/materialyou/.gitkeep and android/app/src/test/java/com/rbdarts/materialyou/.gitkeep
- [x] T003 [P] Copy the attached SVG source into android/app/src/main/res/raw/rbdarts_loading_source.svg for traceable design input
- [x] T004 [P] Create Android-compatible loading image asset in android/app/src/main/res/drawable/rbdarts_loading_mark.xml or android/app/src/main/res/drawable-nodpi/rbdarts_loading_mark.png
- [x] T005 [P] Add Material You theme token scaffolding in android/app/src/main/java/com/rbdarts/core/designsystem/MaterialYouTheme.kt
- [x] T006 [P] Add shared Material You component scaffolding in android/app/src/main/java/com/rbdarts/core/designsystem/RBDartsComponents.kt
- [x] T007 [P] Add route constants and destination metadata scaffolding in android/app/src/main/java/com/rbdarts/app/RBDartsRoutes.kt
- [x] T008 [P] Add UI state model scaffolding in android/app/src/main/java/com/rbdarts/core/ui/AppUiStates.kt
- [x] T009 [P] Add privacy-safe UI diagnostics event names in android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt
- [x] T010 Update README.md with Android Material You verification and APK/emulator smoke commands

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establish theme, app shell, state, permissions, diagnostics, and test infrastructure shared by every user story.

**CRITICAL**: No user story implementation should begin until this foundation is complete.

- [x] T011 Create Android Material You threat model and data classification notes in specs/003-android-material-you-ui/security-threat-model.md
- [x] T012 [P] Add route contract tests in android/app/src/test/java/com/rbdarts/materialyou/RBDartsRoutesTest.kt
- [x] T013 [P] Add UI state model tests in android/app/src/test/java/com/rbdarts/materialyou/AppUiStatesTest.kt
- [x] T014 [P] Add privacy-safe UI diagnostics tests in android/app/src/test/java/com/rbdarts/materialyou/UiDiagnosticsTest.kt
- [x] T015 [P] Add Material You theme smoke test in android/app/src/androidTest/java/com/rbdarts/materialyou/MaterialYouThemeTest.kt
- [x] T016 [P] Add Android accessibility matcher helpers in android/app/src/androidTest/java/com/rbdarts/materialyou/AccessibilityAssertions.kt
- [x] T017 Implement dynamic color and branded fallback theme in android/app/src/main/java/com/rbdarts/core/designsystem/MaterialYouTheme.kt
- [x] T018 Implement shared top app bar, section header, empty state, error state, loading state, and primary action components in android/app/src/main/java/com/rbdarts/core/designsystem/RBDartsComponents.kt
- [x] T019 Implement immutable UI state models from data-model.md in android/app/src/main/java/com/rbdarts/core/ui/AppUiStates.kt
- [x] T020 Implement route metadata and protected-route definitions from contracts/navigation-map.md in android/app/src/main/java/com/rbdarts/app/RBDartsRoutes.kt
- [x] T021 Implement privacy-safe UI diagnostics helpers in android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt
- [x] T022 Update android/app/src/main/java/com/rbdarts/app/MainActivity.kt to use the shared Material You theme wrapper
- [x] T023 Add common Android UI test harness in android/app/src/androidTest/java/com/rbdarts/materialyou/RBDartsComposeTestHarness.kt
- [x] T024 Run baseline Android verification and record result in specs/003-android-material-you-ui/verification.md

**Checkpoint**: Foundation ready. User story implementation can now begin.

---

## Phase 3: User Story 1 - Launch And Authenticate With Branded Material You Flow (Priority: P1)

**Goal**: Display a branded loading page, route users by auth state, and provide Google/Facebook SSO login with safe failure/cancel states.

**Independent Test**: Cold launch the app, verify loading image/name/version, route to login when unauthenticated, exercise Google/Facebook actions, and verify provider cancellation/failure stays on login without data loss.

### Tests for User Story 1

- [x] T025 [P] [US1] Add loading state unit tests in android/app/src/test/java/com/rbdarts/materialyou/LaunchPresentationStateTest.kt
- [x] T026 [P] [US1] Add auth UI state unit tests in android/app/src/test/java/com/rbdarts/materialyou/AuthUiStateTest.kt
- [x] T027 [P] [US1] Add startup routing unit tests in android/app/src/test/java/com/rbdarts/materialyou/StartupRouteControllerTest.kt
- [x] T028 [P] [US1] Add loading screen Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenTest.kt
- [x] T029 [P] [US1] Add login screen Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/LoginScreenTest.kt
- [x] T030 [P] [US1] Add auth cancellation/failure UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/LoginFailureStateTest.kt
- [x] T031 [P] [US1] Add loading screen accessibility test in android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingAccessibilityTest.kt

### Implementation for User Story 1

- [x] T032 [P] [US1] Implement loading UI state holder in android/app/src/main/java/com/rbdarts/feature/auth/StartupRouteController.kt
- [x] T033 [P] [US1] Implement loading screen in android/app/src/main/java/com/rbdarts/feature/auth/LoadingScreen.kt
- [x] T034 [P] [US1] Implement login UI state holder in android/app/src/main/java/com/rbdarts/feature/auth/LoginViewModel.kt
- [x] T035 [P] [US1] Implement login screen with Google/Facebook actions in android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt
- [x] T036 [US1] Integrate Google/Facebook provider actions with existing auth provider abstractions in android/app/src/main/java/com/rbdarts/feature/auth/AuthProvider.kt
- [x] T037 [US1] Add privacy/support/account deletion links to login screen using android/app/src/main/java/com/rbdarts/feature/settings/PrivacyAndSupportScreen.kt
- [x] T038 [US1] Route MainActivity through loading, login, or authenticated shell in android/app/src/main/java/com/rbdarts/app/MainActivity.kt
- [x] T039 [US1] Add version/build label sourcing from release config or package metadata in android/app/src/main/java/com/rbdarts/core/launch/ReleaseConfiguration.kt
- [x] T040 [US1] Add privacy-safe startup/auth diagnostics in android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt
- [x] T041 [US1] Add cold-launch loading and auth manual evidence template in specs/003-android-material-you-ui/evidence/us1-loading-auth.md

**Checkpoint**: User Story 1 is independently demoable.

---

## Phase 4: User Story 2 - Navigate Core Baseball Darts Sections (Priority: P2)

**Goal**: Provide authenticated Material You navigation across Game Type, Players, Seasons, Handicaps, Scoring, Stats/Summary, and Settings/Privacy.

**Independent Test**: Sign in or use a test authenticated state, navigate to every top-level destination, rotate/resume, and confirm state and protected routing are preserved.

### Tests for User Story 2

- [x] T042 [P] [US2] Add app shell route state tests in android/app/src/test/java/com/rbdarts/materialyou/AppShellStateTest.kt
- [x] T043 [P] [US2] Add protected-route tests in android/app/src/test/java/com/rbdarts/materialyou/ProtectedRouteTest.kt
- [x] T044 [P] [US2] Add authenticated navigation Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/AppNavigationTest.kt
- [x] T045 [P] [US2] Add adaptive navigation UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/AdaptiveNavigationTest.kt
- [x] T046 [P] [US2] Add navigation accessibility test in android/app/src/androidTest/java/com/rbdarts/materialyou/NavigationAccessibilityTest.kt

### Implementation for User Story 2

- [x] T047 [P] [US2] Implement authenticated app shell in android/app/src/main/java/com/rbdarts/app/RBDartsAppShell.kt
- [x] T048 [P] [US2] Implement navigation host in android/app/src/main/java/com/rbdarts/app/RBDartsNavHost.kt
- [x] T049 [P] [US2] Implement adaptive navigation bar/rail/drawer selection in android/app/src/main/java/com/rbdarts/app/RBDartsAdaptiveNavigation.kt
- [x] T050 [P] [US2] Implement home screen summary and next actions in android/app/src/main/java/com/rbdarts/feature/home/HomeScreen.kt
- [x] T051 [P] [US2] Implement initial Game Type destination shell in android/app/src/main/java/com/rbdarts/feature/gametype/GameTypeScreen.kt
- [x] T052 [P] [US2] Implement initial Players destination shell in android/app/src/main/java/com/rbdarts/feature/player/PlayerScreen.kt
- [x] T053 [P] [US2] Implement initial Seasons destination shell in android/app/src/main/java/com/rbdarts/feature/season/SeasonScreen.kt
- [x] T054 [P] [US2] Implement initial Handicaps destination shell in android/app/src/main/java/com/rbdarts/feature/handicap/HandicapScreen.kt
- [x] T055 [P] [US2] Implement Scoring destination bridge in android/app/src/main/java/com/rbdarts/feature/scoring/ScoringScreen.kt
- [x] T056 [US2] Integrate settings/privacy destination into RBDartsNavHost using android/app/src/main/java/com/rbdarts/feature/settings/PrivacyAndSupportScreen.kt
- [x] T057 [US2] Enforce route protection on sign-out/session expiration in android/app/src/main/java/com/rbdarts/app/RBDartsNavHost.kt
- [x] T058 [US2] Add navigation manual evidence template in specs/003-android-material-you-ui/evidence/us2-navigation.md

**Checkpoint**: User Stories 1 and 2 are independently usable.

---

## Phase 5: User Story 3 - Create Players And Seasons Before Scoring (Priority: P3)

**Goal**: Add Material You player creation and season creation workflows that feed scoring and handicap flows.

**Independent Test**: Create a player, create a season, validate required fields, and verify created values appear in setup-related screens without duplicate entry.

### Tests for User Story 3

- [x] T059 [P] [US3] Add player draft validation tests in android/app/src/test/java/com/rbdarts/materialyou/PlayerProfileDraftTest.kt
- [x] T060 [P] [US3] Add season draft validation tests in android/app/src/test/java/com/rbdarts/materialyou/SeasonDraftTest.kt
- [x] T061 [P] [US3] Add player creation Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/PlayerCreationScreenTest.kt
- [x] T062 [P] [US3] Add season creation Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/SeasonCreationScreenTest.kt
- [x] T063 [P] [US3] Add player/season state restoration test in android/app/src/androidTest/java/com/rbdarts/materialyou/SetupStateRestorationTest.kt

### Implementation for User Story 3

- [x] T064 [P] [US3] Implement player UI state and events in android/app/src/main/java/com/rbdarts/feature/player/PlayerUiState.kt
- [x] T065 [P] [US3] Implement season UI state and events in android/app/src/main/java/com/rbdarts/feature/season/SeasonUiState.kt
- [x] T066 [P] [US3] Implement player creation view model in android/app/src/main/java/com/rbdarts/feature/player/PlayerViewModel.kt
- [x] T067 [P] [US3] Implement season creation view model in android/app/src/main/java/com/rbdarts/feature/season/SeasonViewModel.kt
- [x] T068 [US3] Expand player shell into full Material You player creation form in android/app/src/main/java/com/rbdarts/feature/player/PlayerScreen.kt
- [x] T069 [US3] Expand season shell into full Material You season creation form in android/app/src/main/java/com/rbdarts/feature/season/SeasonScreen.kt
- [x] T070 [US3] Add player and season selection summaries to GameTypeScreen in android/app/src/main/java/com/rbdarts/feature/gametype/GameTypeScreen.kt
- [x] T071 [US3] Persist or bridge player/season drafts through existing repository services in android/app/src/main/java/com/rbdarts/core/data/RBDartsRepository.kt
- [x] T072 [US3] Add player/season manual evidence template in specs/003-android-material-you-ui/evidence/us3-player-season.md

**Checkpoint**: Player and season setup can be tested without handicap or full scoring changes.

---

## Phase 6: User Story 4 - Manage Individual Player Handicaps (Priority: P4)

**Goal**: Provide permission-aware player-level handicap display/editing with derived scoring impact and audit-friendly state.

**Independent Test**: Select a player, view handicap state, edit when authorized, block edit when unauthorized, and verify future setup can use the updated value.

### Tests for User Story 4

- [x] T073 [P] [US4] Add player handicap calculation/state tests in android/app/src/test/java/com/rbdarts/materialyou/PlayerHandicapStateTest.kt
- [x] T074 [P] [US4] Add handicap permission tests in android/app/src/test/java/com/rbdarts/materialyou/HandicapPermissionTest.kt
- [x] T075 [P] [US4] Add handicap Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/HandicapScreenTest.kt
- [x] T076 [P] [US4] Add handicap accessibility test in android/app/src/androidTest/java/com/rbdarts/materialyou/HandicapAccessibilityTest.kt

### Implementation for User Story 4

- [x] T077 [P] [US4] Implement handicap UI state and events in android/app/src/main/java/com/rbdarts/feature/handicap/HandicapUiState.kt
- [x] T078 [P] [US4] Implement handicap view model in android/app/src/main/java/com/rbdarts/feature/handicap/HandicapViewModel.kt
- [x] T079 [US4] Expand handicap shell into player-level Material You handicap management screen in android/app/src/main/java/com/rbdarts/feature/handicap/HandicapScreen.kt
- [x] T080 [US4] Add derived handicap preview component in android/app/src/main/java/com/rbdarts/feature/handicap/HandicapImpactPreview.kt
- [x] T081 [US4] Add permission-aware edit/view behavior in android/app/src/main/java/com/rbdarts/feature/handicap/HandicapViewModel.kt
- [x] T082 [US4] Add handicap audit event mapping in android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt
- [x] T083 [US4] Bridge saved handicap values into GameTypeScreen setup summaries in android/app/src/main/java/com/rbdarts/feature/gametype/GameTypeScreen.kt
- [x] T084 [US4] Add handicap manual evidence template in specs/003-android-material-you-ui/evidence/us4-handicap.md

**Checkpoint**: Handicap management can be tested independently and feeds future setup.

---

## Phase 7: User Story 5 - Score Baseball Darts With A Robust Material You Interface (Priority: P5)

**Goal**: Refactor scoring into a Material You flow with game context, valid/invalid entry, totals, handicap impact, completion, recovery, and accessibility support.

**Independent Test**: Start a game from configured setup, enter scores, reject invalid scores, recover after interruption, and complete a scorecard.

### Tests for User Story 5

- [x] T085 [P] [US5] Add scoring UI state tests in android/app/src/test/java/com/rbdarts/materialyou/ScoringUiStateTest.kt
- [x] T086 [P] [US5] Add scoring validation bridge tests in android/app/src/test/java/com/rbdarts/materialyou/ScoringValidationUiTest.kt
- [x] T087 [P] [US5] Add scoring recovery UI state tests in android/app/src/test/java/com/rbdarts/materialyou/ScoringRecoveryStateTest.kt
- [x] T088 [P] [US5] Add robust scoring Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/RobustScoringScreenTest.kt
- [x] T089 [P] [US5] Add invalid score Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/InvalidScoreEntryTest.kt
- [x] T090 [P] [US5] Add scoring accessibility test in android/app/src/androidTest/java/com/rbdarts/materialyou/ScoringAccessibilityTest.kt
- [x] T091 [P] [US5] Add scoring latency smoke test in android/app/src/androidTest/java/com/rbdarts/materialyou/ScoringPerformanceSmokeTest.kt

### Implementation for User Story 5

- [x] T092 [P] [US5] Implement scoring UI state and events in android/app/src/main/java/com/rbdarts/feature/scoring/ScoringUiState.kt
- [x] T093 [P] [US5] Implement scoring view model bridge to existing standalone scoring service in android/app/src/main/java/com/rbdarts/feature/scoring/ScoringViewModel.kt
- [x] T094 [US5] Replace scoring destination bridge with robust Material You scoring screen in android/app/src/main/java/com/rbdarts/feature/scoring/ScoringScreen.kt
- [x] T095 [US5] Add score entry keypad/input component in android/app/src/main/java/com/rbdarts/feature/scoring/ScoreEntryControls.kt
- [x] T096 [US5] Add running totals and handicap-adjusted totals component in android/app/src/main/java/com/rbdarts/feature/scoring/ScoreTotalsPanel.kt
- [x] T097 [US5] Add participant and active inning context component in android/app/src/main/java/com/rbdarts/feature/scoring/ScoringContextHeader.kt
- [x] T098 [US5] Add scorecard review and completion component in android/app/src/main/java/com/rbdarts/feature/scoring/ScorecardReviewPanel.kt
- [x] T099 [US5] Integrate active scoring recovery from existing repository/session store in android/app/src/main/java/com/rbdarts/feature/scoring/ScoringViewModel.kt
- [x] T100 [US5] Add invalid input and offline/save failure states in android/app/src/main/java/com/rbdarts/feature/scoring/ScoringScreen.kt
- [x] T101 [US5] Add scoring diagnostics without sensitive payloads in android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt
- [x] T102 [US5] Add scoring manual evidence template in specs/003-android-material-you-ui/evidence/us5-scoring.md

**Checkpoint**: All requested sections are functional and scoring can be demoed end-to-end.

---

## Phase 8: Polish & Cross-Cutting Concerns

**Purpose**: Validate the full Material You experience across accessibility, state restoration, emulator/device smoke, docs, and launch readiness.

- [x] T103 [P] Add full app journey Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/MaterialYouJourneyTest.kt
- [x] T104 [P] Add dark theme and dynamic color screenshot smoke test in android/app/src/androidTest/java/com/rbdarts/materialyou/ThemeModeSmokeTest.kt
- [x] T105 [P] Add large font/display size accessibility smoke test in android/app/src/androidTest/java/com/rbdarts/materialyou/LargeFontSmokeTest.kt
- [x] T106 [P] Add sign-out protected navigation regression test in android/app/src/androidTest/java/com/rbdarts/materialyou/SignOutRouteProtectionTest.kt
- [x] T107 [P] Add loading image asset safety review in specs/003-android-material-you-ui/asset-review.md
- [x] T108 [P] Add Material You design QA checklist in specs/003-android-material-you-ui/design-qa.md
- [x] T109 [P] Update launch store screenshot checklist for new Android UI in launch/google-play/screenshots/screenshot-checklist.md
- [x] T110 Update Android quickstart verification notes in specs/003-android-material-you-ui/quickstart.md
- [x] T111 Update README.md with Material You feature workflow and emulator demo path
- [x] T112 Run Android unit tests with cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew testDebugUnitTest and record result in specs/003-android-material-you-ui/verification.md
- [x] T113 Run Android lint with cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew lintDebug and record result in specs/003-android-material-you-ui/verification.md
- [x] T114 Build debug APK with cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew assembleDebug and record result in specs/003-android-material-you-ui/verification.md
- [x] T115 Install and launch APK on Medium_Phone_API_36.1 emulator and save screenshot evidence to specs/003-android-material-you-ui/evidence/emulator-smoke.md
- [x] T116 Run final secret scan check for copied image/config/UI docs and record result in specs/003-android-material-you-ui/security-threat-model.md
- [x] T117 Review all requirements against tasks and mark checklist completion in specs/003-android-material-you-ui/checklists/requirements.md

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies.
- **Foundational (Phase 2)**: Depends on Setup and blocks all user stories.
- **US1 Launch/Auth (Phase 3)**: Depends on Foundational and is the MVP.
- **US2 Navigation (Phase 4)**: Depends on US1 routing and Foundational route metadata.
- **US3 Player/Season (Phase 5)**: Depends on US2 navigation shells and shared UI state.
- **US4 Handicap (Phase 6)**: Depends on US3 player state and US2 navigation.
- **US5 Scoring (Phase 7)**: Depends on US2 navigation and should integrate with US3/US4 setup where available.
- **Polish (Phase 8)**: Depends on all desired user stories.

### User Story Dependencies

- **US1 - Launch/Auth**: Can be implemented after Foundation only.
- **US2 - Navigation**: Requires US1 route/auth shell; can use initial shells for later sections.
- **US3 - Player/Season**: Requires US2 navigation destinations.
- **US4 - Handicap**: Requires player selection/state from US3.
- **US5 - Scoring**: Can begin with existing scoring services after US2, then integrate US3/US4 when those are complete.

### Within Each User Story

- Write tests first and confirm they fail before implementation.
- Implement UI state models before screens.
- Implement view models before navigation integration.
- Complete story-specific accessibility and state restoration checks before checkpoint approval.

## Parallel Opportunities

- T003-T009 can run in parallel after T001-T002.
- T012-T016 can run in parallel during Foundation.
- US1 tests T025-T031 can run in parallel.
- US2 initial destination shells T050-T055 can run in parallel after app shell route interfaces exist.
- US3 player and season implementation can run in parallel across separate packages.
- US4 tests and read-only display can begin while audit persistence is refined.
- US5 component tasks T095-T098 can run in parallel after ScoringUiState is defined.

## Parallel Example: User Story 1

```bash
# Required tests can be authored together:
Task: "Add loading screen Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenTest.kt"
Task: "Add login screen Compose UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/LoginScreenTest.kt"
Task: "Add auth cancellation/failure UI test in android/app/src/androidTest/java/com/rbdarts/materialyou/LoginFailureStateTest.kt"

# Implementation can split after UI state contracts exist:
Task: "Implement loading screen in android/app/src/main/java/com/rbdarts/feature/auth/LoadingScreen.kt"
Task: "Implement login screen with Google/Facebook actions in android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt"
```

## Implementation Strategy

### MVP First

1. Complete Setup and Foundational phases.
2. Complete US1 loading/auth routing.
3. Validate cold launch, loading screen, login screen, provider failure/cancel states, and privacy links.
4. Demo US1 before proceeding to the full app shell.

### Incremental Delivery

1. US1: Branded loading and SSO login.
2. US2: Authenticated Material You navigation shell.
3. US3: Player and season setup.
4. US4: Player-level handicap management.
5. US5: Robust scoring UI.
6. Polish: Accessibility, performance, emulator, APK, screenshot, and docs.

### Notes

- [P] tasks touch different files and can run in parallel.
- Each task includes an exact file path for traceability.
- Android-only scope is intentional for this feature.
- Do not commit real Google/Facebook/Firebase credentials while implementing SSO UI.
