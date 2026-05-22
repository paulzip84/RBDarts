# Implementation Plan: [FEATURE]

**Branch**: `[###-feature-name]` | **Date**: [DATE] | **Spec**: [link]

**Input**: Feature specification from `/specs/[###-feature-name]/spec.md`

**Note**: This template is filled in by the `/speckit-plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

[Extract from feature spec: primary requirement + technical approach from research]

## Technical Context

<!--
  ACTION REQUIRED: Replace the content in this section with the technical details
  for the project. The structure here is presented in advisory capacity to guide
  the iteration process.
-->

**Language/Version**: [Swift version for iOS, Kotlin version for Android or NEEDS CLARIFICATION]

**Primary Dependencies**: [iOS frameworks, Android libraries, SSO/identity SDKs or NEEDS CLARIFICATION]

**Storage**: [local persistence, secure token storage, remote services, or N/A]

**Testing**: [XCTest/XCUITest for iOS, JUnit/Android UI tests for Android, security/performance tests or NEEDS CLARIFICATION]

**Target Platform**: [supported iOS versions/devices and Android API levels/devices or NEEDS CLARIFICATION]

**Project Type**: Native mobile app with iOS and Android clients

**Performance Goals**: [launch, score entry latency, screen transition, memory, battery, crash-free targets or NEEDS CLARIFICATION]

**Constraints**: [security, privacy, offline/degraded behavior, auth/session handling, accessibility, battery or NEEDS CLARIFICATION]

**Scale/Scope**: [expected users, match volume, device coverage, screens, supported locales or NEEDS CLARIFICATION]

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Security and Privacy Gate

- Threat model documents data touched, trust boundaries, attack paths, and mitigations.
- Sensitive data is minimized and excluded from logs, analytics, crash reports, and fixtures.
- Secrets, tokens, keys, and signing materials are stored outside the repository.

### Native Platform Architecture Gate

- iOS architecture, module boundaries, state management, async model, secure storage, and test strategy are documented.
- Android architecture, module boundaries, state management, async model, secure storage, lifecycle handling, and test strategy are documented.
- Shared behavior is expressed through specs/contracts/tests rather than forcing a lowest-common-denominator app architecture.

### Trusted SSO Identity Gate

- Authentication uses trusted SSO providers such as Google and Facebook when sign-in is required.
- Token storage, refresh, revocation, sign-out, provider failure, account linking, and cancellation behavior are specified.
- No first-party password collection or validation is introduced without a constitution amendment.

### Performance and Stability Gate

- Measurable launch, score entry, interaction, memory, battery, and crash-free targets are defined.
- Recovery behavior is specified for provider failure, network loss, app restart, session expiration, and OS lifecycle events.

### Test and Observability Gate

- Required unit, integration, UI/end-to-end, security, and performance tests are identified for affected platforms.
- Crash, error, and performance diagnostics are privacy-safe and exclude secrets, tokens, and unnecessary personal data.

## Project Structure

### Documentation (this feature)

```text
specs/[###-feature]/
├── plan.md              # This file (/speckit-plan command output)
├── research.md          # Phase 0 output (/speckit-plan command)
├── data-model.md        # Phase 1 output (/speckit-plan command)
├── quickstart.md        # Phase 1 output (/speckit-plan command)
├── contracts/           # Phase 1 output (/speckit-plan command)
└── tasks.md             # Phase 2 output (/speckit-tasks command - NOT created by /speckit-plan)
```

### Source Code (repository root)
<!--
  ACTION REQUIRED: Replace the placeholder tree below with the concrete layout
  for this feature. Delete unused options and expand the chosen structure with
  real paths (e.g., apps/admin, packages/something). The delivered plan must
  not include Option labels.
-->

```text
# [REMOVE IF UNUSED] Option 1: Single project (DEFAULT)
src/
├── models/
├── services/
├── cli/
└── lib/

tests/
├── contract/
├── integration/
└── unit/

# [REMOVE IF UNUSED] Option 2: Web application (when "frontend" + "backend" detected)
backend/
├── src/
│   ├── models/
│   ├── services/
│   └── api/
└── tests/

frontend/
├── src/
│   ├── components/
│   ├── pages/
│   └── services/
└── tests/

# [REMOVE IF UNUSED] Option 3: Native mobile apps (RBDarts default)
ios/
├── RBDarts/
│   ├── App/
│   ├── Features/
│   ├── Shared/
│   └── Resources/
└── RBDartsTests/

android/
├── app/
│   └── src/
│       ├── main/
│       ├── test/
│       └── androidTest/
└── [feature modules if used]

api/
└── [include only if backend services are in scope]
```

**Structure Decision**: [Document the selected structure and reference the real
directories captured above]

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| [e.g., 4th project] | [current need] | [why 3 projects insufficient] |
| [e.g., Repository pattern] | [specific problem] | [why direct DB access insufficient] |
