# Implementation Plan: Authenticated Android App Shell Redesign

**Branch**: `006-reface-rest-android` | **Date**: 2026-05-22 | **Spec**: [spec.md](spec.md)

**Input**: Feature specification from `/specs/006-reface-rest-android/spec.md`

## Summary

Redesign the Android post-login experience so Home, Game Type, Players, Seasons, Handicaps, Scoring, and Settings use the same premium dark visual language established by the redesigned login page, while preserving the already-redesigned load and login pages. Replace the current compact bottom navigation and expanded navigation rail with a top-left hamburger button that opens a Material 3 navigation drawer containing all authenticated destinations plus Sign Out.

The implementation remains Android-only and presentation/navigation focused. It must preserve existing authentication boundaries, sign-out behavior, protected route enforcement, form/scoring workflows, and privacy-safe diagnostics.

## Technical Context

**Language/Version**: Kotlin 2.1.0, Jetpack Compose, Android Gradle Plugin 8.13.2

**Primary Dependencies**: Existing AndroidX Activity Compose, Compose Material 3, Lifecycle ViewModel Compose, Hilt navigation setup, local Android vector resources. No new runtime dependency is planned for the drawer shell.

**Storage**: No new persistent storage. Existing screen/view-model state remains the source of truth for players, seasons, handicaps, scoring, and auth state.

**Testing**: JUnit unit tests for shell state, destination protection, diagnostics, and menu item contracts; Android Compose UI tests for hamburger visibility, drawer open/close, destination selection, no bottom navigation, dark surfaces, accessibility labels, compact/expanded layouts, large font, and representative workflows; Android lint and APK build; emulator/manual screenshot smoke when stable.

**Target Platform**: Android app module, minSdk 29, targetSdk 36, compact phones through tablets/foldables

**Project Type**: Native mobile app with Android client changes only

**Performance Goals**: Drawer open/close within 250 ms; local destination transitions within 500 ms; no scoring input latency regression; no added startup image/network cost; no unbounded recomposition or layout thrash.

**Constraints**: Security-first; protected routes must remain protected after sign-out/session expiration; no provider/session details in the drawer; loading and login redesigns are preserved; no bottom navigation in authenticated shell; top-left hamburger remains the primary navigation affordance; accessible labels and focus order required.

**Scale/Scope**: One Android authenticated shell, one dark authenticated design surface system, one navigation drawer menu, existing authenticated screens restyled, focused tests, docs, and screenshot evidence.

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Security and Privacy Gate

- Pass. The feature touches route names, screen labels, local workflow UI state, and coarse diagnostics only.
- Sensitive provider/session/user data remains outside drawer labels, navigation diagnostics, and visual state.
- Sign-out and protected routing remain explicit gates.

### Native Platform Architecture Gate

- Pass. Android implementation uses native Kotlin and Compose Material 3 app shell patterns.
- iOS is out of scope for this Android-only feature.
- Shared behavior is documented through Speckit artifacts only.

### Trusted SSO Identity Gate

- Pass. SSO provider configuration and credential flows are unchanged.
- Sign-out remains supported from the authenticated shell and must route back to login.
- No first-party passwords or provider secrets are introduced.

### Performance and Stability Gate

- Pass. Drawer and local navigation latency targets are defined.
- Scoring responsiveness, rotation, process recreation, large font, display zoom, and fallback route recovery are included.

### Test and Observability Gate

- Pass. Unit, Compose UI, accessibility, protected route, diagnostics, lint, APK, and manual screenshot evidence are identified.
- Diagnostics must use coarse route/menu event attributes only.

## Project Structure

### Documentation (this feature)

```text
specs/006-reface-rest-android/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   ├── authenticated-shell-navigation-contract.md
│   └── dark-authenticated-ui-contract.md
└── tasks.md
```

### Source Code (repository root)

```text
android/
├── app/
│   └── src/
│       ├── main/
│       │   ├── java/com/rbdarts/app/
│       │   │   ├── RBDartsAppShell.kt
│       │   │   ├── RBDartsAdaptiveNavigation.kt
│       │   │   ├── RBDartsNavHost.kt
│       │   │   └── RBDartsRoutes.kt
│       │   ├── java/com/rbdarts/core/designsystem/
│       │   ├── java/com/rbdarts/core/observability/
│       │   ├── java/com/rbdarts/core/ui/
│       │   ├── java/com/rbdarts/feature/gametype/
│       │   ├── java/com/rbdarts/feature/handicap/
│       │   ├── java/com/rbdarts/feature/home/
│       │   ├── java/com/rbdarts/feature/player/
│       │   ├── java/com/rbdarts/feature/scoring/
│       │   ├── java/com/rbdarts/feature/season/
│       │   └── res/drawable/
│       ├── test/java/com/rbdarts/materialyou/
│       └── androidTest/java/com/rbdarts/materialyou/
```

**Structure Decision**: Keep implementation in the existing Android app module. Replace/refactor the current `RBDartsAdaptiveNavigation` bottom/rail behavior into a drawer-based authenticated shell. Place reusable premium dark app shell and components in `core/designsystem`, shell state in `core/ui` or `app`, privacy-safe diagnostic helpers in `core/observability`, and tests in existing `com.rbdarts.materialyou` packages.

## Phase 0: Research

Completed in [research.md](research.md).

Research decisions:

- Use Material 3 `ModalNavigationDrawer`, `ModalDrawerSheet`, and `NavigationDrawerItem` for the hamburger pattern.
- Keep the hamburger at the top-left via top app bar `navigationIcon`.
- Remove bottom navigation and navigation rail from the authenticated shell rather than introducing adaptive alternatives that violate the request.
- Use login-inspired dark app tokens as the authenticated visual baseline.
- Preserve existing route enum/navigation state instead of introducing Navigation Compose as part of this feature.
- Use local vector resources for the hamburger and destination marks to avoid a new icon dependency.

## Phase 1: Design And Contracts

Completed artifacts:

- [data-model.md](data-model.md)
- [contracts/authenticated-shell-navigation-contract.md](contracts/authenticated-shell-navigation-contract.md)
- [contracts/dark-authenticated-ui-contract.md](contracts/dark-authenticated-ui-contract.md)
- [quickstart.md](quickstart.md)

Post-design constitution check:

- Security and Privacy Gate: Pass. Data classification and diagnostic limits are explicit.
- Native Platform Architecture Gate: Pass. Android-only Compose Material 3 drawer plan is clear.
- Trusted SSO Identity Gate: Pass. Identity flow is unchanged; sign-out and protected routes remain enforced.
- Performance and Stability Gate: Pass. Drawer/navigation/scoring targets are measurable.
- Test and Observability Gate: Pass. Required tests and privacy-safe events are specified.

## Phase 2: Task Planning Preview

`$speckit-tasks` should generate work in this order:

1. Create 006 security, design QA, verification, and screenshot evidence docs.
2. Add shell/menu state models and privacy-safe navigation diagnostics.
3. Add reusable dark authenticated app shell components and local hamburger/destination vector assets.
4. Replace bottom navigation/rail with a top-left hamburger `ModalNavigationDrawer`.
5. Restyle Home, Game Type, Players, Seasons, Handicaps, Scoring, and Settings with login-inspired dark components.
6. Preserve sign-out and protected routing from `RBDartsAppRoot`/`RBDartsDestinations`.
7. Add unit and Compose UI tests for menu, route, dark styling, accessibility, compact/expanded, large font, and workflows.
8. Run `testDebugUnitTest`, `compileDebugAndroidTestKotlin`, `lintDebug`, `assembleDebug`, diagnostics/secret review, and emulator/manual screenshot smoke.

## Complexity Tracking

No constitution violations or complexity exceptions are required.
