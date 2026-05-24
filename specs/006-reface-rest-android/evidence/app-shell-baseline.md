# Baseline Delta: Authenticated Android App Shell Redesign

Feature: `006-reface-rest-android`

## Before

- Compact authenticated screens used a bottom navigation bar.
- Expanded authenticated screens used a navigation rail.
- Authenticated screens used the generic app Material surface, which visually diverged from the redesigned loading and login pages.
- Sign Out appeared as a top app bar text action.

## After

- Authenticated screens use a top-left hamburger button.
- The hamburger opens a Material 3 navigation drawer.
- Bottom navigation and navigation rail code paths are removed.
- Sign Out is available in the drawer.
- Authenticated content uses login-inspired dark background, elevated surfaces, teal accent, and high-contrast text.
