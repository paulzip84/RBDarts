# Security Threat Model: Authenticated Android App Shell Redesign

Feature: `006-reface-rest-android`

## Data Classification

- Public app data: route labels, menu labels, static screen titles, app version, support/privacy URLs.
- User workflow data: player display names, season names, handicap values, score entries, active player, inning, target, totals.
- Sensitive data: SSO tokens, provider payloads, account identifiers, email addresses, session state, signed build metadata, private player notes, unreleased scoring records, raw diagnostics, and stack traces.

## Security Requirements

- Loading and login screens remain outside the authenticated shell.
- Home, Game Type, Players, Seasons, Handicaps, Scoring, and Settings remain protected destinations.
- Sign Out from the drawer closes authenticated UI access and routes back to login through existing app state.
- Drawer labels must not contain email addresses, provider ids, session ids, debug state, hidden route parameters, raw scores, or player-specific private notes.
- Navigation diagnostics may include only coarse destination names, menu state, result, and timing bucket.

## Implementation Notes

- The drawer uses local vector assets only, so no new remote artwork or icon dependency is introduced.
- The shell emits only privacy-safe diagnostic event builders in `UiDiagnostics`.
- Existing route protection remains centralized through `RBDartsDestinations.destinationFor` and `protectedRouteOrLogin`.
- Form and scoring content remains inside protected routes and is not shown when the route resolves to login.

## Review Status

- Source reviewed: authenticated shell, drawer navigation, route contracts, diagnostics, design-system components, and primary workflow screens.
- Secret scan scope: docs, drawer labels, diagnostics, vector assets, and app shell source.
- Automated scan result: no bottom navigation, navigation rail, or login credential controls remain in the authenticated shell source.
- Automated sensitive-term scan result: matches are limited to requirement text and redaction lists; drawer labels and icon assets do not contain sensitive values.
- Open manual item: verify emulator screenshots do not display private account, provider, token, or debug details.
