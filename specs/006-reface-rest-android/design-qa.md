# Design QA: Authenticated Android App Shell Redesign

Feature: `006-reface-rest-android`

## Visual Baseline

- Preserve redesigned load screen and login page.
- Authenticated shell uses deep navy background, elevated blue-gray surfaces, teal accent, high-contrast text, and muted secondary text.
- Bottom navigation and navigation rail are removed from authenticated screens.
- Top-left hamburger button opens the navigation drawer.

## Screen Checklist

- Home: dark dashboard, clear start scoring action, secure session messaging.
- Game Type: dark option panels, visible selected state, clear continue action.
- Players: readable text fields, validation messages, save action, saved player cards.
- Seasons: readable date/rule fields, validation messages, save action, saved season cards.
- Handicaps: player-level actions, selected player impact, editable override field, save action.
- Scoring: prominent pending score, digit controls, totals, scorecard review, submit/complete actions.
- Settings: version, support, privacy, and account deletion actions on dark surfaces.

## Accessibility Checklist

- Hamburger content description: `Open navigation menu`.
- Drawer destination labels are readable and accessible.
- Selected drawer destination exposes selected state.
- Large font and display zoom do not clip route titles, drawer labels, forms, or scoring controls.
- Back closes the drawer before normal app navigation.

## Screenshot Evidence Targets

- Home with drawer closed.
- Drawer open from Home.
- Player creation form.
- Season creation form.
- Handicap management.
- Scoring screen with score controls.
- Settings screen.

## Current Notes

- Implementation uses Material 3 `ModalNavigationDrawer`, `ModalDrawerSheet`, and `NavigationDrawerItem`.
- Local vector icons are monochrome and tinted by Material state colors.
- Manual emulator screenshot capture remains pending until a stable emulator run is available.
