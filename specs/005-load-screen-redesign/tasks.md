# Tasks: Load Screen Visual Redesign

**Input**: Design documents from `/specs/005-load-screen-redesign/`

**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/, quickstart.md

**Tests**: Required for startup routing, load presentation state, asset fallback, asset safety, diagnostics redaction, accessibility, adaptive layout, dark/light theme, large font, Android lint, APK build, and emulator smoke.

**Organization**: Tasks are grouped by user story so the redesigned load-screen increments can be implemented and tested independently.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel because it touches different files and does not depend on incomplete tasks.
- **[Story]**: Which user story this task belongs to, only used in user story phases.
- Every task includes an exact file path.

## Phase 1: Setup (Load Screen Redesign Workspace)

**Purpose**: Prepare security notes, visual QA, asset review, verification logs, and manual evidence locations.

- [x] T001 Create load-screen security and data classification notes in `specs/005-load-screen-redesign/security-threat-model.md`
- [x] T002 [P] Create source and runtime artwork review checklist in `specs/005-load-screen-redesign/asset-review.md`
- [x] T003 [P] Create adaptive visual QA checklist in `specs/005-load-screen-redesign/design-qa.md`
- [x] T004 [P] Create verification log in `specs/005-load-screen-redesign/verification.md`
- [x] T005 [P] Create manual smoke evidence template in `specs/005-load-screen-redesign/evidence/load-screen-smoke.md`
- [x] T006 Record current plain load-screen baseline and redesign deltas in `specs/005-load-screen-redesign/evidence/load-screen-baseline.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establish display-safe state, asset metadata, diagnostics, reusable load-screen components, and test harness support before user story work.

**CRITICAL**: No user story implementation should begin until this foundation is complete.

- [x] T007 [P] Add load presentation state unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoadScreenPresentationStateTest.kt`
- [x] T008 [P] Add load artwork asset metadata and fallback unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoadArtworkAssetContractTest.kt`
- [x] T009 [P] Add load-screen diagnostics redaction tests in `android/app/src/test/java/com/rbdarts/materialyou/LoadScreenDiagnosticsContractTest.kt`
- [x] T010 [P] Extend startup routing contract tests for session-expired, offline, timeout, and preserved-route safety in `android/app/src/test/java/com/rbdarts/materialyou/StartupRouteControllerTest.kt`
- [x] T011 [P] Add load-screen Compose test harness helpers in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadScreenComposeTestHarness.kt`
- [x] T012 Add load artwork, fallback, and display-safe loading message models in `android/app/src/main/java/com/rbdarts/core/ui/AppUiStates.kt`
- [x] T013 Add load-screen diagnostic event helpers in `android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt`
- [x] T014 Create reusable full-bleed artwork, scrim, loading status, and fallback components in `android/app/src/main/java/com/rbdarts/core/designsystem/LoadScreenComponents.kt`
- [x] T015 Update source image safety requirements and known data touched by the feature in `specs/005-load-screen-redesign/security-threat-model.md`
- [x] T016 Run baseline compile check with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew compileDebugKotlin` and record result in `specs/005-load-screen-redesign/verification.md`

**Checkpoint**: Foundation ready. User story implementation can now begin.

---

## Phase 3: User Story 1 - Launch With A Full-Bleed RBDarts Brand Moment (Priority: P1)

**Goal**: Show a polished full-bleed load screen derived from the supplied darts artwork, with readable `RBDarts`, version/build, and loading status before routing.

**Independent Test**: Cold launch unauthenticated and verify the load screen uses the optimized artwork, displays `RBDarts`, version/build, and a progress state, then routes to login without a blank or stale screen.

### Tests for User Story 1

- [x] T017 [P] [US1] Add full-bleed artwork Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenFullBleedArtworkTest.kt`
- [x] T018 [P] [US1] Add version/build label Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenVersionLabelTest.kt`
- [x] T019 [P] [US1] Add loading status/progress Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenProgressStateTest.kt`
- [x] T020 [P] [US1] Add no-login-controls regression Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenNoLoginControlsTest.kt`
- [x] T021 [P] [US1] Add route-to-login startup regression test in `android/app/src/test/java/com/rbdarts/materialyou/StartupRouteControllerTest.kt`

### Implementation for User Story 1

- [x] T022 [US1] Create an optimized local runtime derivative of `/Users/paulzip84/Downloads/RBDarts_Login` at `android/app/src/main/res/drawable-nodpi/rbdarts_load_background.jpg`
- [x] T023 [US1] Document source dimensions, runtime derivative dimensions, file size, format, focal region, and optimization command in `specs/005-load-screen-redesign/asset-review.md`
- [x] T024 [US1] Redesign `LoadingScreen` around full-bleed artwork, overlay scrim, app name, version/build, and loading status in `android/app/src/main/java/com/rbdarts/feature/auth/LoadingScreen.kt`
- [x] T025 [US1] Render the optimized artwork through reusable components from `android/app/src/main/java/com/rbdarts/core/designsystem/LoadScreenComponents.kt`
- [x] T026 [US1] Preserve `LaunchPresentationState` version/build source from `RBDartsAppRoot` in `android/app/src/main/java/com/rbdarts/app/RBDartsAppRoot.kt`
- [x] T027 [US1] Ensure load-screen text is not horizontally clipped on compact width in `android/app/src/main/java/com/rbdarts/feature/auth/LoadingScreen.kt`
- [x] T028 [US1] Record US1 visual notes and screenshot expectations in `specs/005-load-screen-redesign/design-qa.md`
- [x] T029 [US1] Record US1 manual smoke evidence template results in `specs/005-load-screen-redesign/evidence/load-screen-smoke.md`

**Checkpoint**: User Story 1 is independently demoable.

---

## Phase 4: User Story 2 - Keep The Image Safe, Performant, And Adaptive (Priority: P2)

**Goal**: Ensure the image-based startup state is local, efficient, readable, adaptive, and stable across representative Android devices and system settings.

**Independent Test**: Launch on compact, large phone, and tablet/foldable viewports and verify the artwork crop, text safe region, startup rendering, and fallback behavior.

### Tests for User Story 2

- [x] T030 [P] [US2] Add compact-phone crop and safe-text Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenCompactLayoutTest.kt`
- [x] T031 [P] [US2] Add large-screen adaptive load layout Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenExpandedLayoutTest.kt`
- [x] T032 [P] [US2] Add dark and light theme contrast smoke tests in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenThemeContrastTest.kt`
- [x] T033 [P] [US2] Add large font/display zoom regression test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenLargeFontTest.kt`
- [x] T034 [P] [US2] Add fallback artwork Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenFallbackTest.kt`
- [x] T035 [P] [US2] Add packaged asset contract unit test in `android/app/src/test/java/com/rbdarts/materialyou/LoadArtworkAssetContractTest.kt`

### Implementation for User Story 2

- [x] T036 [US2] Add primary/fallback artwork mode selection to `android/app/src/main/java/com/rbdarts/core/ui/AppUiStates.kt`
- [x] T037 [US2] Implement fallback rendering with `android/app/src/main/res/drawable/rbdarts_loading_mark.xml` in `android/app/src/main/java/com/rbdarts/core/designsystem/LoadScreenComponents.kt`
- [x] T038 [US2] Tune image content scale, alignment, focal region, and scrim treatment in `android/app/src/main/java/com/rbdarts/core/designsystem/LoadScreenComponents.kt`
- [x] T039 [US2] Keep progress/status placement stable through rotation and background/resume in `android/app/src/main/java/com/rbdarts/feature/auth/LoadingScreen.kt`
- [x] T040 [US2] Add source and derivative metadata, remote-reference, and secret scan results in `specs/005-load-screen-redesign/asset-review.md`
- [x] T041 [US2] Add APK size and startup memory notes for the optimized asset in `specs/005-load-screen-redesign/verification.md`
- [x] T042 [US2] Update adaptive QA outcomes for compact, large phone, tablet/foldable, dark/light theme, and large font in `specs/005-load-screen-redesign/design-qa.md`

**Checkpoint**: User Stories 1 and 2 are independently usable.

---

## Phase 5: User Story 3 - Provide Accessible And Privacy-Safe Startup Feedback (Priority: P3)

**Goal**: Provide accessible startup feedback and privacy-safe route diagnostics without exposing provider, account, session, or debug details.

**Independent Test**: Start with unauthenticated, authenticated, offline, session-expired, and delayed startup states and verify accessible labels, safe messages, diagnostics redaction, and correct routing.

### Tests for User Story 3

- [x] T043 [P] [US3] Add load-screen accessibility label Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingAccessibilityTest.kt`
- [x] T044 [P] [US3] Add decorative artwork semantics Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenArtworkSemanticsTest.kt`
- [x] T045 [P] [US3] Add privacy-safe loading message unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoadScreenPresentationStateTest.kt`
- [x] T046 [P] [US3] Add diagnostic allowed/disallowed attribute tests in `android/app/src/test/java/com/rbdarts/materialyou/LoadScreenDiagnosticsContractTest.kt`
- [x] T047 [P] [US3] Add authenticated and session-expired routing tests in `android/app/src/test/java/com/rbdarts/materialyou/StartupRouteControllerTest.kt`
- [x] T048 [P] [US3] Add duplicate-navigation lifecycle regression test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoadingScreenLifecycleTest.kt`

### Implementation for User Story 3

- [x] T049 [US3] Add screen-level `RBDarts loading screen` semantics and concise artwork treatment in `android/app/src/main/java/com/rbdarts/feature/auth/LoadingScreen.kt`
- [x] T050 [US3] Ensure app name, version/build, and loading status are independently exposed to assistive technology in `android/app/src/main/java/com/rbdarts/feature/auth/LoadingScreen.kt`
- [x] T051 [US3] Add privacy-safe load viewed, artwork mode, route completed, and route delayed diagnostics in `android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt`
- [x] T052 [US3] Keep provider/session errors off the load screen while routing via `android/app/src/main/java/com/rbdarts/app/RBDartsAppRoot.kt`
- [x] T053 [US3] Verify unauthenticated, authenticated, offline, session-expired, and timeout startup outcomes in `android/app/src/main/java/com/rbdarts/feature/auth/StartupRouteController.kt`
- [x] T054 [US3] Update startup routing contract notes with implementation decisions in `specs/005-load-screen-redesign/contracts/startup-routing-contract.md`
- [x] T055 [US3] Record accessibility, TalkBack, privacy-safe messages, and routing smoke results in `specs/005-load-screen-redesign/evidence/load-screen-smoke.md`

**Checkpoint**: All user stories are independently functional.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Validate design quality, security, accessibility, performance, APK generation, emulator smoke, and documentation.

- [x] T056 [P] Update Android quickstart notes for the redesigned load screen in `specs/005-load-screen-redesign/quickstart.md`
- [x] T057 [P] Update README Android launch verification notes for 005 load-screen redesign in `README.md`
- [x] T058 [P] Update Google Play screenshot checklist for load-screen capture evidence in `launch/google-play/screenshots/screenshot-checklist.md`
- [x] T059 [P] Add screenshot evidence paths and capture checklist in `specs/005-load-screen-redesign/design-qa.md`
- [x] T060 Run Android unit tests with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew testDebugUnitTest` and record result in `specs/005-load-screen-redesign/verification.md`
- [x] T061 Run Android UI test source compilation with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew compileDebugAndroidTestKotlin` and record result in `specs/005-load-screen-redesign/verification.md`
- [x] T062 Run Android lint with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew lintDebug` and record result in `specs/005-load-screen-redesign/verification.md`
- [x] T063 Build debug APK with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew assembleDebug` and record result in `specs/005-load-screen-redesign/verification.md`
- [ ] T064 Install and launch APK on `Medium_Phone_API_36.1` or available emulator and save screenshot evidence in `specs/005-load-screen-redesign/evidence/load-screen-smoke.md`
- [x] T065 Run final secret, metadata, and remote-reference scan for the source image, runtime derivative, load-screen UI, and docs and record result in `specs/005-load-screen-redesign/security-threat-model.md`
- [x] T066 Review requirements against completed implementation and update `specs/005-load-screen-redesign/checklists/requirements.md`
- [ ] T067 Confirm all tasks are complete and mark checklist state in `specs/005-load-screen-redesign/tasks.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies.
- **Foundational (Phase 2)**: Depends on Setup and blocks all user stories.
- **US1 Full-Bleed Brand Moment (Phase 3)**: Depends on Foundational and is the MVP.
- **US2 Safe, Performant, Adaptive Image (Phase 4)**: Depends on US1 artwork integration and Foundational asset state.
- **US3 Accessible, Privacy-Safe Feedback (Phase 5)**: Depends on Foundational diagnostics and route contracts; can begin after the US1 visual shell exists.
- **Polish (Phase 6)**: Depends on all desired user stories.

### User Story Dependencies

- **US1 - Full-bleed RBDarts brand moment**: Can be implemented after Foundation only.
- **US2 - Safe, performant, adaptive image**: Requires the primary artwork surface from US1.
- **US3 - Accessible, privacy-safe startup feedback**: Uses US1 visual shell and US2 fallback/adaptive state, but remains independently testable by rendering startup states.

### Within Each User Story

- Write and run the listed tests first; they should fail before implementation.
- Implement state models before composables.
- Implement reusable components before updating `LoadingScreen.kt`.
- Integrate diagnostics after user-visible states exist.
- Validate each story independently before moving to the next priority.

### Parallel Opportunities

- T002-T005 can run in parallel after T001.
- T007-T011 can run in parallel after Setup.
- T012-T015 can run in parallel when file ownership is coordinated.
- US1 tests T017-T021 can run in parallel.
- US2 tests T030-T035 can run in parallel.
- US3 tests T043-T048 can run in parallel.
- Polish documentation tasks T056-T059 can run in parallel.

---

## Parallel Example: User Story 1

```text
Task: T017 LoadingScreenFullBleedArtworkTest.kt
Task: T018 LoadingScreenVersionLabelTest.kt
Task: T019 LoadingScreenProgressStateTest.kt
Task: T020 LoadingScreenNoLoginControlsTest.kt
Task: T021 StartupRouteControllerTest.kt route-to-login regression
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Setup.
2. Complete Foundational state, asset metadata, diagnostics, components, and test harness.
3. Complete US1 tests and implementation.
4. Validate cold launch renders the redesigned branded load screen and routes to login.

### Incremental Delivery

1. Foundation -> testable state/components.
2. US1 -> full-bleed branded load screen.
3. US2 -> optimized local asset, adaptive crop, fallback, and performance checks.
4. US3 -> accessible status, safe messages, diagnostics, and route safety.
5. Polish -> final verification, screenshots, docs, and task closure.

### Scope Guardrails

- Do not change SSO provider configuration or add credential collection.
- Do not put provider buttons, password fields, password-reset links, or raw provider errors on the load screen.
- Do not fetch the load image from the network at runtime.
- Do not commit production secrets, signing keys, Firebase production config, provider credentials, or reviewer secrets.
- Do not package the raw source image if the optimized derivative is sufficient for runtime and evidence.
