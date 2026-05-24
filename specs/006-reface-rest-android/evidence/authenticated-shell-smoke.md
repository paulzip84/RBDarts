# Smoke Evidence: Authenticated Android App Shell Redesign

Feature: `006-reface-rest-android`

## Manual Smoke Matrix

| Scenario | Status | Evidence |
| --- | --- | --- |
| Home renders in dark authenticated shell | Passed | `emulator-authenticated-home.png` |
| Hamburger opens drawer from top left | Passed | `emulator-authenticated-drawer.png` |
| Drawer lists Home, Game Type, Players, Seasons, Handicaps, Scoring, Settings, Sign Out | Passed | `emulator-authenticated-drawer.png`, `emulator-drawer-hierarchy.xml` |
| Selecting Players closes drawer and navigates | Automated coverage | `NavigationDrawerSelectionTest`; screenshot pending |
| Sign Out returns to login | Automated coverage | `NavigationDrawerSignOutTest`; screenshot pending |
| Scoring controls remain prominent | Passed | `emulator-scoring-validated.png` |
| Large font/display zoom remains usable | Automated coverage | `AuthenticatedShellLargeFontTest`, screenshot pending |
| APK installs and launches on `Medium_Phone_API_36.1` | Passed | Login screenshot: `emulator-login-validated.png` |

## Notes

- Automated Compose tests cover the core drawer behavior.
- A headless `Medium_Phone_API_36.1` emulator booted, installed `app-debug.apk`, launched RBDarts, authenticated through local Google SSO, opened the drawer, and navigated to Scoring.
- The emulator briefly displayed a `System UI isn't responding` dialog during startup; choosing `Wait` cleared the system dialog and RBDarts continued normally.
