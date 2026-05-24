# Tasks: Login Page Visual Redesign

**Input**: Design documents from `/specs/004-login-page-redesign/`

**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/, quickstart.md

**Tests**: Required for SSO-only invariants, login presentation state, provider loading/failure/cancellation/offline/session-expired states, protected routing, accessibility, dark/light theme, large font, adaptive layout, diagnostics redaction, lint, APK build, and emulator smoke.

**Organization**: Tasks are grouped by user story so each login redesign increment can be implemented and tested independently.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel because it touches different files and does not depend on incomplete tasks.
- **[Story]**: Which user story this task belongs to, only used in user story phases.
- Every task includes an exact file path.

## Phase 1: Setup (Login Redesign Workspace)

**Purpose**: Prepare feature evidence, review scaffolding, and assets planning for the Android login redesign.

- [x] T001 Create login redesign security notes in `specs/004-login-page-redesign/security-threat-model.md`
- [x] T002 [P] Create login visual QA checklist in `specs/004-login-page-redesign/design-qa.md`
- [x] T003 [P] Create verification log in `specs/004-login-page-redesign/verification.md`
- [x] T004 [P] Create manual evidence template in `specs/004-login-page-redesign/evidence/login-redesign-smoke.md`
- [x] T005 [P] Create provider brand asset review checklist in `specs/004-login-page-redesign/provider-brand-review.md`
- [x] T006 Record current login screen baseline and design deltas in `specs/004-login-page-redesign/evidence/login-baseline.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Establish SSO-only state contracts, reusable login UI components, diagnostics, and test harness before user story work.

**CRITICAL**: No user story implementation should begin until this foundation is complete.

- [x] T007 [P] Add SSO-only no-password contract unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoginNoPasswordContractTest.kt`
- [x] T008 [P] Add login presentation state unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoginPresentationStateTest.kt`
- [x] T009 [P] Add SSO provider action state unit tests in `android/app/src/test/java/com/rbdarts/materialyou/SsoProviderActionTest.kt`
- [x] T010 [P] Add login diagnostics redaction tests in `android/app/src/test/java/com/rbdarts/materialyou/LoginDiagnosticsContractTest.kt`
- [x] T011 [P] Add login navigation contract unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoginNavigationContractTest.kt`
- [x] T012 [P] Add login-focused Compose test harness helpers in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginComposeTestHarness.kt`
- [x] T013 Implement login presentation, provider action, support link, and safe message models in `android/app/src/main/java/com/rbdarts/core/ui/LoginPresentationState.kt`
- [x] T014 Implement reusable premium login surface, provider button, divider, help link, and support link components in `android/app/src/main/java/com/rbdarts/core/designsystem/LoginComponents.kt`
- [x] T015 Add login-specific privacy-safe diagnostic event helpers in `android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt`
- [x] T016 Extend login view-model state mapping for unavailable, offline, cancelled, failed, and session-expired display states in `android/app/src/main/java/com/rbdarts/feature/auth/LoginViewModel.kt`
- [x] T017 Add Android-compatible local provider mark assets or approved fallbacks in `android/app/src/main/res/drawable/ic_google_mark.xml` and `android/app/src/main/res/drawable/ic_facebook_mark.xml`
- [x] T018 Update login data classification and threat mitigations in `specs/004-login-page-redesign/security-threat-model.md`
- [x] T019 Run baseline compile check with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew compileDebugKotlin` and record result in `specs/004-login-page-redesign/verification.md`

**Checkpoint**: Foundation ready. User story implementation can now begin.

---

## Phase 3: User Story 1 - Welcome Back With A Premium Branded Login Screen (Priority: P1)

**Goal**: Deliver the visually redesigned login page with a dark premium branded welcome treatment and no first-party credential UI.

**Independent Test**: Launch unauthenticated and verify the login page shows RBDarts branding, a welcome heading, concise SSO copy, provider actions, support access, and no email/password/password-reset controls.

### Tests for User Story 1

- [x] T020 [P] [US1] Add default premium login Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginPremiumScreenTest.kt`
- [x] T021 [P] [US1] Add no email/password/password-reset Compose UI regression test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginNoPasswordUiTest.kt`
- [x] T022 [P] [US1] Add dark theme login smoke test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginDarkThemeTest.kt`
- [x] T023 [P] [US1] Add light theme fallback login smoke test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginLightThemeTest.kt`
- [x] T024 [P] [US1] Add large font and compact width login UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginLargeFontAdaptiveTest.kt`
- [x] T025 [P] [US1] Add login accessibility labels test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginAccessibilityTest.kt`

### Implementation for User Story 1

- [x] T026 [US1] Redesign `LoginScreen` around the premium dark welcome layout in `android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt`
- [x] T027 [US1] Render the RBDarts mark or approved logo in the login header using `android/app/src/main/res/drawable/rbdarts_loading_mark.xml`
- [x] T028 [US1] Add "Welcome back" headline and concise SSO-only supporting copy in `android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt`
- [x] T029 [US1] Add decorative dark background treatment with screen-reader-hidden semantics in `android/app/src/main/java/com/rbdarts/core/designsystem/LoginComponents.kt`
- [x] T030 [US1] Replace visible plain privacy/support block with secondary support link treatment in `android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt`
- [x] T031 [US1] Ensure no email, password, or password-reset composables exist in `android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt`
- [x] T032 [US1] Update visual QA notes and screenshot checklist in `specs/004-login-page-redesign/design-qa.md`
- [x] T033 [US1] Record US1 manual smoke evidence template results in `specs/004-login-page-redesign/evidence/login-redesign-smoke.md`

**Checkpoint**: User Story 1 is independently demoable.

---

## Phase 4: User Story 2 - Sign In With Trusted SSO Providers (Priority: P2)

**Goal**: Provide clear Google and Facebook SSO actions with selected-provider loading, disabled, retry, failure, cancellation, and unavailable states.

**Independent Test**: Open login, tap each provider, verify selected loading state, success routes to authenticated home, and cancellation/failure/unavailable states remain retryable without exposing provider payloads.

### Tests for User Story 2

- [x] T034 [P] [US2] Add selected-provider loading unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoginProviderLoadingStateTest.kt`
- [x] T035 [P] [US2] Add provider unavailable/offline/session-expired unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoginProviderFailureStateTest.kt`
- [x] T036 [P] [US2] Add provider loading Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginProviderLoadingUiTest.kt`
- [x] T037 [P] [US2] Add provider cancellation Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginProviderCancellationUiTest.kt`
- [x] T038 [P] [US2] Add provider unavailable/failure Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginProviderFailureUiTest.kt`
- [x] T039 [P] [US2] Add double-tap protection Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginProviderDoubleTapTest.kt`
- [x] T040 [P] [US2] Add authenticated success route regression test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginSuccessRouteTest.kt`

### Implementation for User Story 2

- [x] T041 [US2] Replace generic action buttons with provider-aware SSO buttons in `android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt`
- [x] T042 [US2] Wire Google provider label, accessibility text, loading state, and unavailable fallback in `android/app/src/main/java/com/rbdarts/core/ui/LoginPresentationState.kt`
- [x] T043 [US2] Wire Facebook provider label, accessibility text, loading state, and unavailable fallback in `android/app/src/main/java/com/rbdarts/core/ui/LoginPresentationState.kt`
- [x] T044 [US2] Protect provider actions from duplicate submission while one provider is loading in `android/app/src/main/java/com/rbdarts/feature/auth/LoginViewModel.kt`
- [x] T045 [US2] Map provider cancellation and failure into privacy-safe login messages in `android/app/src/main/java/com/rbdarts/feature/auth/LoginViewModel.kt`
- [x] T046 [US2] Add provider selection, success, cancellation, and failure diagnostics in `android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt`
- [x] T047 [US2] Preserve successful sign-in routing through `RBDartsAppRoot` without bypassing protected routes in `android/app/src/main/java/com/rbdarts/app/RBDartsAppRoot.kt`
- [x] T048 [US2] Update provider brand review status in `specs/004-login-page-redesign/provider-brand-review.md`

**Checkpoint**: User Stories 1 and 2 are independently usable.

---

## Phase 5: User Story 3 - Recover From Login Problems Without Password UX (Priority: P3)

**Goal**: Provide provider-safe help, support, privacy, account deletion, offline, and session-expired recovery paths without password reset language.

**Independent Test**: Trigger provider failure/offline/session-expired states and verify safe messages, retry behavior, and support/account links are available without password reset UX.

### Tests for User Story 3

- [x] T049 [P] [US3] Add sign-in help link unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoginSupportLinkStateTest.kt`
- [x] T050 [P] [US3] Add offline login state unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoginOfflineStateTest.kt`
- [x] T051 [P] [US3] Add session-expired login state unit tests in `android/app/src/test/java/com/rbdarts/materialyou/LoginSessionExpiredStateTest.kt`
- [x] T052 [P] [US3] Add sign-in help Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginHelpLinkUiTest.kt`
- [x] T053 [P] [US3] Add support/privacy/account deletion Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginSupportLinksUiTest.kt`
- [x] T054 [P] [US3] Add offline message Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginOfflineUiTest.kt`
- [x] T055 [P] [US3] Add session-expired protected route Compose UI test in `android/app/src/androidTest/java/com/rbdarts/materialyou/LoginSessionExpiredRouteTest.kt`

### Implementation for User Story 3

- [x] T056 [US3] Add provider-safe "Need help signing in?" action in `android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt`
- [x] T057 [US3] Add compact support/privacy/account deletion secondary link presentation in `android/app/src/main/java/com/rbdarts/core/designsystem/LoginComponents.kt`
- [x] T058 [US3] Source support/privacy/account deletion labels and HTTPS destinations from `ReleaseConfiguration` in `android/app/src/main/java/com/rbdarts/feature/auth/LoginScreen.kt`
- [x] T059 [US3] Add offline login message mapping in `android/app/src/main/java/com/rbdarts/feature/auth/LoginViewModel.kt`
- [x] T060 [US3] Add session-expired login message mapping in `android/app/src/main/java/com/rbdarts/feature/auth/LoginViewModel.kt`
- [x] T061 [US3] Add help-selected diagnostic event in `android/app/src/main/java/com/rbdarts/core/observability/UiDiagnostics.kt`
- [x] T062 [US3] Update login navigation recovery notes in `specs/004-login-page-redesign/contracts/login-navigation-contract.md`
- [x] T063 [US3] Record support/offline/session-expired manual evidence in `specs/004-login-page-redesign/evidence/login-redesign-smoke.md`

**Checkpoint**: All user stories are independently functional.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Validate design quality, security, accessibility, APK generation, emulator smoke, and documentation.

- [x] T064 [P] Update Android Material You quickstart notes for the redesigned login in `specs/004-login-page-redesign/quickstart.md`
- [x] T065 [P] Update Google Play screenshot checklist for redesigned login captures in `launch/google-play/screenshots/screenshot-checklist.md`
- [x] T066 [P] Update README Android verification notes for 004 login redesign in `README.md`
- [x] T067 [P] Add login screenshot evidence paths and capture checklist in `specs/004-login-page-redesign/design-qa.md`
- [x] T068 Run Android unit tests with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew testDebugUnitTest` and record result in `specs/004-login-page-redesign/verification.md`
- [x] T069 Run Android UI test source compilation with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew compileDebugAndroidTestKotlin` and record result in `specs/004-login-page-redesign/verification.md`
- [x] T070 Run Android lint with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew lintDebug` and record result in `specs/004-login-page-redesign/verification.md`
- [x] T071 Build debug APK with `cd android && JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home" ANDROID_HOME="$HOME/Library/Android/sdk" ./gradlew assembleDebug` and record result in `specs/004-login-page-redesign/verification.md`
- [x] T072 Install and launch APK on `Medium_Phone_API_36.1` or available emulator and save screenshot evidence in `specs/004-login-page-redesign/evidence/login-redesign-smoke.md`
- [x] T073 Run final secret scan for login UI, provider assets, and docs and record result in `specs/004-login-page-redesign/security-threat-model.md`
- [x] T074 Review requirements against completed implementation and update `specs/004-login-page-redesign/checklists/requirements.md`
- [x] T075 Confirm all tasks are complete and mark checklist state in `specs/004-login-page-redesign/tasks.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies.
- **Foundational (Phase 2)**: Depends on Setup and blocks all user stories.
- **US1 Premium Branded Login (Phase 3)**: Depends on Foundational and is the MVP.
- **US2 Trusted SSO Provider Actions (Phase 4)**: Depends on US1 layout and Foundational provider state.
- **US3 Login Problem Recovery (Phase 5)**: Depends on Foundational support-link and message state; can begin after US1 shell exists.
- **Polish (Phase 6)**: Depends on all desired user stories.

### User Story Dependencies

- **US1 - Premium branded login**: Can be implemented after Foundation only.
- **US2 - Trusted SSO provider actions**: Requires the redesigned provider action surface from US1.
- **US3 - Recovery without password UX**: Uses US1 visual shell and US2 provider state handling, but remains independently testable by rendering recovery states.

### Within Each User Story

- Write and run the listed tests first; they should fail before implementation.
- Implement state models before composables.
- Implement reusable components before updating `LoginScreen.kt`.
- Integrate diagnostics after user-visible states exist.
- Validate each story independently before moving to the next priority.

### Parallel Opportunities

- T002-T005 can run in parallel after T001.
- T007-T012 can run in parallel after Setup.
- T013-T018 can run in parallel when files do not overlap, but T016 must be coordinated with later ViewModel tasks.
- US1 tests T020-T025 can run in parallel.
- US2 tests T034-T040 can run in parallel.
- US3 tests T049-T055 can run in parallel.
- Polish documentation tasks T064-T067 can run in parallel.

---

## Parallel Example: User Story 1

```text
Task: T020 LoginPremiumScreenTest.kt
Task: T021 LoginNoPasswordUiTest.kt
Task: T022 LoginDarkThemeTest.kt
Task: T023 LoginLightThemeTest.kt
Task: T024 LoginLargeFontAdaptiveTest.kt
Task: T025 LoginAccessibilityTest.kt
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Setup.
2. Complete Foundational state, components, diagnostics, and test harness.
3. Complete US1 tests and implementation.
4. Validate unauthenticated login renders the premium branded SSO-only surface.

### Incremental Delivery

1. Foundation -> testable state/components.
2. US1 -> redesigned no-password login page.
3. US2 -> provider loading/success/failure handling.
4. US3 -> help, support, offline, and session-expired recovery.
5. Polish -> final verification, screenshots, docs, and task closure.

### Scope Guardrails

- Do not add email/password authentication.
- Do not add Microsoft, Okta, or additional SSO providers.
- Do not commit production provider credentials, Firebase config, signing keys, or reviewer secrets.
- Do not introduce remote runtime image fetches for decorative login assets.
