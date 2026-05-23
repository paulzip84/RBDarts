# Implementation Plan: Login Page Visual Redesign

**Branch**: `004-login-page-redesign` | **Date**: 2026-05-22 | **Spec**: [spec.md](spec.md)

**Input**: Feature specification from `/specs/004-login-page-redesign/spec.md`

## Summary

Redesign the Android Kotlin/Jetpack Compose login page into a premium dark, branded, SSO-only welcome experience inspired by the supplied reference image. The implementation will preserve the existing RBDarts security posture by keeping Google and Facebook SSO as the only MVP sign-in paths, avoiding email/password UI, and converting password-recovery affordances into provider-safe support links.

The plan scopes changes to the Android login feature, shared Material You components where reusable, UI state models, login diagnostics, and Android test coverage. No backend, iOS, first-party password, Microsoft, Okta, or production provider-console work is included.

## Technical Context

**Language/Version**: Kotlin 2.1.0, Jetpack Compose, Android Gradle Plugin 8.13.2

**Primary Dependencies**: AndroidX Activity Compose, Compose Material 3, Lifecycle ViewModel Compose, existing Firebase/Auth provider dependencies, Google ID dependency, Facebook Login dependency

**Storage**: No new storage. Existing auth/session abstractions remain responsible for provider session handling. UI state keeps only coarse provider, status, and display-safe messages.

**Testing**: JUnit unit tests for login presentation/state rules; Android Compose UI tests for default, loading, provider failure, offline, dark theme, large-font/adaptive layout, accessibility labels, and route protection

**Target Platform**: Android app module, minSdk 29, targetSdk 36, compact phones through tablets/foldables

**Project Type**: Native mobile app with Android client changes only

**Performance Goals**: Login visible within 1 second after unauthenticated routing; selected-provider loading state within 250 ms; no visible provider-button layout shifts after interaction; retry state after provider failure without app restart

**Constraints**: Security-first; SSO-only; no first-party password handling; no raw provider payloads in UI state or diagnostics; Material You; accessible 48dp touch targets; dark visual treatment with safe light-theme fallback; preserve local pre-auth routing intent where safe

**Scale/Scope**: One Android login surface plus reusable login components, state contracts, docs, and tests. MVP providers remain Google and Facebook.

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Security and Privacy Gate

- Pass. Plan documents login data classification in [data-model.md](data-model.md) and privacy-safe diagnostics in [login-ui-state-contract.md](contracts/login-ui-state-contract.md).
- UI state excludes provider tokens, emails, raw provider payloads, passwords, and secrets.
- No signing material, provider secrets, or config files are added.

### Native Platform Architecture Gate

- Pass. Android uses Kotlin, Compose Material 3, ViewModel state, and Android UI tests in the existing app module.
- iOS is out of scope for this Android-only login redesign.
- Shared behavior is expressed in Speckit docs/contracts and does not force cross-platform production code.

### Trusted SSO Identity Gate

- Pass. Google and Facebook remain the only MVP provider actions.
- First-party email/password, password reset, Microsoft, and Okta are out of scope.
- Provider failure, cancellation, unavailable, offline, sign-out redirection, and session-expired behavior are covered in contracts.

### Performance and Stability Gate

- Pass. Login render, provider tap response, retry recovery, rotation, background/resume, dark theme, dynamic color, and large-font stability targets are defined.

### Test and Observability Gate

- Pass. Required unit, UI, accessibility, and performance smoke coverage is identified.
- Login diagnostics use coarse provider/status attributes only.

## Project Structure

### Documentation (this feature)

```text
specs/004-login-page-redesign/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   ├── login-navigation-contract.md
│   └── login-ui-state-contract.md
└── tasks.md
```

### Source Code (repository root)

```text
android/
├── app/
│   └── src/
│       ├── main/
│       │   ├── java/com/rbdarts/core/designsystem/
│       │   ├── java/com/rbdarts/core/observability/
│       │   ├── java/com/rbdarts/core/ui/
│       │   ├── java/com/rbdarts/feature/auth/
│       │   └── res/drawable/
│       ├── test/java/com/rbdarts/materialyou/
│       └── androidTest/java/com/rbdarts/materialyou/
```

**Structure Decision**: Keep the redesign inside the existing Android app module. Login-specific presentation belongs under `feature/auth`; reusable Material You controls belong under `core/designsystem`; login-safe state contracts belong under `core/ui`; diagnostics helpers remain in `core/observability`; tests continue in `com.rbdarts.materialyou` until a dedicated auth UI test package is justified.

## Phase 0: Research

Completed in [research.md](research.md).

Research decisions:

- Translate the reference image into RBDarts-safe SSO UX rather than copying email/password fields.
- Use a dark premium login surface as the primary direction, with Material You color roles and a light fallback.
- Represent provider actions with a reusable SSO provider button model instead of hard-coded one-off buttons.
- Keep support/privacy/account deletion available but visually secondary.
- Use local provider states and existing auth abstractions for debug/unconfigured builds.

## Phase 1: Design And Contracts

Completed artifacts:

- [data-model.md](data-model.md)
- [contracts/login-ui-state-contract.md](contracts/login-ui-state-contract.md)
- [contracts/login-navigation-contract.md](contracts/login-navigation-contract.md)
- [quickstart.md](quickstart.md)

Post-design constitution check:

- Security and Privacy Gate: Pass. Data classification and redaction rules are explicit.
- Native Platform Architecture Gate: Pass. Android-only Compose implementation path is clear.
- Trusted SSO Identity Gate: Pass. No first-party credentials are introduced.
- Performance and Stability Gate: Pass. Measurable login and provider interaction targets remain intact.
- Test and Observability Gate: Pass. Required tests and privacy-safe event contracts are identified.

## Phase 2: Task Planning Preview

`$speckit-tasks` should generate work in this order:

1. Add login presentation state and provider action models.
2. Add reusable dark login surface, provider button, divider, and support-link components.
3. Redesign `LoginScreen.kt` around the new premium SSO-only layout.
4. Extend `LoginViewModel.kt` for unavailable/offline/session-expired states as needed.
5. Add privacy-safe login diagnostics events.
6. Add unit tests for state, provider actions, no-password invariants, and diagnostics redaction.
7. Add Compose UI tests for default, loading, failure, offline, dark theme, large font, adaptive layout, and support links.
8. Run `testDebugUnitTest`, `compileDebugAndroidTestKotlin`, `lintDebug`, `assembleDebug`, and emulator smoke.

## Complexity Tracking

No constitution violations or complexity exceptions are required.
