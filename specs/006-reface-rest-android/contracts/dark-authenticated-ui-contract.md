# Contract: Dark Authenticated UI

Feature: `006-reface-rest-android`

## Scope

Screens in scope:

- Home
- Game Type
- Players and player creation
- Seasons and season creation
- Handicaps and player-level handicap editing
- Scoring
- Settings

Screens preserved as references:

- Redesigned loading screen from `005-load-screen-redesign`.
- Redesigned login page from `004-login-page-redesign`.

## Visual System

Authenticated screens must use:

- Deep dark/navy page background inspired by the login page.
- Blue-gray elevated surfaces for panels and grouped controls.
- Teal accent for selected menu items and primary actions.
- High-contrast white or near-white primary text.
- Muted blue-gray secondary text.
- Restrained warning, error, success, and disabled colors that remain readable on dark surfaces.
- Consistent spacing and radius aligned with existing RBDarts design tokens.

Authenticated screens must not use:

- The old bright/generic Material page background.
- One-off per-screen color palettes.
- Decorative visuals that obscure text, controls, or scoring information.
- Nested cards inside cards.

## Workflow Rules

Player and season forms:

- Labels, input values, helper text, validation messages, and save actions remain readable.
- Validation errors use accessible dark-theme error treatment.
- Primary save/create actions remain visible without extra navigation steps.

Handicap management:

- Player names, current handicap, derived handicap, and save/override actions remain scannable.
- Disabled or read-only states remain visibly distinct.

Scoring:

- Score input controls remain prominent and reachable.
- Pending score, active player, inning/target, totals, and submit action remain readable.
- Scoring interactions must not require opening the hamburger menu.

Home and Settings:

- Section summaries and support/settings actions use the same premium dark surface system.
- Sign Out remains available through the hamburger menu.

## Accessibility

- Top-left hamburger button has a clear content description.
- Drawer destination names are accessible.
- Selected menu destination is announced or semantically exposed.
- Large font and display zoom must not overlap titles, menu items, form labels, or scoring controls.
- Dark contrast must be sufficient for text, disabled controls, validation messages, and selected states.

## Verification

Minimum evidence:

- Compose UI tests for each primary authenticated destination rendered under the dark shell.
- Compose UI tests for hamburger menu open/close and selected state.
- Unit tests for route protection and privacy-safe diagnostics.
- Manual screenshots of Home, menu open, Players/Seasons form, Handicaps, and Scoring on a stable emulator/device.
