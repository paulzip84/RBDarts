# Load Screen Baseline

Feature: `005-load-screen-redesign`

## Baseline Before 005

- File: `android/app/src/main/java/com/rbdarts/feature/auth/LoadingScreen.kt`
- Layout: centered Column with the existing vector `rbdarts_loading_mark`.
- Text: `RBDarts`, version/build, and `Preparing secure darts session`.
- Background: plain Material surface.
- Routing: `RBDartsAppRoot` starts at `Loading` and routes to login or home through `StartupRouteController`.

## Redesign Delta

- Replace plain centered layout with full-bleed image artwork.
- Use optimized local derivative of `/Users/paulzip84/Downloads/RBDarts_Login`.
- Add bottom scrim and stable text/status region.
- Keep version/build sourced from release configuration.
- Keep routing outside the load-screen composable.
- Add fallback rendering for missing/disabled primary artwork.
- Add accessibility, diagnostics, and route contract tests.
