# Tasks: Mobile App Store Launch Readiness

**Input**: Design documents from `/specs/002-launch-mobile-apps/`

**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/, quickstart.md

**Tests**: Required for launch-critical build configuration, authentication/reviewer access, scoring smoke flows, recovery, privacy disclosures, store package validation, beta gates, and launch monitoring readiness.

**Organization**: Tasks are grouped by user story so each launch-readiness increment can be implemented and tested independently.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel because it touches different files and does not depend on incomplete tasks.
- **[Story]**: Which user story this task belongs to, only used in user story phases.
- Every task includes an exact file path.

## Phase 1: Setup (Launch Artifact Infrastructure)

**Purpose**: Create the non-secret launch workspace, validation script locations, and platform release configuration placeholders.

- [X] T001 Create launch artifact placeholders in launch/app-store/metadata/.gitkeep, launch/app-store/screenshots/.gitkeep, launch/app-store/review-notes/.gitkeep, launch/google-play/metadata/.gitkeep, launch/google-play/screenshots/.gitkeep, launch/google-play/app-content/.gitkeep, launch/privacy/.gitkeep, launch/beta/.gitkeep, launch/release-gates/.gitkeep, and launch/runbooks/.gitkeep
- [X] T002 Create launch validation script directory placeholders in scripts/launch/.gitkeep and scripts/launch/schemas/.gitkeep
- [X] T003 [P] Create iOS release configuration template in ios/RBDarts/Resources/ReleaseConfig.example.plist
- [X] T004 [P] Create Android release configuration template in android/app/src/main/res/values/release_config_example.xml
- [X] T005 [P] Create Firebase production environment template in firebase/.env.production.example
- [X] T006 [P] Create launch evidence README in launch/README.md
- [X] T007 [P] Create launch artifact ignore safeguards in .gitignore
- [X] T008 [P] Create store account access checklist in launch/runbooks/store-account-access.md
- [X] T009 [P] Create launch owner and decision log template in launch/release-gates/decision-log.md
- [X] T010 Document launch workflow entry points in README.md

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Define launch models, schemas, privacy inventory, release gate contracts, and validation tooling used by every user story.

**CRITICAL**: No user story work can begin until this phase is complete.

- [X] T011 Create launch threat model and data classification notes in specs/002-launch-mobile-apps/security-threat-model.md
- [X] T012 [P] Create launch candidate schema in scripts/launch/schemas/launch-candidate.schema.json
- [X] T013 [P] Create store listing package schema in scripts/launch/schemas/store-listing-package.schema.json
- [X] T014 [P] Create privacy disclosure schema in scripts/launch/schemas/privacy-disclosure.schema.json
- [X] T015 [P] Create release gate schema in scripts/launch/schemas/release-gate.schema.json
- [X] T016 [P] Create beta cycle schema in scripts/launch/schemas/beta-cycle.schema.json
- [X] T017 [P] Create launch monitoring schema in scripts/launch/schemas/launch-monitoring.schema.json
- [X] T018 Create launch artifact validator script in scripts/launch/validate-launch-artifacts.mjs
- [X] T019 Create launch validation test suite in scripts/launch/validate-launch-artifacts.test.mjs
- [X] T020 [P] Create initial data inventory in launch/privacy/data-inventory.md
- [X] T021 [P] Create third-party service inventory in launch/privacy/third-party-services.md
- [X] T022 [P] Create non-secret release gate catalog in launch/release-gates/gate-catalog.json
- [X] T023 [P] Create launch candidate template in launch/release-gates/launch-candidate.template.json
- [X] T024 [P] Create release readiness report template in launch/release-gates/readiness-report.template.md
- [X] T025 [P] Create iOS launch smoke test plan in ios/RBDartsUITests/LaunchReadinessSmokeTests.swift
- [X] T026 [P] Create Android launch smoke test plan in android/app/src/androidTest/java/com/rbdarts/launch/LaunchReadinessSmokeTest.kt
- [X] T027 [P] Create Firebase backend smoke test in firebase/functions/test/launchReadiness.test.ts
- [X] T028 [P] Add Settings feature folders and placeholders in ios/RBDarts/Features/Settings/.gitkeep and android/app/src/main/java/com/rbdarts/feature/settings/.gitkeep
- [X] T029 [P] Create privacy-safe launch diagnostics contract in launch/privacy/diagnostics-review.md
- [X] T030 Create launch quickstart validation checklist in launch/release-gates/quickstart-validation.md

**Checkpoint**: Foundation ready. User story implementation can now begin.

---

## Phase 3: User Story 1 - Produce Launch-Ready Mobile Builds (Priority: P1)

**Goal**: Produce production-identifiable iOS and Android release candidates that pass smoke, recovery, and configuration checks.

**Independent Test**: Install release candidates on representative iOS and Android devices, sign in, complete standalone scoring, recover from interruption, and verify no development-only configuration or placeholder content is visible.

### Tests for User Story 1

- [X] T031 [P] [US1] Add iOS release configuration unit tests in ios/RBDartsTests/Launch/ReleaseConfigurationTests.swift
- [X] T032 [P] [US1] Add Android release configuration unit tests in android/app/src/test/java/com/rbdarts/launch/ReleaseConfigurationTest.kt
- [X] T033 [P] [US1] Add iOS launch smoke UI test for sign-in, standalone scoring, recovery, privacy/support access, and sign-out in ios/RBDartsUITests/LaunchReadinessSmokeTests.swift
- [X] T034 [P] [US1] Add Android launch smoke UI test for sign-in, standalone scoring, recovery, privacy/support access, and sign-out in android/app/src/androidTest/java/com/rbdarts/launch/LaunchReadinessSmokeTest.kt
- [X] T035 [P] [US1] Add Firebase production configuration smoke test in firebase/functions/test/launchReadiness.test.ts
- [X] T036 [P] [US1] Add launch candidate schema fixture test in scripts/launch/validate-launch-artifacts.test.mjs

### Implementation for User Story 1

- [X] T037 [P] [US1] Implement iOS release configuration model in ios/RBDarts/Shared/Launch/ReleaseConfiguration.swift
- [X] T038 [P] [US1] Implement Android release configuration model in android/app/src/main/java/com/rbdarts/core/launch/ReleaseConfiguration.kt
- [X] T039 [P] [US1] Add iOS launch configuration loading in ios/RBDarts/App/RBDartsApp.swift
- [X] T040 [P] [US1] Add Android launch configuration loading in android/app/src/main/java/com/rbdarts/app/RBDartsApplication.kt
- [X] T041 [P] [US1] Add iOS production app identity and version checklist in launch/release-gates/ios-build-identity.md
- [X] T042 [P] [US1] Add Android production application id and version checklist in launch/release-gates/android-build-identity.md
- [X] T043 [P] [US1] Configure iOS release build settings placeholders in ios/RBDarts.xcodeproj/project.pbxproj
- [X] T044 [P] [US1] Configure Android release build type and signing placeholders in android/app/build.gradle.kts
- [X] T045 [P] [US1] Add iOS privacy/support/account deletion links surface in ios/RBDarts/Features/Settings/PrivacyAndSupportView.swift
- [X] T046 [P] [US1] Add Android privacy/support/account deletion links surface in android/app/src/main/java/com/rbdarts/feature/settings/PrivacyAndSupportScreen.kt
- [X] T047 [P] [US1] Add iOS release candidate checklist in launch/release-gates/ios-release-candidate.md
- [X] T048 [P] [US1] Add Android release candidate checklist in launch/release-gates/android-release-candidate.md
- [X] T049 [US1] Integrate Settings privacy/support access from iOS app entry point in ios/RBDarts/App/RBDartsApp.swift
- [X] T050 [US1] Integrate Settings privacy/support access from Android app entry point in android/app/src/main/java/com/rbdarts/app/MainActivity.kt
- [X] T051 [US1] Create release candidate record for iOS in launch/release-gates/ios-launch-candidate.json
- [X] T052 [US1] Create release candidate record for Android in launch/release-gates/android-launch-candidate.json
- [X] T053 [US1] Run launch artifact validator and record build candidate result in launch/release-gates/build-validation.md

**Checkpoint**: User Story 1 is functional and testable as the minimum launch-readiness increment.

---

## Phase 4: User Story 2 - Complete Store Listing And Compliance Package (Priority: P2)

**Goal**: Prepare complete Apple App Store and Google Play metadata, privacy disclosures, screenshots, support links, and reviewer access instructions.

**Independent Test**: Review each store package against the contract and confirm every required field, asset, disclosure, and reviewer instruction is complete, accurate, and consistent with the installed release candidate.

### Tests for User Story 2

- [X] T054 [P] [US2] Add store listing package schema tests in scripts/launch/validate-launch-artifacts.test.mjs
- [X] T055 [P] [US2] Add privacy disclosure schema tests in scripts/launch/validate-launch-artifacts.test.mjs
- [X] T056 [P] [US2] Add public URL reachability validator in scripts/launch/check-public-urls.mjs
- [X] T057 [P] [US2] Add reviewer access validation checklist test in launch/app-store/review-notes/reviewer-access-check.md

### Implementation for User Story 2

- [X] T058 [P] [US2] Draft Apple App Store metadata in launch/app-store/metadata/en-US.json
- [X] T059 [P] [US2] Draft Google Play metadata in launch/google-play/metadata/en-US.json
- [X] T060 [P] [US2] Create Apple screenshot capture checklist in launch/app-store/screenshots/screenshot-checklist.md
- [X] T061 [P] [US2] Create Google Play screenshot capture checklist in launch/google-play/screenshots/screenshot-checklist.md
- [X] T062 [P] [US2] Create Apple review notes template in launch/app-store/review-notes/review-notes.md
- [X] T063 [P] [US2] Create Google Play app access instructions template in launch/google-play/app-content/app-access.md
- [X] T064 [P] [US2] Create Apple privacy disclosure draft in launch/privacy/apple-app-privacy.json
- [X] T065 [P] [US2] Create Google Data Safety disclosure draft in launch/privacy/google-data-safety.json
- [X] T066 [P] [US2] Create age/content rating answers worksheet in launch/google-play/app-content/content-rating.md
- [X] T067 [P] [US2] Create Apple age rating and compliance worksheet in launch/app-store/metadata/age-rating-and-compliance.md
- [X] T068 [P] [US2] Create support URL and privacy policy URL verification evidence in launch/privacy/public-url-verification.md
- [X] T069 [P] [US2] Create reviewer access plan with no secrets in launch/release-gates/reviewer-access-plan.md
- [X] T070 [P] [US2] Create account deletion request evidence in launch/privacy/account-deletion-path.md
- [X] T071 [P] [US2] Create third-party disclosure mapping in launch/privacy/third-party-disclosure-map.md
- [X] T072 [US2] Validate store packages with scripts/launch/validate-launch-artifacts.mjs and record results in launch/release-gates/store-package-validation.md
- [X] T073 [US2] Validate public support/privacy URLs with scripts/launch/check-public-urls.mjs and record results in launch/privacy/public-url-verification.md

**Checkpoint**: Store listing and compliance packages are complete enough for review preparation.

---

## Phase 5: User Story 3 - Validate Through Beta And Release Gates (Priority: P3)

**Goal**: Run beta validation, collect evidence, review diagnostics/accessibility/device coverage, and update release gates before public submission.

**Independent Test**: Complete a beta cycle with representative testers and verify all blocking launch gates are passed, fixed, or formally deferred with owner approval.

### Tests for User Story 3

- [X] T074 [P] [US3] Add beta cycle schema tests in scripts/launch/validate-launch-artifacts.test.mjs
- [X] T075 [P] [US3] Add release gate schema tests in scripts/launch/validate-launch-artifacts.test.mjs
- [X] T076 [P] [US3] Add iOS accessibility smoke coverage in ios/RBDartsUITests/LaunchAccessibilityTests.swift
- [X] T077 [P] [US3] Add Android accessibility smoke coverage in android/app/src/androidTest/java/com/rbdarts/launch/LaunchAccessibilityTest.kt
- [X] T078 [P] [US3] Add launch diagnostics privacy regression checks in ios/RBDartsTests/Launch/LaunchDiagnosticsPrivacyTests.swift and android/app/src/test/java/com/rbdarts/launch/LaunchDiagnosticsPrivacyTest.kt

### Implementation for User Story 3

- [X] T079 [P] [US3] Create TestFlight beta plan in launch/beta/testflight-plan.md
- [X] T080 [P] [US3] Create Google Play internal or closed testing plan in launch/beta/google-play-testing-plan.md
- [X] T081 [P] [US3] Create beta tester journey script in launch/beta/tester-journey-script.md
- [X] T082 [P] [US3] Create beta feedback collection template in launch/beta/feedback-template.md
- [X] T083 [P] [US3] Create beta device matrix in launch/beta/device-matrix.md
- [X] T084 [P] [US3] Create beta cycle record for iOS in launch/beta/ios-beta-cycle.json
- [X] T085 [P] [US3] Create beta cycle record for Android in launch/beta/android-beta-cycle.json
- [X] T086 [P] [US3] Create accessibility evidence report for iOS in launch/release-gates/ios-accessibility.md
- [X] T087 [P] [US3] Create accessibility evidence report for Android in launch/release-gates/android-accessibility.md
- [X] T088 [P] [US3] Create crash-free sessions evidence template in launch/release-gates/crash-free-evidence.md
- [X] T089 [P] [US3] Create recovery and offline beta evidence in launch/release-gates/recovery-beta-evidence.md
- [X] T090 [P] [US3] Create diagnostics privacy review evidence in launch/privacy/diagnostics-launch-review.md
- [X] T091 [US3] Update release gate results after beta in launch/release-gates/beta-gate-results.json
- [X] T092 [US3] Create launch readiness report draft in launch/release-gates/launch-readiness-report.md
- [X] T093 [US3] Run launch artifact validator and record beta gate result in launch/release-gates/beta-validation.md

**Checkpoint**: Beta evidence and release gates are ready for submission decision.

---

## Phase 6: User Story 4 - Submit, Release, And Monitor Public Launch (Priority: P4)

**Goal**: Define and execute controlled submission, review triage, release, monitoring, support, and hotfix/rollback processes.

**Independent Test**: Use the runbooks to submit both platform candidates, track review status, respond to review issues, and verify monitoring/escalation readiness for the first release window.

### Tests for User Story 4

- [X] T094 [P] [US4] Add launch monitoring schema tests in scripts/launch/validate-launch-artifacts.test.mjs
- [X] T095 [P] [US4] Add store review issue schema tests in scripts/launch/validate-launch-artifacts.test.mjs
- [X] T096 [P] [US4] Add runbook link validation in scripts/launch/check-public-urls.mjs
- [X] T097 [P] [US4] Add launch readiness report validation in scripts/launch/validate-launch-artifacts.test.mjs

### Implementation for User Story 4

- [X] T098 [P] [US4] Create Apple submission runbook in launch/runbooks/apple-submission.md
- [X] T099 [P] [US4] Create Google Play submission runbook in launch/runbooks/google-play-submission.md
- [X] T100 [P] [US4] Create store review issue tracker template in launch/runbooks/store-review-issues.md
- [X] T101 [P] [US4] Create first release monitoring plan in launch/runbooks/launch-monitoring-plan.json
- [X] T102 [P] [US4] Create support readiness runbook in launch/runbooks/support-readiness.md
- [X] T103 [P] [US4] Create severe issue escalation runbook in launch/runbooks/severe-issue-escalation.md
- [X] T104 [P] [US4] Create hotfix and rollback criteria in launch/runbooks/hotfix-rollback.md
- [X] T105 [P] [US4] Create App Store review response template in launch/app-store/review-notes/review-response-template.md
- [X] T106 [P] [US4] Create Google Play review response template in launch/google-play/app-content/review-response-template.md
- [X] T107 [P] [US4] Create production rollout decision checklist in launch/release-gates/production-rollout-checklist.md
- [X] T108 [US4] Finalize launch readiness report with submission, release, and monitoring decision in launch/release-gates/launch-readiness-report.md
- [X] T109 [US4] Run launch artifact validator and record public launch readiness result in launch/release-gates/public-launch-validation.md

**Checkpoint**: Submission, release, monitoring, and response process is ready for public launch.

---

## Phase 7: Polish & Cross-Cutting Concerns

**Purpose**: Final hardening, documentation, and validation across all launch stories.

- [X] T110 [P] Add launch artifact index in launch/INDEX.md
- [X] T111 [P] Add store policy refresh notes in launch/runbooks/store-policy-refresh.md
- [X] T112 [P] Add dependency/license launch review in launch/privacy/dependency-license-review.md
- [X] T113 [P] Add App Check and production Firebase availability evidence in launch/release-gates/firebase-production-readiness.md
- [X] T114 [P] Add SSO provider production readiness evidence in launch/release-gates/sso-production-readiness.md
- [X] T115 [P] Add final screenshot and metadata consistency review in launch/release-gates/metadata-screenshot-consistency.md
- [X] T116 [P] Add final secret scan evidence in launch/release-gates/secret-scan.md
- [X] T117 [P] Add final performance evidence in launch/release-gates/performance-evidence.md
- [X] T118 [P] Add final quickstart execution notes in launch/release-gates/quickstart-execution.md
- [X] T119 Update specs/002-launch-mobile-apps/quickstart.md with concrete commands and evidence paths
- [X] T120 Update README.md with launch-readiness workflow and non-secret artifact guidance

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies.
- **Foundational (Phase 2)**: Depends on Setup and blocks all user stories.
- **User Story 1 (Phase 3)**: Depends on Foundational. This is the MVP launch-readiness increment.
- **User Story 2 (Phase 4)**: Depends on Foundational and should use US1 release candidate records when available.
- **User Story 3 (Phase 5)**: Depends on US1 release candidates and US2 reviewer/privacy/store package drafts.
- **User Story 4 (Phase 6)**: Depends on US2 store package completion and US3 gate/beta evidence.
- **Polish (Phase 7)**: Depends on all launch stories selected for the release.

### User Story Dependencies

- **US1 - Produce Launch-Ready Mobile Builds**: Foundation only.
- **US2 - Complete Store Listing And Compliance Package**: Foundation; can begin in parallel with US1 drafts, but final validation depends on candidate identity from US1.
- **US3 - Validate Through Beta And Release Gates**: Requires release candidates and store/reviewer/privacy package drafts.
- **US4 - Submit, Release, And Monitor Public Launch**: Requires launch readiness report, store packages, beta results, and monitoring gates.

### Within Each User Story

- Required tests MUST be written before implementation.
- Schema/contract validation before artifact creation.
- Platform smoke tests before release gate approval.
- Privacy and reviewer access validation before store submission.
- Beta evidence before public launch approval.
- Launch monitoring and support runbooks before production rollout.

## Parallel Opportunities

- Setup tasks T003-T009 can run in parallel after T001-T002.
- Foundational schema and inventory tasks T012-T017 and T020-T029 can run in parallel after T011.
- US1 iOS, Android, Firebase, and launch evidence tasks can run in parallel where files do not overlap.
- US2 Apple, Google, privacy, screenshot, and reviewer access package tasks can run in parallel.
- US3 TestFlight, Google Play testing, accessibility, beta, and diagnostics evidence tasks can run in parallel.
- US4 Apple submission, Google submission, monitoring, support, escalation, and response runbooks can run in parallel.

## Parallel Example: User Story 1

```bash
Task: "T031 iOS release configuration unit tests in ios/RBDartsTests/Launch/ReleaseConfigurationTests.swift"
Task: "T032 Android release configuration unit tests in android/app/src/test/java/com/rbdarts/launch/ReleaseConfigurationTest.kt"
Task: "T035 Firebase production configuration smoke test in firebase/functions/test/launchReadiness.test.ts"
Task: "T041 iOS build identity checklist in launch/release-gates/ios-build-identity.md"
Task: "T042 Android build identity checklist in launch/release-gates/android-build-identity.md"
```

## Parallel Example: User Story 2

```bash
Task: "T058 Apple App Store metadata in launch/app-store/metadata/en-US.json"
Task: "T059 Google Play metadata in launch/google-play/metadata/en-US.json"
Task: "T064 Apple privacy disclosure in launch/privacy/apple-app-privacy.json"
Task: "T065 Google Data Safety disclosure in launch/privacy/google-data-safety.json"
Task: "T069 reviewer access plan in launch/release-gates/reviewer-access-plan.md"
```

## Parallel Example: User Story 3

```bash
Task: "T079 TestFlight beta plan in launch/beta/testflight-plan.md"
Task: "T080 Google Play testing plan in launch/beta/google-play-testing-plan.md"
Task: "T086 iOS accessibility evidence in launch/release-gates/ios-accessibility.md"
Task: "T087 Android accessibility evidence in launch/release-gates/android-accessibility.md"
Task: "T090 diagnostics privacy review in launch/privacy/diagnostics-launch-review.md"
```

## Parallel Example: User Story 4

```bash
Task: "T098 Apple submission runbook in launch/runbooks/apple-submission.md"
Task: "T099 Google Play submission runbook in launch/runbooks/google-play-submission.md"
Task: "T101 launch monitoring plan in launch/runbooks/launch-monitoring-plan.json"
Task: "T102 support readiness runbook in launch/runbooks/support-readiness.md"
Task: "T104 hotfix and rollback criteria in launch/runbooks/hotfix-rollback.md"
```

## Implementation Strategy

### MVP First

1. Complete Phase 1 and Phase 2.
2. Complete Phase 3 for US1 launch-ready build candidates.
3. Validate release candidate identity, smoke tests, recovery, and configuration.
4. Stop before store submission until store package and privacy artifacts are ready.

### Incremental Delivery

1. Add US2 store listing, privacy, reviewer access, and compliance package.
2. Add US3 beta validation and release gate evidence.
3. Add US4 submission, release, support, monitoring, and hotfix/rollback runbooks.
4. Run Phase 7 hardening before any public release.

### Release Gates

- No committed secrets, signing materials, reviewer passwords, or private Firebase configs.
- No first-party password flow.
- Release candidates pass auth, score entry, recovery, privacy/support, and sign-out smoke tests.
- Store privacy and data-safety disclosures match app behavior.
- Reviewer access is safe, tested, and revocable.
- Beta evidence shows no unresolved critical crash, startup, auth, score-save, or data-loss defect.
- Launch readiness report is approved with monitoring, support, escalation, hotfix, and rollback criteria.
