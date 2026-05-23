# Implementation Plan: Load Screen Visual Redesign

**Branch**: `005-load-screen-redesign` | **Date**: 2026-05-22 | **Spec**: [spec.md](spec.md)

**Input**: Feature specification from `/specs/005-load-screen-redesign/spec.md`

## Summary

Redesign the Android startup load screen from a plain centered vector mark into a full-bleed branded image experience using the attached `RBDarts_Login` PNG as the source artwork. The implementation will package an optimized Android-safe local derivative, layer readable `RBDarts` and version/build text over the artwork with a scrim/safe text region, preserve existing startup routing, and add fallback, accessibility, asset-review, and performance verification.

No backend, iOS, login-page, provider configuration, or identity-flow changes are included. The load screen remains presentation and startup feedback only; authentication and route decisions stay delegated to existing session/routing logic.

## Technical Context

**Language/Version**: Kotlin 2.1.0, Jetpack Compose, Android Gradle Plugin 8.13.2

**Primary Dependencies**: AndroidX Activity Compose, Compose Material 3, Lifecycle ViewModel Compose, existing Android resources, existing release configuration and auth routing classes

**Storage**: No new runtime storage. The source image may be copied into feature evidence or raw resources for traceability; the app runtime should use optimized local Android drawable/nodpi image assets.

**Testing**: JUnit unit tests for load presentation/fallback/route rules; Android Compose UI tests for full-bleed rendering, version label, accessibility labels, compact/adaptive layout, and fallback state; Android lint and APK build; emulator smoke screenshot

**Target Platform**: Android app module, minSdk 29, targetSdk 36, compact phones through tablets/foldables

**Project Type**: Native mobile app with Android client changes only

**Performance Goals**: Branded load content visible within 1 second; local session routing completes within 2 seconds; image derivative avoids startup memory pressure, blank screens, or visible jank

**Constraints**: Security-first; no remote image fetches; no secrets or provider payloads on load screen; static asset safety review required; accessibility labels required; fallback brand state required; preserve 003/004 routing and login behavior

**Scale/Scope**: One Android load screen, one optimized image asset family, small presentation state additions, tests, verification docs, and screenshot evidence

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Security and Privacy Gate

- Pass. Load screen displays only public brand/version/status text and local artwork.
- Sensitive session/provider data remains outside load-screen UI state and diagnostics.
- Asset review and secret scan are required for the attached image and optimized derivative.

### Native Platform Architecture Gate

- Pass. Android implementation uses Kotlin, Compose, Android resource packaging, and existing startup route components.
- iOS is out of scope for this Android-only feature.
- Shared behavior is documented through Speckit artifacts only.

### Trusted SSO Identity Gate

- Pass. The load screen does not collect credentials or alter SSO provider behavior.
- Auth/session decisions remain delegated to `StartupRouteController`, `RBDartsAppRoot`, and existing auth state.

### Performance and Stability Gate

- Pass. The plan defines startup visibility, route timing, image memory/APK bloat, fallback, rotation, background/resume, and accessibility stability targets.

### Test and Observability Gate

- Pass. Required unit, Compose UI, lint, APK, emulator smoke, asset review, and privacy-safe diagnostics checks are identified.

## Project Structure

### Documentation (this feature)

```text
specs/005-load-screen-redesign/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   ├── load-screen-ui-contract.md
│   └── startup-routing-contract.md
└── tasks.md
```

### Source Code (repository root)

```text
android/
├── app/
│   └── src/
│       ├── main/
│       │   ├── java/com/rbdarts/core/ui/
│       │   ├── java/com/rbdarts/core/observability/
│       │   ├── java/com/rbdarts/feature/auth/
│       │   └── res/
│       │       ├── drawable/
│       │       ├── drawable-nodpi/
│       │       └── raw/
│       ├── test/java/com/rbdarts/materialyou/
│       └── androidTest/java/com/rbdarts/materialyou/
```

**Structure Decision**: Keep implementation in the existing Android app module. Runtime image derivatives belong in Android resources, load-screen UI in `feature/auth`, presentation state in `core/ui`, diagnostics in `core/observability`, and tests in the existing `com.rbdarts.materialyou` test packages.

## Phase 0: Research

Completed in [research.md](research.md).

Research decisions:

- Use the attached image as visual source, not as an unmodified runtime asset.
- Package an optimized local bitmap derivative under Android resources.
- Use a full-bleed image with scrim/safe text region for readability.
- Preserve existing startup routing and keep provider/session error details off the load screen.
- Provide fallback to the existing RBDarts vector mark if the bitmap cannot render.

## Phase 1: Design And Contracts

Completed artifacts:

- [data-model.md](data-model.md)
- [contracts/load-screen-ui-contract.md](contracts/load-screen-ui-contract.md)
- [contracts/startup-routing-contract.md](contracts/startup-routing-contract.md)
- [quickstart.md](quickstart.md)

Post-design constitution check:

- Security and Privacy Gate: Pass. Data classification and asset review requirements are explicit.
- Native Platform Architecture Gate: Pass. Android-only Compose/resource implementation path is clear.
- Trusted SSO Identity Gate: Pass. SSO and session logic remain delegated.
- Performance and Stability Gate: Pass. Image optimization and fallback targets remain measurable.
- Test and Observability Gate: Pass. Tests and privacy-safe diagnostics are specified.

## Phase 2: Task Planning Preview

`$speckit-tasks` should generate work in this order:

1. Add load-screen asset review and verification docs.
2. Optimize and package `RBDarts_Login` as Android-safe local artwork.
3. Add load presentation state/fallback models and diagnostics.
4. Redesign `LoadingScreen.kt` into full-bleed image + scrim + readable status.
5. Preserve startup routing in `RBDartsAppRoot.kt`.
6. Add unit tests for presentation, fallback, asset metadata, and routing.
7. Add Compose UI tests for default, version label, accessibility, compact/adaptive, and fallback.
8. Run `testDebugUnitTest`, `compileDebugAndroidTestKotlin`, `lintDebug`, `assembleDebug`, secret scan, asset scan, and emulator smoke.

## Complexity Tracking

No constitution violations or complexity exceptions are required.
