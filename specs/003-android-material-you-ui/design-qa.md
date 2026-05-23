# Design QA: Android Material You UI

Feature: `003-android-material-you-ui`

## Material You Checks

- Dynamic color is enabled on Android 12+ with branded light/dark fallback schemes.
- Cards use the shared Material shape system and stay at app-level 8dp radii for repeated content.
- Primary workflows are available from the first authenticated screen: game type, players, seasons, handicaps, scoring, and settings.
- Compact navigation uses a bottom bar; wider layouts use a navigation rail.
- Loading, login, setup, handicap, and scoring screens use accessible visible labels.

## Interaction Checks

- SSO failure and cancellation states remain on the login screen.
- Sign-out returns to login and protects authenticated destinations.
- Player and season forms validate required data before saving.
- Handicap edits are permission-aware.
- Scoring rejects out-of-range values, updates running totals, shows handicap-adjusted totals, and exposes recovery/completion controls.

## Accessibility Checks

- Compose tests cover visible labels for loading, navigation, handicap, and scoring.
- Manual QA should still verify TalkBack order, 200% font scaling, dark theme contrast, and device rotation on a physical device or emulator.
