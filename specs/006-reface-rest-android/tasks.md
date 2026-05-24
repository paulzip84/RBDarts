# Tasks: Authenticated Android App Shell Redesign

**Input**: Design documents from `/specs/006-reface-rest-android/`

**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/, quickstart.md

**Tests**: Required for protected routing, drawer navigation, no bottom navigation or rail, dark authenticated styling, accessibility, adaptive layout, workflow screens, privacy-safe diagnostics, Android lint, APK build, and emulator/manual screenshot smoke.

**Organization**: Tasks are grouped by user story so the authenticated app shell redesign can be implemented and tested incrementally.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel because it touches different files and does not depend on incomplete tasks.
- **[Story]**: Which user story this task belongs to, only used in user story phases.
- Every task includes an exact file path.

## Phase 1: Setup (Authenticated Shell Redesign Workspace)

**Purpose**: Prepare security notes, design QA, baseline evidence, asset review, and verification logs.

- [x] T001 Create authenticated shell security and data classification notes in `specs/006-reface-rest-android/security-threat-model.md`
- [x] T002 [P] Create dark app shell visual QA checklist in `specs/006-reface-rest-android/design-qa.md`
- [x] T003 [P] Create verification log in `specs/006-reface-rest-android/verification.md`
- [x] T004 [P] Create manual smoke evidence template in `specs/006-reface-rest-android/evidence/authenticated-shell-smoke.md`
- [x] T005 Record current bottom navigation, navigation rail, and bright-screen baseline deltas in `specs/006-reface-rest-android/evidence/app-shell-baseline.md`
- [x] T006 [P] Create local icon and visual asset review checklist in `specs/006-reface-rest-android/asset-review.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establish shell/menu state, route contracts, diagnostics, reusable dark components, local icons, and test harness support before user story work.

**CRITICAL**: No user story implementation should begin until this foundation is complete.

- [x] T007 [P] Add authenticated shell state unit tests in `android/app/src/test/java/com/rbdarts/materialyou/AuthenticatedShellStateTest.kt`
- [x] T008 [P] Add navigation menu item contract tests in `android/app/src/test/java/com/rbdarts/materialyou/NavigationMenuItemContractTest.kt`
- [x] T009 [P] Extend protected route tests for drawer navigation, sign-out, and session restoration in `android/app/src/test/java/com/rbdarts/materialyou/ProtectedRouteTest.kt`
- [x] T010 [P] Add privacy-safe navigation diagnostics tests in `android/app/src/test/java/com/rbdarts/materialyou/NavigationDiagnosticsContractTest.kt`
- [x] T011 [P] Add Compose drawer test harness helpers in `android/app/src/androidTest/java/com/rbdarts/materialyou/AuthenticatedShellComposeTestHarness.kt`
- [x] T012 Add authenticated drawer/menu state models in `android/app/src/main/java/com/rbdarts/core/ui/AppUiStates.kt`
- [x] T013 Add privacy-safe drawer diagnostic helpers in `android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt`
- [x] T014 Add premium authenticated shell colors, surfaces, buttons, and list components in `android/app/src/main/java/com/rbdarts/core/designsystem/AuthenticatedShellComponents.kt`
- [x] T015 Add local vector icon drawables for hamburger and drawer destinations in `android/app/src/main/res/drawable/ic_rbdarts_menu.xml`
- [x] T016 [P] Add Home drawer icon vector in `android/app/src/main/res/drawable/ic_rbdarts_home.xml`
- [x] T017 [P] Add Game Type drawer icon vector in `android/app/src/main/res/drawable/ic_rbdarts_game_type.xml`
- [x] T018 [P] Add Players drawer icon vector in `android/app/src/main/res/drawable/ic_rbdarts_players.xml`
- [x] T019 [P] Add Seasons drawer icon vector in `android/app/src/main/res/drawable/ic_rbdarts_seasons.xml`
- [x] T020 [P] Add Handicaps drawer icon vector in `android/app/src/main/res/drawable/ic_rbdarts_handicaps.xml`
- [x] T021 [P] Add Scoring drawer icon vector in `android/app/src/main/res/drawable/ic_rbdarts_scoring.xml`
- [x] T022 [P] Add Settings drawer icon vector in `android/app/src/main/res/drawable/ic_rbdarts_settings.xml`
- [x] T023 [P] Add Sign Out drawer icon vector in `android/app/src/main/res/drawable/ic_rbdarts_sign_out.xml`
- [x] T024 Update security notes with protected route, sign-out, drawer label, and diagnostics constraints in `specs/006-reface-rest-android/security-threat-model.md`
- [x] T025 Run baseline compile check with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew compileDebugKotlin` and record result in `specs/006-reface-rest-android/verification.md`

**Checkpoint**: Foundation ready. User story implementation can now begin.

---

## Phase 3: User Story 1 - Use A Unified Premium Dark App Shell (Priority: P1)

**Goal**: Show Home, Game Type, Players, Seasons, Handicaps, Scoring, and Settings inside a cohesive dark authenticated shell that visually follows the redesigned login page.

**Independent Test**: Render each authenticated destination and verify the dark background, elevated surfaces, teal accent, text treatment, and content spacing match the login-inspired design without changing loading or login screens.

### Tests for User Story 1

- [x] T026 [P] [US1] Add dark authenticated Home shell Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/AuthenticatedShellDarkHomeTest.kt`
- [x] T027 [P] [US1] Add route-by-route dark surface Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/AuthenticatedScreensDarkStyleTest.kt`
- [x] T028 [P] [US1] Add loading/login preservation regression Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadLoginPreservedByShellTest.kt`
- [x] T029 [P] [US1] Add bright generic Material surface regression test in `android/app/src/androidTest/java/com/rbdarts/materialyou/AuthenticatedDarkSurfaceRegressionTest.kt`

### Implementation for User Story 1

- [x] T030 [US1] Refactor authenticated shell layout to use premium dark background, top app bar, and content surfaces in `android/app/src/main/java/com/rbdarts/app/RBDartsAppShell.kt`
- [x] T031 [US1] Add reusable dark screen containers and workflow surfaces in `android/app/src/main/java/com/rbdarts/core/designsystem/RBDartsComponents.kt`
- [x] T032 [US1] Integrate dark shell containers around authenticated destinations in `android/app/src/main/java/com/rbdarts/app/RBDartsNavHost.kt`
- [x] T033 [US1] Preserve redesigned loading and login route separation from the authenticated shell in `android/app/src/main/java/com/rbdarts/app/RBDartsAppRoot.kt`
- [x] T034 [US1] Tune Material You dark color roles without disrupting login colors in `android/app/src/main/java/com/rbdarts/core/designsystem/MaterialYouTheme.kt`
- [x] T035 [US1] Record dark authenticated screen visual decisions and screenshot expectations in `specs/006-reface-rest-android/design-qa.md`

**Checkpoint**: User Story 1 is independently demoable.

---

## Phase 4: User Story 2 - Navigate With A Top-Left Hamburger Menu (Priority: P1)

**Goal**: Replace the existing bottom navigation and wide navigation rail with a top-left hamburger button that opens a Material 3 navigation drawer containing all authenticated destinations plus Sign Out.

**Independent Test**: On authenticated screens, verify the hamburger is visible at the top left, the drawer opens and closes, destination selection works, Sign Out returns to login, and no bottom bar or navigation rail is present.

### Tests for User Story 2

- [x] T036 [P] [US2] Add top-left hamburger visibility Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/HamburgerNavigationButtonTest.kt`
- [x] T037 [P] [US2] Add no bottom navigation or rail regression Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/NoBottomNavigationRegressionTest.kt`
- [x] T038 [P] [US2] Add drawer content and selected-state Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/NavigationDrawerContentTest.kt`
- [x] T039 [P] [US2] Add drawer destination selection and close behavior Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/NavigationDrawerSelectionTest.kt`
- [x] T040 [P] [US2] Add drawer Sign Out routing Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/NavigationDrawerSignOutTest.kt`
- [x] T041 [P] [US2] Add Android back closes drawer Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/NavigationDrawerBackBehaviorTest.kt`

### Implementation for User Story 2

- [x] T042 [US2] Replace bottom navigation and navigation rail behavior with a Material 3 drawer implementation in `android/app/src/main/java/com/rbdarts/app/RBDartsAdaptiveNavigation.kt`
- [x] T043 [US2] Add the top-left hamburger app bar action and drawer state wiring in `android/app/src/main/java/com/rbdarts/app/RBDartsAppShell.kt`
- [x] T044 [US2] Render Home, Game Type, Players, Seasons, Handicaps, Scoring, Settings, and Sign Out drawer items in `android/app/src/main/java/com/rbdarts/app/RBDartsAdaptiveNavigation.kt`
- [x] T045 [US2] Wire drawer destination selection, drawer close, and current-route selected state in `android/app/src/main/java/com/rbdarts/app/RBDartsAppShell.kt`
- [x] T046 [US2] Preserve protected destination and sign-out behavior through route state in `android/app/src/main/java/com/rbdarts/app/RBDartsRoutes.kt`
- [x] T047 [US2] Emit privacy-safe drawer open, close, destination selected, sign-out, and protected redirect diagnostics in `android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt`
- [x] T048 [US2] Record drawer behavior decisions and no-bottom-nav evidence requirements in `specs/006-reface-rest-android/evidence/authenticated-shell-smoke.md`

**Checkpoint**: User Stories 1 and 2 are independently usable.

---

## Phase 5: User Story 3 - Preserve Efficient Darts Workflows (Priority: P2)

**Goal**: Keep game type selection, player creation, season creation, handicap management, and scoring fast, readable, and complete after the dark shell redesign.

**Independent Test**: Complete representative setup and scoring workflows and verify primary actions remain visible, controls are readable, state is preserved, and scoring interactions remain responsive.

### Tests for User Story 3

- [x] T049 [P] [US3] Add player and season dark form Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/DarkWorkflowFormsTest.kt`
- [x] T050 [P] [US3] Add player-level handicap dark edit Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/DarkHandicapWorkflowTest.kt`
- [x] T051 [P] [US3] Add scoring controls dark workflow Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/DarkScoringWorkflowTest.kt`
- [x] T052 [P] [US3] Add scoring responsiveness smoke or recomposition guard test in `android/app/src/androidTest/java/com/rbdarts/materialyou/ScoringShellPerformanceSmokeTest.kt`

### Implementation for User Story 3

- [x] T053 [US3] Restyle the Home dashboard with dark shell surfaces and concise workflow entry points in `android/app/src/main/java/com/rbdarts/feature/home/HomeScreen.kt`
- [x] T054 [US3] Restyle Game Type selection while preserving selection behavior in `android/app/src/main/java/com/rbdarts/feature/gametype/GameTypeScreen.kt`
- [x] T055 [US3] Restyle player list and player creation form while preserving validation and save behavior in `android/app/src/main/java/com/rbdarts/feature/player/PlayerScreen.kt`
- [x] T056 [US3] Restyle season list and season creation form while preserving validation and save behavior in `android/app/src/main/java/com/rbdarts/feature/season/SeasonScreen.kt`
- [x] T057 [US3] Restyle player-level handicap management and override controls in `android/app/src/main/java/com/rbdarts/feature/handicap/HandicapScreen.kt`
- [x] T058 [US3] Restyle scoring controls, inning context, totals, and submit actions in `android/app/src/main/java/com/rbdarts/feature/scoring/ScoringScreen.kt`
- [x] T059 [US3] Restyle Settings rendering or placeholder route content in `android/app/src/main/java/com/rbdarts/app/RBDartsNavHost.kt`
- [x] T060 [US3] Preserve form and scoring state across drawer open/close and destination changes in `android/app/src/main/java/com/rbdarts/app/RBDartsAppShell.kt`
- [x] T061 [US3] Record workflow QA notes for forms, handicaps, and scoring in `specs/006-reface-rest-android/design-qa.md`

**Checkpoint**: User Stories 1, 2, and 3 are independently functional.

---

## Phase 6: User Story 4 - Support Accessibility And Adaptive Layouts (Priority: P3)

**Goal**: Ensure the hamburger shell and dark authenticated screens work with TalkBack, large font, display zoom, rotation, compact widths, and expanded widths without restoring old navigation.

**Independent Test**: Render representative screens with compact/expanded layouts and accessibility settings, then verify labels, selected states, focus order, content fit, and back behavior.

### Tests for User Story 4

- [x] T062 [P] [US4] Add hamburger accessibility label Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/HamburgerAccessibilityTest.kt`
- [x] T063 [P] [US4] Add drawer selected-state semantics Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/DrawerSelectedStateSemanticsTest.kt`
- [x] T064 [P] [US4] Add large font and display zoom Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/AuthenticatedShellLargeFontTest.kt`
- [x] T065 [P] [US4] Add compact and expanded drawer layout Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/AuthenticatedShellAdaptiveLayoutTest.kt`
- [x] T066 [P] [US4] Add session expiration while on protected route regression test in `android/app/src/test/java/com/rbdarts/materialyou/ProtectedRouteTest.kt`

### Implementation for User Story 4

- [x] T067 [US4] Add hamburger content description, destination labels, and selected-state semantics in `android/app/src/main/java/com/rbdarts/app/RBDartsAdaptiveNavigation.kt`
- [x] T068 [US4] Tune drawer width, spacing, system insets, and ellipsized labels for compact and expanded layouts in `android/app/src/main/java/com/rbdarts/app/RBDartsAdaptiveNavigation.kt`
- [x] T069 [US4] Ensure back behavior closes the drawer before normal navigation behavior in `android/app/src/main/java/com/rbdarts/app/RBDartsAppShell.kt`
- [x] T070 [US4] Update quickstart with accessibility, large font, display zoom, and emulator smoke steps in `specs/006-reface-rest-android/quickstart.md`
- [x] T071 [US4] Record accessibility and adaptive layout results in `specs/006-reface-rest-android/evidence/authenticated-shell-smoke.md`

**Checkpoint**: All user stories are independently functional.

---

## Phase 7: Polish & Cross-Cutting Concerns

**Purpose**: Validate design quality, security, accessibility, performance, APK generation, emulator smoke, and documentation.

- [x] T072 [P] Update Android launch verification notes for the authenticated shell redesign in `README.md`
- [x] T073 [P] Update Google Play screenshot checklist for dark authenticated shell captures in `launch/google-play/screenshots/screenshot-checklist.md`
- [x] T074 [P] Add screenshot evidence paths and capture checklist in `specs/006-reface-rest-android/design-qa.md`
- [x] T075 Run Android unit tests with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew testDebugUnitTest` and record result in `specs/006-reface-rest-android/verification.md`
- [x] T076 Run Android UI test source compilation with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew compileDebugAndroidTestKotlin` and record result in `specs/006-reface-rest-android/verification.md`
- [x] T077 Run Android lint with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew lintDebug` and record result in `specs/006-reface-rest-android/verification.md`
- [x] T078 Build debug APK with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew assembleDebug` and record result in `specs/006-reface-rest-android/verification.md`
- [x] T079 Install and launch APK on `Medium_Phone_API_36.1` or available emulator and save screenshot evidence in `specs/006-reface-rest-android/evidence/authenticated-shell-smoke.md`
- [x] T080 Run final secret, metadata, diagnostics, route, and asset scan for authenticated shell UI and docs and record result in `specs/006-reface-rest-android/security-threat-model.md`
- [x] T081 Review requirements against completed implementation and update `specs/006-reface-rest-android/checklists/requirements.md`
- [x] T082 Confirm all tasks are complete and mark checklist state in `specs/006-reface-rest-android/tasks.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies.
- **Foundational (Phase 2)**: Depends on Setup and blocks all user stories.
- **US1 Unified Premium Dark Shell (Phase 3)**: Depends on Foundational and is part of the MVP.
- **US2 Top-Left Hamburger Menu (Phase 4)**: Depends on Foundational and is part of the MVP.
- **US3 Efficient Darts Workflows (Phase 5)**: Depends on the dark shell and drawer structure from US1 and US2.
- **US4 Accessibility And Adaptive Layouts (Phase 6)**: Depends on the drawer and restyled screens.
- **Polish (Phase 7)**: Depends on all desired user stories.

### User Story Dependencies

- **US1 - Unified premium dark app shell**: Can be implemented after Foundation only.
- **US2 - Top-left hamburger menu**: Can be implemented after Foundation only and should land with US1 for the requested shell experience.
- **US3 - Efficient darts workflows**: Requires US1 visual tokens and US2 drawer navigation to validate real workflows.
- **US4 - Accessibility and adaptive layouts**: Requires drawer behavior and representative restyled screens.

### Within Each User Story

- Write and run the listed tests first; they should fail before implementation.
- Implement state and design-system primitives before screen integration.
- Implement drawer behavior before deleting old bottom/rail assumptions from screens.
- Validate each story independently before moving to the next priority.
- Record design, security, and verification evidence as each story lands.

### Parallel Opportunities

- T002-T006 can run in parallel after T001.
- T007-T011 can run in parallel after Setup.
- T016-T023 can run in parallel after icon naming is agreed through T015.
- US1 tests T026-T029 can run in parallel.
- US2 tests T036-T041 can run in parallel.
- US3 tests T049-T052 can run in parallel.
- US4 tests T062-T066 can run in parallel.
- Polish documentation tasks T072-T074 can run in parallel.

---

## Parallel Example: User Story 2

```text
Task: T036 HamburgerNavigationButtonTest.kt
Task: T037 NoBottomNavigationRegressionTest.kt
Task: T038 NavigationDrawerContentTest.kt
Task: T039 NavigationDrawerSelectionTest.kt
Task: T040 NavigationDrawerSignOutTest.kt
Task: T041 NavigationDrawerBackBehaviorTest.kt
```

---

## Implementation Strategy

### MVP First (User Stories 1 And 2)

1. Complete Setup.
2. Complete Foundational state, diagnostics, dark components, icons, and test harness.
3. Complete US1 tests and dark shell implementation.
4. Complete US2 tests and drawer navigation implementation.
5. Validate Home plus every drawer destination renders with no bottom navigation.

### Incremental Delivery

1. Foundation -> testable state, diagnostics, components, and assets.
2. US1 -> dark authenticated shell and route preservation.
3. US2 -> hamburger drawer replacing bottom navigation and rail.
4. US3 -> workflow screen restyle for setup, handicap, and scoring.
5. US4 -> accessibility and adaptive behavior.
6. Polish -> docs, verification, APK, emulator/manual screenshot evidence, and final checklist.

### Scope Guardrails

- Do not rework the redesigned loading screen or login page beyond preserving handoff consistency.
- Do not change SSO provider configuration, token handling, or backend behavior.
- Do not introduce a new navigation framework unless the existing route enum cannot satisfy the drawer contract.
- Do not expose provider, user, player, score, or session details in drawer labels or diagnostics.
- Do not restore bottom navigation or navigation rail at any viewport size.
